package app;

import models.User;
import services.UserService;
import repositories.userRepository.MockUserRepository;
import statics.UserRoles;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserServiceIntegTest {

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService(new MockUserRepository());
    }

    @Test
    void testLoginWithValidCredentials() {
        User user = userService.login("farmer1", "password123");
        assertNotNull(user);
        assertEquals("farmer1", user.getUsername());
    }

    @Test
    void testLoginWithInvalidCredentials() {
        User user = userService.login("farmer1", "wrongpassword");
        assertNull(user);
    }

    @Test
    void testRegisterNewUser() {
        boolean success = userService.register("New", "User", "newuser", "pass123", UserRoles.CUSTOMER);
        assertTrue(success);
        User user = userService.login("newuser", "pass123");
        assertNotNull(user);
    }

    @Test
    void testRegisterDuplicateUsername() {
        boolean success = userService.register("Dup", "User", "farmer1", "pass123", UserRoles.FARMER);
        assertFalse(success);
    }

    @Test
    void testChangePasswordFlow() {
        User user = userService.login("farmer1", "password123");
        assertNotNull(user);
        boolean changed = userService.changePassword(user.getId(), "password123", "newpass");
        assertTrue(changed);
        User loginWithNew = userService.login("farmer1", "newpass");
        assertNotNull(loginWithNew);
    }
}
