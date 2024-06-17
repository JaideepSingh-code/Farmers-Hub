package services;

import models.Item;
import repositories.itemRepository.ItemRepository;
import java.util.List;

public class ItemService {

    private ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public List<Item> getItemsByFarmer(int farmerId) {
        return itemRepository.findByFarmerId(farmerId);
    }

    public Item getItemById(int id) {
        return itemRepository.findById(id);
    }

    public void addItem(Item item) {
        itemRepository.save(item);
    }

    public void updateItem(Item item) {
        Item existing = itemRepository.findById(item.getId());
        if (existing != null) {
            existing.setName(item.getName());
            existing.setDescription(item.getDescription());
            existing.setPrice(item.getPrice());
            existing.setQuantityAvailable(item.getQuantityAvailable());
            itemRepository.update(existing);
        }
    }

    public void deleteItem(int id) {
        itemRepository.delete(id);
    }

    public boolean purchaseItem(int itemId, int quantity) {
        Item item = itemRepository.findById(itemId);
        if (item != null && item.getQuantityAvailable() >= quantity) {
            item.updateQuantityAvailable(-quantity);
            itemRepository.update(item);
            return true;
        }
        return false;
    }
}
