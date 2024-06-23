package controllers;

import models.User;
import models.Farmer;
import services.UserService;
import views.farmerViews.FarmerLandingPage;
import views.customerViews.CustomerLandingPage;
import javafx.stage.Stage;

public class LoginController {

    private UserService userService;
    private Stage primaryStage;

    public LoginController(UserService userService, Stage primaryStage) {
        this.userService = userService;
        this.primaryStage = primaryStage;
    }

    public boolean handleLogin(String username, String password) {
        User user = userService.login(username, password);
        if (user != null) {
            if (user instanceof Farmer) {
                FarmerLandingPage farmerPage = new FarmerLandingPage(primaryStage, user);
                primaryStage.getScene().setRoot(farmerPage.getView());
            } else {
                CustomerLandingPage customerPage = new CustomerLandingPage(primaryStage, user);
                primaryStage.getScene().setRoot(customerPage.getView());
            }
            return true;
        }
        return false;
    }
}
