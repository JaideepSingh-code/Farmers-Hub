package app;

import models.User;
import models.Farmer;
import models.Customer;
import repositories.userRepository.MockUserRepository;
import statics.UserRoles;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MockUserRepositoryTest {

    private MockUserRepository repository;

    @BeforeEach
    void setUp() {
        repository = new MockUserRepository();
    }

    @Test
    void testFindByUsername_ExistingUser() {
        User user = repository.findByUsername("farmer1");
        assertNotNull(user);
        assertEquals("farmer1", user.getUsername());
        assertEquals("John", user.getFirstName());
    }

    @Test
    void testFindByUsername_NonExistingUser() {
        User user = repository.findByUsername("nonexistent");
        assertNull(user);
    }

    @Test
    void testSave_NewFarmer() {
        Farmer farmer = new Farmer("Test", "Farmer", "testfarmer", "pass123");
        repository.save(farmer, UserRoles.FARMER);
        assertNotEquals(0, farmer.getId());
        User found = repository.findByUsername("testfarmer");
        assertNotNull(found);
        assertEquals("Test", found.getFirstName());
    }

    @Test
    void testSave_NewCustomer() {
        Customer customer = new Customer("Test", "Customer", "testcustomer", "pass123");
        repository.save(customer, UserRoles.CUSTOMER);
        User found = repository.findByUsername("testcustomer");
        assertNotNull(found);
        assertTrue(found instanceof Customer);
    }

    @Test
    void testUsernameExists() {
        assertTrue(repository.usernameExists("farmer1"));
        assertFalse(repository.usernameExists("nonexistent"));
    }

    @Test
    void testUpdatePassword_CorrectOldPassword() {
        User user = repository.findByUsername("farmer1");
        assertTrue(repository.updatePassword(user.getId(), "password123", "newpass"));
        User updated = repository.findById(user.getId());
        assertEquals("newpass", updated.getPassword());
    }

    @Test
    void testUpdatePassword_WrongOldPassword() {
        User user = repository.findByUsername("farmer1");
        assertFalse(repository.updatePassword(user.getId(), "wrongpass", "newpass"));
    }

    @Test
    void testFindById() {
        User user = repository.findByUsername("farmer1");
        User found = repository.findById(user.getId());
        assertNotNull(found);
        assertEquals(user.getUsername(), found.getUsername());
    }

    @Test
    void testFindAll() {
        assertFalse(repository.findAll().isEmpty());
        assertEquals(2, repository.findAll().size());
    }
}
