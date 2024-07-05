package views.farmerViews;

import models.User;
import controllers.ItemController;
import services.ItemService;
import repositories.itemRepository.MockItemRepository;
import statics.ItemStatics;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import utils.AlertUtils;

public class UploadItemPage {

    private VBox view;
    private Stage primaryStage;
    private User currentUser;

    public UploadItemPage(Stage primaryStage, User currentUser) {
        this.primaryStage = primaryStage;
        this.currentUser = currentUser;
        createView();
    }

    private void createView() {
        view = new VBox(12);
        view.setAlignment(Pos.CENTER);
        view.setPadding(new Insets(30));
        view.setMaxWidth(450);

        Label titleLabel = new Label("Upload New Item");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 24));

        TextField nameField = new TextField();
        nameField.setPromptText("Item Name");
        nameField.setMaxWidth(350);

        TextArea descField = new TextArea();
        descField.setPromptText("Description");
        descField.setMaxWidth(350);
        descField.setPrefRowCount(3);

        TextField priceField = new TextField();
        priceField.setPromptText("Price");
        priceField.setMaxWidth(350);

        TextField quantityField = new TextField();
        quantityField.setPromptText("Quantity Available");
        quantityField.setMaxWidth(350);

        ToggleGroup typeGroup = new ToggleGroup();
        RadioButton produceRadio = new RadioButton("Produce");
        produceRadio.setToggleGroup(typeGroup);
        produceRadio.setUserData(ItemStatics.PRODUCE);
        produceRadio.setSelected(true);
        RadioButton machineRadio = new RadioButton("Machine");
        machineRadio.setToggleGroup(typeGroup);
        machineRadio.setUserData(ItemStatics.MACHINE);

        HBox typeBox = new HBox(20, new Label("Type:"), produceRadio, machineRadio);
        typeBox.setAlignment(Pos.CENTER);

        Button uploadBtn = new Button("Upload Item");
        uploadBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px;");
        uploadBtn.setMaxWidth(350);

        Button backBtn = new Button("Back to Dashboard");
        backBtn.setMaxWidth(350);

        uploadBtn.setOnAction(e -> {
            String type = typeGroup.getSelectedToggle().getUserData().toString();
            ItemController controller = new ItemController(new ItemService(new MockItemRepository()));
            String error = controller.addItem(currentUser.getId(), nameField.getText(),
                    descField.getText(), priceField.getText(), quantityField.getText(), type);
            if (error != null) {
                AlertUtils.showError("Upload Error", error);
            } else {
                AlertUtils.showSuccess("Item uploaded successfully!");
                FarmerLandingPage farmerPage = new FarmerLandingPage(primaryStage, currentUser);
                primaryStage.getScene().setRoot(farmerPage.getView());
            }
        });

        backBtn.setOnAction(e -> {
            FarmerLandingPage farmerPage = new FarmerLandingPage(primaryStage, currentUser);
            primaryStage.getScene().setRoot(farmerPage.getView());
        });

        view.getChildren().addAll(titleLabel, nameField, descField, priceField,
                quantityField, typeBox, uploadBtn, backBtn);
    }

    public VBox getView() {
        return view;
    }
}
