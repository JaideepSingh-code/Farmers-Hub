package repositories.orderItemRepository;

import models.OrderItem;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MockOrderItemRepository implements OrderItemRepository {

    private Map<Integer, OrderItem> orders = new HashMap<>();
    private int nextId = 1;

    @Override
    public void save(OrderItem orderItem) {
        orderItem.setId(nextId);
        orders.put(nextId++, orderItem);
    }

    @Override
    public List<OrderItem> findByCustomerId(int customerId) {
        return orders.values().stream()
                .filter(o -> o.getCustomerId() == customerId)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderItem> findByFarmerId(int farmerId) {
        return orders.values().stream()
                .filter(o -> o.getFarmerId() == farmerId)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderItem> findAll() {
        return new ArrayList<>(orders.values());
    }
}
