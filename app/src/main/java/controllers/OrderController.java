package controllers;

import models.Item;
import models.OrderItem;
import services.ItemService;
import services.OrderService;
import java.util.ArrayList;
import java.util.List;

public class OrderController {

    private OrderService orderService;
    private ItemService itemService;
    private List<OrderItem> cart = new ArrayList<>();

    public OrderController(OrderService orderService, ItemService itemService) {
        this.orderService = orderService;
        this.itemService = itemService;
    }

    public String addToCart(int customerId, int itemId, int quantity) {
        Item item = itemService.getItemById(itemId);
        if (item == null) {
            return "Item not found.";
        }
        if (quantity > item.getQuantityAvailable()) {
            return "Not enough stock available. Only " + item.getQuantityAvailable() + " left.";
        }
        double totalPrice = item.getPrice() * quantity;
        OrderItem orderItem = new OrderItem(customerId, itemId, item.getFarmerId(),
                item.getName(), quantity, totalPrice);
        cart.add(orderItem);
        return null;
    }

    public List<OrderItem> getCart() {
        return cart;
    }

    public void clearCart() {
        cart.clear();
    }

    public double getCartTotal() {
        return cart.stream().mapToDouble(OrderItem::getTotalPrice).sum();
    }

    public String checkout() {
        for (OrderItem orderItem : cart) {
            boolean purchased = itemService.purchaseItem(orderItem.getItemId(), orderItem.getQuantity());
            if (!purchased) {
                return "Failed to purchase " + orderItem.getItemName() + ". Not enough stock.";
            }
            orderService.placeOrder(orderItem);
        }
        cart.clear();
        return null;
    }

    public List<OrderItem> getOrderHistory(int customerId) {
        return orderService.getOrdersByCustomer(customerId);
    }

    public List<OrderItem> getSalesHistory(int farmerId) {
        return orderService.getSalesByFarmer(farmerId);
    }
}
