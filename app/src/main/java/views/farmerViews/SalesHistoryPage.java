package views.farmerViews;

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
import java.util.List;

public class SalesHistoryPage {

    private BorderPane view;
    private Stage primaryStage;
    private User currentUser;

    public SalesHistoryPage(Stage primaryStage, User currentUser) {
        this.primaryStage = primaryStage;
        this.currentUser = currentUser;
        createView();
    }

    @SuppressWarnings("unchecked")
    private void createView() {
        view = new BorderPane();
        view.setPadding(new Insets(20));

        Label titleLabel = new Label("Sales History");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 22));

        OrderService orderService = new OrderService(new MockOrderItemRepository());
        List<OrderItem> sales = orderService.getSalesByFarmer(currentUser.getId());

        double totalRevenue = sales.stream().mapToDouble(OrderItem::getTotalPrice).sum();
        Label revenueLabel = new Label(String.format("Total Revenue: $%.2f", totalRevenue));
        revenueLabel.setFont(Font.font("System", FontWeight.BOLD, 16));

        TableView<OrderItem> salesTable = new TableView<>();

        TableColumn<OrderItem, String> itemCol = new TableColumn<>("Item");
        itemCol.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        itemCol.setPrefWidth(200);

        TableColumn<OrderItem, Integer> qtyCol = new TableColumn<>("Qty");
        qtyCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        qtyCol.setPrefWidth(80);

        TableColumn<OrderItem, Double> priceCol = new TableColumn<>("Total ($)");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        priceCol.setPrefWidth(100);

        TableColumn<OrderItem, LocalDateTime> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        dateCol.setPrefWidth(200);

        salesTable.getColumns().addAll(itemCol, qtyCol, priceCol, dateCol);
        salesTable.setItems(FXCollections.observableArrayList(sales));

        Button backBtn = new Button("Back to Dashboard");
        backBtn.setOnAction(e -> {
            FarmerLandingPage farmerPage = new FarmerLandingPage(primaryStage, currentUser);
            primaryStage.getScene().setRoot(farmerPage.getView());
        });

        VBox top = new VBox(10, titleLabel, revenueLabel);
        view.setTop(top);
        view.setCenter(salesTable);
        view.setBottom(backBtn);
        BorderPane.setMargin(backBtn, new Insets(10, 0, 0, 0));
    }

    public BorderPane getView() {
        return view;
    }
}
