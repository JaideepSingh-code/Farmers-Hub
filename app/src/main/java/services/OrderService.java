package services;

import models.OrderItem;
import repositories.orderItemRepository.OrderItemRepository;
import java.util.List;

public class OrderService {

    private OrderItemRepository orderItemRepository;

    public OrderService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    public void placeOrder(OrderItem orderItem) {
        orderItemRepository.save(orderItem);
    }

    public List<OrderItem> getOrdersByCustomer(int customerId) {
        return orderItemRepository.findByCustomerId(customerId);
    }

    public List<OrderItem> getSalesByFarmer(int farmerId) {
        return orderItemRepository.findByFarmerId(farmerId);
    }

    public double getTotalSales(int farmerId) {
        return getSalesByFarmer(farmerId).stream()
                .mapToDouble(OrderItem::getTotalPrice)
                .sum();
    }
}
