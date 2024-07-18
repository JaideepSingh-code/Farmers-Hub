package views.customerViews;

import models.User;
import models.RatingAndReview;
import controllers.ReviewController;
import services.RatingAndReviewService;
import repositories.reviewRepository.MockReviewRepository;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import utils.AlertUtils;

public class ReviewPage {

    private BorderPane view;
    private Stage primaryStage;
    private User currentUser;
    private ReviewController reviewController;

    public ReviewPage(Stage primaryStage, User currentUser) {
        this.primaryStage = primaryStage;
        this.currentUser = currentUser;
        this.reviewController = new ReviewController(
                new RatingAndReviewService(new MockReviewRepository()));
        createView();
    }

    @SuppressWarnings("unchecked")
    private void createView() {
        view = new BorderPane();
        view.setPadding(new Insets(20));

        Label titleLabel = new Label("Reviews");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 22));

        // Add review form
        Label formLabel = new Label("Leave a Review");
        formLabel.setFont(Font.font("System", FontWeight.BOLD, 16));

        TextField itemIdField = new TextField();
        itemIdField.setPromptText("Item ID");
        itemIdField.setMaxWidth(200);

        Spinner<Integer> ratingSpinner = new Spinner<>(1, 5, 5);
        ratingSpinner.setPrefWidth(80);

        TextArea reviewTextArea = new TextArea();
        reviewTextArea.setPromptText("Write your review...");
        reviewTextArea.setPrefRowCount(3);
        reviewTextArea.setMaxWidth(400);

        Button submitBtn = new Button("Submit Review");
        submitBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");

        submitBtn.setOnAction(e -> {
            try {
                int itemId = Integer.parseInt(itemIdField.getText());
                String error = reviewController.addReview(currentUser.getId(), itemId,
                        ratingSpinner.getValue(), reviewTextArea.getText());
                if (error != null) {
                    AlertUtils.showError("Error", error);
                } else {
                    AlertUtils.showSuccess("Review submitted!");
                    itemIdField.clear();
                    reviewTextArea.clear();
                }
            } catch (NumberFormatException ex) {
                AlertUtils.showError("Error", "Please enter a valid Item ID.");
            }
        });

        VBox form = new VBox(8, formLabel, itemIdField,
                new HBox(10, new Label("Rating:"), ratingSpinner),
                reviewTextArea, submitBtn);
        form.setAlignment(Pos.CENTER_LEFT);

        Button backBtn = new Button("Back to Shopping");
        backBtn.setOnAction(e -> {
            CustomerLandingPage customerPage = new CustomerLandingPage(primaryStage, currentUser);
            primaryStage.getScene().setRoot(customerPage.getView());
        });

        VBox top = new VBox(10, titleLabel);
        view.setTop(top);
        view.setCenter(form);
        view.setBottom(backBtn);
        BorderPane.setMargin(backBtn, new Insets(10, 0, 0, 0));
    }

    public BorderPane getView() {
        return view;
    }
}
