package views;

import models.User;
import services.UserService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import utils.AlertUtils;

public class UpdateProfilePage {

    private VBox view;
    private Stage primaryStage;
    private User currentUser;
    private UserService userService;

    public UpdateProfilePage(Stage primaryStage, User currentUser, UserService userService) {
        this.primaryStage = primaryStage;
        this.currentUser = currentUser;
        this.userService = userService;
        createView();
    }

    private void createView() {
        view = new VBox(15);
        view.setAlignment(Pos.CENTER);
        view.setPadding(new Insets(30));
        view.setMaxWidth(400);

        Label titleLabel = new Label("Update Profile");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 24));

        TextField firstNameField = new TextField(currentUser.getFirstName());
        firstNameField.setPromptText("First Name");
        firstNameField.setMaxWidth(300);

        TextField lastNameField = new TextField(currentUser.getLastName());
        lastNameField.setPromptText("Last Name");
        lastNameField.setMaxWidth(300);

        Label changePasswordLabel = new Label("Change Password");
        changePasswordLabel.setFont(Font.font("System", FontWeight.BOLD, 16));

        PasswordField oldPasswordField = new PasswordField();
        oldPasswordField.setPromptText("Current Password");
        oldPasswordField.setMaxWidth(300);

        PasswordField newPasswordField = new PasswordField();
        newPasswordField.setPromptText("New Password");
        newPasswordField.setMaxWidth(300);

        PasswordField confirmNewPasswordField = new PasswordField();
        confirmNewPasswordField.setPromptText("Confirm New Password");
        confirmNewPasswordField.setMaxWidth(300);

        Button saveBtn = new Button("Save Changes");
        saveBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        saveBtn.setMaxWidth(300);

        Button changePasswordBtn = new Button("Change Password");
        changePasswordBtn.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white;");
        changePasswordBtn.setMaxWidth(300);

        Button backBtn = new Button("Back");
        backBtn.setMaxWidth(300);

        saveBtn.setOnAction(e -> {
            currentUser.setFirstName(firstNameField.getText());
            currentUser.setLastName(lastNameField.getText());
            userService.updateProfile(currentUser);
            AlertUtils.showSuccess("Profile updated successfully!");
        });

        changePasswordBtn.setOnAction(e -> {
            String oldPass = oldPasswordField.getText();
            String newPass = newPasswordField.getText();
            String confirmPass = confirmNewPasswordField.getText();
            if (!newPass.equals(confirmPass)) {
                AlertUtils.showError("Error", "New passwords do not match.");
                return;
            }
            boolean success = userService.changePassword(currentUser.getId(), oldPass, newPass);
            if (success) {
                AlertUtils.showSuccess("Password changed successfully!");
            } else {
                AlertUtils.showError("Error", "Current password is incorrect.");
            }
        });

        view.getChildren().addAll(titleLabel, firstNameField, lastNameField,
                saveBtn, new Separator(), changePasswordLabel,
                oldPasswordField, newPasswordField, confirmNewPasswordField,
                changePasswordBtn, backBtn);
    }

    public VBox getView() {
        return view;
    }
}
