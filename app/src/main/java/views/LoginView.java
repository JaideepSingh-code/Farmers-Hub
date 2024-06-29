package views;

import controllers.LoginController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import utils.AlertUtils;

public class LoginView {

    private VBox view;
    private Stage primaryStage;
    private LoginController loginController;

    public LoginView(Stage primaryStage, LoginController loginController) {
        this.primaryStage = primaryStage;
        this.loginController = loginController;
        createView();
    }

    private void createView() {
        view = new VBox(15);
        view.setAlignment(Pos.CENTER);
        view.setPadding(new Insets(40));
        view.setMaxWidth(400);

        Label titleLabel = new Label("Farmers Hub");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 28));

        Label subtitleLabel = new Label("Login to your account");
        subtitleLabel.setFont(Font.font("System", 14));

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setMaxWidth(300);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setMaxWidth(300);

        Button loginBtn = new Button("Login");
        loginBtn.setMaxWidth(300);
        loginBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px;");

        Button registerBtn = new Button("Create Account");
        registerBtn.setMaxWidth(300);
        registerBtn.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-size: 14px;");

        loginBtn.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            if (username.isEmpty() || password.isEmpty()) {
                AlertUtils.showError("Login Error", "Please enter username and password.");
                return;
            }
            boolean success = loginController.handleLogin(username, password);
            if (!success) {
                AlertUtils.showError("Login Error", "Invalid username or password.");
            }
        });

        registerBtn.setOnAction(e -> {
            RegistrationPageView regView = new RegistrationPageView(primaryStage);
            primaryStage.getScene().setRoot(regView.getView());
        });

        view.getChildren().addAll(titleLabel, subtitleLabel, usernameField, passwordField, loginBtn, registerBtn);
    }

    public VBox getView() {
        return view;
    }
}
