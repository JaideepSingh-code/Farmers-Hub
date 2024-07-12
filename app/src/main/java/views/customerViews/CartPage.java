package views.customerViews;

import models.User;
import models.OrderItem;
import controllers.OrderController;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import utils.AlertUtils;

public class CartPage {

    private BorderPane view;
    private Stage primaryStage;
    private User currentUser;
    private OrderController orderController;

    public CartPage(Stage primaryStage, User currentUser, OrderController orderController) {
        this.primaryStage = primaryStage;
        this.currentUser = currentUser;
        this.orderController = orderController;
        createView();
    }

    @SuppressWarnings("unchecked")
    private void createView() {
        view = new BorderPane();
        view.setPadding(new Insets(20));

        Label titleLabel = new Label("Shopping Cart");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 22));

        TableView<OrderItem> cartTable = new TableView<>();

        TableColumn<OrderItem, String> nameCol = new TableColumn<>("Item");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        nameCol.setPrefWidth(200);

        TableColumn<OrderItem, Integer> qtyCol = new TableColumn<>("Qty");
        qtyCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        qtyCol.setPrefWidth(80);

        TableColumn<OrderItem, Double> priceCol = new TableColumn<>("Total ($)");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        priceCol.setPrefWidth(100);

        cartTable.getColumns().addAll(nameCol, qtyCol, priceCol);
        cartTable.setItems(FXCollections.observableArrayList(orderController.getCart()));

        Label totalLabel = new Label(String.format("Cart Total: $%.2f", orderController.getCartTotal()));
        totalLabel.setFont(Font.font("System", FontWeight.BOLD, 16));

        Button checkoutBtn = new Button("Checkout");
        checkoutBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px;");

        Button clearBtn = new Button("Clear Cart");
        clearBtn.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");

        Button backBtn = new Button("Continue Shopping");

        checkoutBtn.setOnAction(e -> {
            if (orderController.getCart().isEmpty()) {
                AlertUtils.showError("Error", "Cart is empty.");
                return;
            }
            String error = orderController.checkout();
            if (error != null) {
                AlertUtils.showError("Checkout Error", error);
            } else {
                AlertUtils.showSuccess("Order placed successfully!");
                CustomerLandingPage customerPage = new CustomerLandingPage(primaryStage, currentUser);
                primaryStage.getScene().setRoot(customerPage.getView());
            }
        });

        clearBtn.setOnAction(e -> {
            orderController.clearCart();
            cartTable.setItems(FXCollections.observableArrayList());
            totalLabel.setText("Cart Total: $0.00");
        });

        backBtn.setOnAction(e -> {
            CustomerLandingPage customerPage = new CustomerLandingPage(primaryStage, currentUser);
            primaryStage.getScene().setRoot(customerPage.getView());
        });

        HBox buttons = new HBox(10, checkoutBtn, clearBtn, backBtn);

        view.setTop(titleLabel);
        view.setCenter(cartTable);
        VBox bottom = new VBox(10, totalLabel, buttons);
        bottom.setPadding(new Insets(10, 0, 0, 0));
        view.setBottom(bottom);
    }

    public BorderPane getView() {
        return view;
    }
}
