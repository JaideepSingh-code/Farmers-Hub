package views.customerViews;

import models.User;
import models.OrderItem;
import services.OrderService;
import repositories.orderItemRepository.MockOrderItemRepository;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.time.LocalDateTime;

public class OrderHistoryPage {

    private BorderPane view;
    private Stage primaryStage;
    private User currentUser;

    public OrderHistoryPage(Stage primaryStage, User currentUser) {
        this.primaryStage = primaryStage;
        this.currentUser = currentUser;
        createView();
    }

    @SuppressWarnings("unchecked")
    private void createView() {
        view = new BorderPane();
        view.setPadding(new Insets(20));

        Label titleLabel = new Label("Order History");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 22));

        OrderService orderService = new OrderService(new MockOrderItemRepository());

        TableView<OrderItem> orderTable = new TableView<>();

        TableColumn<OrderItem, String> nameCol = new TableColumn<>("Item");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        nameCol.setPrefWidth(200);

        TableColumn<OrderItem, Integer> qtyCol = new TableColumn<>("Qty");
        qtyCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        qtyCol.setPrefWidth(80);

        TableColumn<OrderItem, Double> priceCol = new TableColumn<>("Total ($)");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        priceCol.setPrefWidth(100);

        TableColumn<OrderItem, LocalDateTime> dateCol = new TableColumn<>("Order Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        dateCol.setPrefWidth(200);

        orderTable.getColumns().addAll(nameCol, qtyCol, priceCol, dateCol);
        orderTable.setItems(FXCollections.observableArrayList(
                orderService.getOrdersByCustomer(currentUser.getId())));

        Button backBtn = new Button("Back to Shopping");
        backBtn.setOnAction(e -> {
            CustomerLandingPage customerPage = new CustomerLandingPage(primaryStage, currentUser);
            primaryStage.getScene().setRoot(customerPage.getView());
        });

        view.setTop(titleLabel);
        view.setCenter(orderTable);
        view.setBottom(backBtn);
        BorderPane.setMargin(backBtn, new Insets(10, 0, 0, 0));
    }

    public BorderPane getView() {
        return view;
    }
}
