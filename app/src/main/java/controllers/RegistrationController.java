package controllers;

import services.UserService;
import utils.ValidationUtils;

public class RegistrationController {

    private UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    public String handleRegistration(String firstName, String lastName, String username,
                                     String password, String confirmPassword, String role) {
        if (!ValidationUtils.isNonEmpty(firstName, lastName, username, password, role)) {
            return "All fields are required.";
        }
        if (!ValidationUtils.isValidUsername(username)) {
            return "Username must be at least 4 characters and contain only letters, numbers, and underscores.";
        }
        if (!ValidationUtils.isValidPassword(password)) {
            return "Password must be at least 6 characters.";
        }
        if (!password.equals(confirmPassword)) {
            return "Passwords do not match.";
        }
        boolean success = userService.register(firstName, lastName, username, password, role);
        if (!success) {
            return "Username already exists. Please choose a different one.";
        }
        return null; // null means success
    }
}
