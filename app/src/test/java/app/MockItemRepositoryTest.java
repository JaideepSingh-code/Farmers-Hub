package app;

import models.Item;
import models.Produce;
import models.Machine;
import repositories.itemRepository.MockItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class MockItemRepositoryTest {

    private MockItemRepository repository;

    @BeforeEach
    void setUp() {
        repository = new MockItemRepository();
    }

    @Test
    void testFindAll_ReturnsSeededItems() {
        List<Item> items = repository.findAll();
        assertNotNull(items);
        assertEquals(3, items.size());
    }

    @Test
    void testFindByFarmerId() {
        List<Item> items = repository.findByFarmerId(1);
        assertFalse(items.isEmpty());
    }

    @Test
    void testSave_NewProduce() {
        Produce produce = new Produce(1, "Apples", "Fresh red apples", 4.99, 30);
        repository.save(produce);
        assertNotEquals(0, produce.getId());
        Item found = repository.findById(produce.getId());
        assertNotNull(found);
        assertEquals("Apples", found.getName());
    }

    @Test
    void testSave_NewMachine() {
        Machine machine = new Machine(1, "Tractor", "Mini tractor", 5000.00, 2);
        repository.save(machine);
        Item found = repository.findById(machine.getId());
        assertNotNull(found);
        assertTrue(found instanceof Machine);
    }

    @Test
    void testUpdate() {
        List<Item> items = repository.findAll();
        Item item = items.get(0);
        item.setName("Updated Name");
        item.setPrice(9.99);
        repository.update(item);
        Item updated = repository.findById(item.getId());
        assertEquals("Updated Name", updated.getName());
        assertEquals(9.99, updated.getPrice());
    }

    @Test
    void testDelete() {
        List<Item> items = repository.findAll();
        int id = items.get(0).getId();
        repository.delete(id);
        assertNull(repository.findById(id));
        assertEquals(2, repository.findAll().size());
    }

    @Test
    void testUpdateQuantity() {
        Item item = repository.findAll().get(0);
        int originalQty = item.getQuantityAvailable();
        item.updateQuantityAvailable(-5);
        assertEquals(originalQty - 5, item.getQuantityAvailable());
    }
}
