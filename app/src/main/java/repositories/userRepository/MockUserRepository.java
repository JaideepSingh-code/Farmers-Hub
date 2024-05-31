package repositories.userRepository;

import models.*;
import statics.UserRoles;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MockUserRepository implements UserRepository {

    private Map<Integer, User> users = new HashMap<>();
    private Map<Integer, String> userRoles = new HashMap<>();
    private int nextId = 1;

    public MockUserRepository() {
        // Seed with test data
        Farmer farmer = new Farmer("John", "Doe", "farmer1", "password123");
        farmer.setId(nextId);
        users.put(nextId, farmer);
        userRoles.put(nextId, UserRoles.FARMER);
        nextId++;

        Customer customer = new Customer("Jane", "Smith", "customer1", "password123");
        customer.setId(nextId);
        users.put(nextId, customer);
        userRoles.put(nextId, UserRoles.CUSTOMER);
        nextId++;
    }

    @Override
    public User findByUsername(String username) {
        return users.values().stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    @Override
    public User findById(int id) {
        return users.get(id);
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public void save(User user, String role) {
        user.setId(nextId);
        users.put(nextId, user);
        userRoles.put(nextId, role);
        nextId++;
    }

    @Override
    public void update(User user) {
        users.put(user.getId(), user);
    }

    @Override
    public boolean updatePassword(int userId, String oldPassword, String newPassword) {
        User user = users.get(userId);
        if (user != null && user.getPassword().equals(oldPassword)) {
            user.setPassword(newPassword);
            return true;
        }
        return false;
    }

    @Override
    public boolean usernameExists(String username) {
        return users.values().stream().anyMatch(u -> u.getUsername().equals(username));
    }

    public String getUserRole(int userId) {
        return userRoles.get(userId);
    }
}
