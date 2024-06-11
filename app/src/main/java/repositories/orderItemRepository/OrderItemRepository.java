package repositories.orderItemRepository;

import models.OrderItem;
import java.util.List;

public interface OrderItemRepository {
    void save(OrderItem orderItem);
    List<OrderItem> findByCustomerId(int customerId);
    List<OrderItem> findByFarmerId(int farmerId);
    List<OrderItem> findAll();
}
