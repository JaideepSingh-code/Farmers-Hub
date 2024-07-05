package views.farmerViews;

import models.User;
import models.Item;
import controllers.ItemController;
import services.ItemService;
import repositories.itemRepository.MockItemRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class FarmerLandingPage {

    private BorderPane view;
    private Stage primaryStage;
    private User currentUser;
    private ItemController itemController;
    private TableView<Item> itemTable;

    public FarmerLandingPage(Stage primaryStage, User currentUser) {
        this.primaryStage = primaryStage;
        this.currentUser = currentUser;
        this.itemController = new ItemController(new ItemService(new MockItemRepository()));
        createView();
    }

    @SuppressWarnings("unchecked")
    private void createView() {
        view = new BorderPane();
        view.setPadding(new Insets(20));

        // Header
        Label welcomeLabel = new Label("Welcome, " + currentUser.getFirstName() + "!");
        welcomeLabel.setFont(Font.font("System", FontWeight.BOLD, 22));

        MenuBar menuBar = new MenuBar();
        Menu profileMenu = new Menu("Profile");
        MenuItem updateProfile = new MenuItem("Update Profile");
        MenuItem changePassword = new MenuItem("Change Password");
        MenuItem logout = new MenuItem("Logout");
        profileMenu.getItems().addAll(updateProfile, changePassword, new SeparatorMenuItem(), logout);
        menuBar.getMenus().add(profileMenu);

        VBox header = new VBox(10, menuBar, welcomeLabel);

        // Buttons
        Button uploadBtn = new Button("Upload New Item");
        uploadBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");

        Button salesBtn = new Button("View Sales History");
        salesBtn.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");

        Button refreshBtn = new Button("Refresh");

        HBox buttonBar = new HBox(10, uploadBtn, salesBtn, refreshBtn);
        buttonBar.setPadding(new Insets(10, 0, 10, 0));

        // Item table
        itemTable = new TableView<>();
        TableColumn<Item, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setPrefWidth(200);

        TableColumn<Item, String> descCol = new TableColumn<>("Description");
        descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        descCol.setPrefWidth(250);

        TableColumn<Item, Double> priceCol = new TableColumn<>("Price ($)");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        priceCol.setPrefWidth(100);

        TableColumn<Item, Integer> qtyCol = new TableColumn<>("Qty Available");
        qtyCol.setCellValueFactory(new PropertyValueFactory<>("quantityAvailable"));
        qtyCol.setPrefWidth(100);

        itemTable.getColumns().addAll(nameCol, descCol, priceCol, qtyCol);
        refreshItems();

        uploadBtn.setOnAction(e -> {
            UploadItemPage uploadPage = new UploadItemPage(primaryStage, currentUser);
            primaryStage.getScene().setRoot(uploadPage.getView());
        });

        salesBtn.setOnAction(e -> {
            SalesHistoryPage salesPage = new SalesHistoryPage(primaryStage, currentUser);
            primaryStage.getScene().setRoot(salesPage.getView());
        });

        refreshBtn.setOnAction(e -> refreshItems());

        VBox center = new VBox(10, buttonBar, itemTable);
        view.setTop(header);
        view.setCenter(center);
    }

    private void refreshItems() {
        ObservableList<Item> items = FXCollections.observableArrayList(
                itemController.getItemsByFarmer(currentUser.getId()));
        itemTable.setItems(items);
    }

    public BorderPane getView() {
        return view;
    }
}
