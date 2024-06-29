package views;

import controllers.RegistrationController;
import services.UserService;
import repositories.userRepository.MockUserRepository;
import statics.UserRoles;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import utils.AlertUtils;

public class RegistrationPageView {

    private VBox view;
    private Stage primaryStage;

    public RegistrationPageView(Stage primaryStage) {
        this.primaryStage = primaryStage;
        createView();
    }

    private void createView() {
        view = new VBox(12);
        view.setAlignment(Pos.CENTER);
        view.setPadding(new Insets(30));
        view.setMaxWidth(400);

        Label titleLabel = new Label("Create Account");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 24));

        TextField firstNameField = new TextField();
        firstNameField.setPromptText("First Name");
        firstNameField.setMaxWidth(300);

        TextField lastNameField = new TextField();
        lastNameField.setPromptText("Last Name");
        lastNameField.setMaxWidth(300);

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setMaxWidth(300);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setMaxWidth(300);

        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Confirm Password");
        confirmPasswordField.setMaxWidth(300);

        ToggleGroup roleGroup = new ToggleGroup();
        RadioButton farmerRadio = new RadioButton("Farmer");
        farmerRadio.setToggleGroup(roleGroup);
        farmerRadio.setUserData(UserRoles.FARMER);
        RadioButton customerRadio = new RadioButton("Customer");
        customerRadio.setToggleGroup(roleGroup);
        customerRadio.setUserData(UserRoles.CUSTOMER);
        customerRadio.setSelected(true);

        HBox roleBox = new HBox(20, farmerRadio, customerRadio);
        roleBox.setAlignment(Pos.CENTER);

        Button registerBtn = new Button("Register");
        registerBtn.setMaxWidth(300);
        registerBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px;");

        Button backBtn = new Button("Back to Login");
        backBtn.setMaxWidth(300);

        registerBtn.setOnAction(e -> {
            String role = roleGroup.getSelectedToggle().getUserData().toString();
            RegistrationController controller = new RegistrationController(
                    new UserService(new MockUserRepository()));
            String error = controller.handleRegistration(
                    firstNameField.getText(), lastNameField.getText(),
                    usernameField.getText(), passwordField.getText(),
                    confirmPasswordField.getText(), role);
            if (error != null) {
                AlertUtils.showError("Registration Error", error);
            } else {
                AlertUtils.showSuccess("Account created successfully! Please login.");
                LoginView loginView = new LoginView(primaryStage, null);
                primaryStage.getScene().setRoot(loginView.getView());
            }
        });

        backBtn.setOnAction(e -> {
            LoginView loginView = new LoginView(primaryStage, null);
            primaryStage.getScene().setRoot(loginView.getView());
        });

        view.getChildren().addAll(titleLabel, firstNameField, lastNameField,
                usernameField, passwordField, confirmPasswordField, roleBox, registerBtn, backBtn);
    }

    public VBox getView() {
        return view;
    }
}
