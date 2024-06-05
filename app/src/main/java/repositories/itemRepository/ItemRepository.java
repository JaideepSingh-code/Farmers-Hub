package repositories.itemRepository;

import models.Item;
import java.util.List;

public interface ItemRepository {
    List<Item> findAll();
    List<Item> findByFarmerId(int farmerId);
    Item findById(int id);
    void save(Item item);
    void update(Item item);
    void delete(int id);
}
