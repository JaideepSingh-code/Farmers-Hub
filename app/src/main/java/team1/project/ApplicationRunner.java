package team1.project;

import controllers.LoginController;
import services.UserService;
import repositories.userRepository.MockUserRepository;
import repositories.userRepository.PostgresUserRepository;
import statics.DbConfig;
import views.LoginView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ApplicationRunner extends Application {

    @Override
    public void start(Stage primaryStage) {
        UserService userService;
        if (DbConfig.IS_MOCK) {
            userService = new UserService(new MockUserRepository());
        } else {
            userService = new UserService(new PostgresUserRepository());
        }

        LoginController loginController = new LoginController(userService, primaryStage);
        LoginView loginView = new LoginView(primaryStage, loginController);

        Scene scene = new Scene(loginView.getView(), 800, 600);
        primaryStage.setTitle("Farmers Hub");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
