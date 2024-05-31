package repositories.userRepository;

import models.User;
import java.util.List;

public interface UserRepository {
    User findByUsername(String username);
    User findById(int id);
    List<User> findAll();
    void save(User user, String role);
    void update(User user);
    boolean updatePassword(int userId, String oldPassword, String newPassword);
    boolean usernameExists(String username);
}
