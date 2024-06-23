package controllers;

import models.*;
import services.ItemService;
import statics.ItemStatics;
import utils.ValidationUtils;
import java.util.List;

public class ItemController {

    private ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    public List<Item> getAllItems() {
        return itemService.getAllItems();
    }

    public List<Item> getItemsByFarmer(int farmerId) {
        return itemService.getItemsByFarmer(farmerId);
    }

    public String addItem(int farmerId, String name, String description, String priceStr,
                          String quantityStr, String type) {
        if (!ValidationUtils.isNonEmpty(name, description, priceStr, quantityStr)) {
            return "All fields are required.";
        }
        if (!ValidationUtils.isValidPrice(priceStr)) {
            return "Please enter a valid price.";
        }
        if (!ValidationUtils.isValidQuantity(quantityStr)) {
            return "Please enter a valid quantity.";
        }
        double price = Double.parseDouble(priceStr);
        int quantity = Integer.parseInt(quantityStr);
        Item item;
        if (ItemStatics.MACHINE.equals(type)) {
            item = new Machine(farmerId, name, description, price, quantity);
        } else {
            item = new Produce(farmerId, name, description, price, quantity);
        }
        itemService.addItem(item);
        return null;
    }

    public void updateItem(Item item) {
        itemService.updateItem(item);
    }

    public void deleteItem(int id) {
        itemService.deleteItem(id);
    }

    public boolean purchaseItem(int itemId, int quantity) {
        return itemService.purchaseItem(itemId, quantity);
    }
}
