package app;

import models.OrderItem;
import repositories.orderItemRepository.MockOrderItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class MockOrderItemRepositoryTest {

    private MockOrderItemRepository repository;

    @BeforeEach
    void setUp() {
        repository = new MockOrderItemRepository();
    }

    @Test
    void testSave() {
        OrderItem order = new OrderItem(1, 1, 1, "Tomatoes", 5, 19.95);
        repository.save(order);
        assertNotEquals(0, order.getId());
    }

    @Test
    void testFindByCustomerId() {
        OrderItem order1 = new OrderItem(1, 1, 1, "Tomatoes", 5, 19.95);
        OrderItem order2 = new OrderItem(1, 2, 1, "Corn", 10, 24.90);
        OrderItem order3 = new OrderItem(2, 1, 1, "Tomatoes", 3, 11.97);
        repository.save(order1);
        repository.save(order2);
        repository.save(order3);

        List<OrderItem> customerOrders = repository.findByCustomerId(1);
        assertEquals(2, customerOrders.size());
    }

    @Test
    void testFindByFarmerId() {
        OrderItem order1 = new OrderItem(1, 1, 1, "Tomatoes", 5, 19.95);
        OrderItem order2 = new OrderItem(2, 2, 2, "Tractor", 1, 5000.00);
        repository.save(order1);
        repository.save(order2);

        List<OrderItem> farmer1Sales = repository.findByFarmerId(1);
        assertEquals(1, farmer1Sales.size());
    }

    @Test
    void testFindAll() {
        repository.save(new OrderItem(1, 1, 1, "Tomatoes", 5, 19.95));
        repository.save(new OrderItem(2, 2, 1, "Corn", 3, 7.47));
        assertEquals(2, repository.findAll().size());
    }
}
