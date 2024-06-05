package repositories.itemRepository;

import models.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MockItemRepository implements ItemRepository {

    private Map<Integer, Item> items = new HashMap<>();
    private int nextId = 1;

    public MockItemRepository() {
        Produce p1 = new Produce(1, "Organic Tomatoes", "Fresh organic tomatoes from the farm", 3.99, 50);
        p1.setId(nextId);
        items.put(nextId++, p1);

        Produce p2 = new Produce(1, "Sweet Corn", "Freshly picked sweet corn", 2.49, 100);
        p2.setId(nextId);
        items.put(nextId++, p2);

        Machine m1 = new Machine(1, "Hand Tiller", "Manual garden tiller for small plots", 89.99, 5);
        m1.setId(nextId);
        items.put(nextId++, m1);
    }

    @Override
    public List<Item> findAll() {
        return new ArrayList<>(items.values());
    }

    @Override
    public List<Item> findByFarmerId(int farmerId) {
        return items.values().stream()
                .filter(item -> item.getFarmerId() == farmerId)
                .collect(Collectors.toList());
    }

    @Override
    public Item findById(int id) {
        return items.get(id);
    }

    @Override
    public void save(Item item) {
        item.setId(nextId);
        items.put(nextId++, item);
    }

    @Override
    public void update(Item item) {
        if (items.containsKey(item.getId())) {
            items.put(item.getId(), item);
        }
    }

    @Override
    public void delete(int id) {
        items.remove(id);
    }
}
