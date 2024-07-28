package repositories.orderItemRepository;

import models.OrderItem;
import statics.DbConfig;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostgresOrderItemRepository implements OrderItemRepository {

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                DbConfig.DB_CONNECTION_STRING, DbConfig.DB_USER, DbConfig.DB_PASSWORD);
    }

    @Override
    public void save(OrderItem orderItem) {
        String sql = "INSERT INTO order_items (customer_id, item_id, farmer_id, item_name, quantity, total_price, order_date) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, orderItem.getCustomerId());
            stmt.setInt(2, orderItem.getItemId());
            stmt.setInt(3, orderItem.getFarmerId());
            stmt.setString(4, orderItem.getItemName());
            stmt.setInt(5, orderItem.getQuantity());
            stmt.setDouble(6, orderItem.getTotalPrice());
            stmt.setTimestamp(7, Timestamp.valueOf(orderItem.getOrderDate()));
            stmt.executeUpdate();
            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                orderItem.setId(keys.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<OrderItem> findByCustomerId(int customerId) {
        return findByField("customer_id", customerId);
    }

    @Override
    public List<OrderItem> findByFarmerId(int farmerId) {
        return findByField("farmer_id", farmerId);
    }

    @Override
    public List<OrderItem> findAll() {
        List<OrderItem> orders = new ArrayList<>();
        String sql = "SELECT * FROM order_items";
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                orders.add(mapResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    private List<OrderItem> findByField(String field, int value) {
        List<OrderItem> orders = new ArrayList<>();
        String sql = "SELECT * FROM order_items WHERE " + field + " = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, value);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                orders.add(mapResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    private OrderItem mapResultSet(ResultSet rs) throws SQLException {
        OrderItem order = new OrderItem(
                rs.getInt("customer_id"), rs.getInt("item_id"), rs.getInt("farmer_id"),
                rs.getString("item_name"), rs.getInt("quantity"), rs.getDouble("total_price"));
        order.setId(rs.getInt("id"));
        order.setOrderDate(rs.getTimestamp("order_date").toLocalDateTime());
        return order;
    }
}
