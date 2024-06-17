package services;

import models.*;
import repositories.userRepository.UserRepository;
import statics.UserRoles;

public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User login(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public boolean register(String firstName, String lastName, String username, String password, String role) {
        if (userRepository.usernameExists(username)) {
            return false;
        }
        User user;
        if (UserRoles.FARMER.equals(role)) {
            user = new Farmer(firstName, lastName, username, password);
        } else {
            user = new Customer(firstName, lastName, username, password);
        }
        userRepository.save(user, role);
        return true;
    }

    public boolean changePassword(int userId, String oldPassword, String newPassword) {
        return userRepository.updatePassword(userId, oldPassword, newPassword);
    }

    public void updateProfile(User user) {
        userRepository.update(user);
    }

    public User getUserById(int id) {
        return userRepository.findById(id);
    }
}
