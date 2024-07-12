package views.customerViews;

import models.User;
import models.Item;
import controllers.ItemController;
import controllers.OrderController;
import services.ItemService;
import services.OrderService;
import repositories.itemRepository.MockItemRepository;
import repositories.orderItemRepository.MockOrderItemRepository;
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

public class CustomerLandingPage {

    private BorderPane view;
    private Stage primaryStage;
    private User currentUser;
    private ItemController itemController;
    private OrderController orderController;

    public CustomerLandingPage(Stage primaryStage, User currentUser) {
        this.primaryStage = primaryStage;
        this.currentUser = currentUser;
        ItemService itemService = new ItemService(new MockItemRepository());
        this.itemController = new ItemController(itemService);
        this.orderController = new OrderController(
                new OrderService(new MockOrderItemRepository()), itemService);
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
        MenuItem logout = new MenuItem("Logout");
        profileMenu.getItems().addAll(updateProfile, new SeparatorMenuItem(), logout);
        menuBar.getMenus().add(profileMenu);

        // Navigation buttons
        Button cartBtn = new Button("View Cart");
        cartBtn.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white;");

        Button ordersBtn = new Button("Order History");
        ordersBtn.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");

        Button reviewsBtn = new Button("My Reviews");
        reviewsBtn.setStyle("-fx-background-color: #9C27B0; -fx-text-fill: white;");

        HBox navBar = new HBox(10, cartBtn, ordersBtn, reviewsBtn);
        navBar.setPadding(new Insets(10, 0, 10, 0));

        // Items table
        TableView<Item> itemTable = new TableView<>();
        TableColumn<Item, String> nameCol = new TableColumn<>("Item Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setPrefWidth(180);

        TableColumn<Item, String> descCol = new TableColumn<>("Description");
        descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        descCol.setPrefWidth(220);

        TableColumn<Item, Double> priceCol = new TableColumn<>("Price ($)");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        priceCol.setPrefWidth(90);

        TableColumn<Item, Integer> qtyCol = new TableColumn<>("Available");
        qtyCol.setCellValueFactory(new PropertyValueFactory<>("quantityAvailable"));
        qtyCol.setPrefWidth(80);

        itemTable.getColumns().addAll(nameCol, descCol, priceCol, qtyCol);
        itemTable.setItems(FXCollections.observableArrayList(itemController.getAllItems()));

        // Add to cart
        Spinner<Integer> qtySpinner = new Spinner<>(1, 100, 1);
        qtySpinner.setPrefWidth(80);
        Button addToCartBtn = new Button("Add to Cart");
        addToCartBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");

        addToCartBtn.setOnAction(e -> {
            Item selected = itemTable.getSelectionModel().getSelectedItem();
            if (selected == null) {
                AlertUtils.showError("Error", "Please select an item.");
                return;
            }
            String error = orderController.addToCart(currentUser.getId(),
                    selected.getId(), qtySpinner.getValue());
            if (error != null) {
                AlertUtils.showError("Error", error);
            } else {
                AlertUtils.showSuccess(selected.getName() + " added to cart!");
            }
        });

        HBox addToCartBar = new HBox(10, new Label("Quantity:"), qtySpinner, addToCartBtn);
        addToCartBar.setAlignment(Pos.CENTER_LEFT);
        addToCartBar.setPadding(new Insets(10, 0, 0, 0));

        cartBtn.setOnAction(e -> {
            CartPage cartPage = new CartPage(primaryStage, currentUser, orderController);
            primaryStage.getScene().setRoot(cartPage.getView());
        });

        ordersBtn.setOnAction(e -> {
            OrderHistoryPage orderPage = new OrderHistoryPage(primaryStage, currentUser);
            primaryStage.getScene().setRoot(orderPage.getView());
        });

        reviewsBtn.setOnAction(e -> {
            ReviewPage reviewPage = new ReviewPage(primaryStage, currentUser);
            primaryStage.getScene().setRoot(reviewPage.getView());
        });

        VBox top = new VBox(5, menuBar, welcomeLabel, navBar);
        VBox center = new VBox(10, itemTable, addToCartBar);
        view.setTop(top);
        view.setCenter(center);
    }

    public BorderPane getView() {
        return view;
    }
}
