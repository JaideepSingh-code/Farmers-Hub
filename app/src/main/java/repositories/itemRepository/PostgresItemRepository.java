package repositories.itemRepository;

import models.*;
import statics.DbConfig;
import statics.ItemStatics;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostgresItemRepository implements ItemRepository {

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                DbConfig.DB_CONNECTION_STRING, DbConfig.DB_USER, DbConfig.DB_PASSWORD);
    }

    @Override
    public List<Item> findAll() {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT * FROM items";
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                items.add(mapResultSetToItem(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    @Override
    public List<Item> findByFarmerId(int farmerId) {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT * FROM items WHERE farmer_id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, farmerId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                items.add(mapResultSetToItem(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    @Override
    public Item findById(int id) {
        String sql = "SELECT * FROM items WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToItem(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void save(Item item) {
        String type = (item instanceof Produce) ? ItemStatics.PRODUCE : ItemStatics.MACHINE;
        String sql = "INSERT INTO items (farmer_id, name, description, price, quantity_available, type) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, item.getFarmerId());
            stmt.setString(2, item.getName());
            stmt.setString(3, item.getDescription());
            stmt.setDouble(4, item.getPrice());
            stmt.setInt(5, item.getQuantityAvailable());
            stmt.setString(6, type);
            stmt.executeUpdate();
            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                item.setId(keys.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Item item) {
        String sql = "UPDATE items SET name = ?, description = ?, price = ?, quantity_available = ? WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, item.getName());
            stmt.setString(2, item.getDescription());
            stmt.setDouble(3, item.getPrice());
            stmt.setInt(4, item.getQuantityAvailable());
            stmt.setInt(5, item.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM items WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Item mapResultSetToItem(ResultSet rs) throws SQLException {
        String type = rs.getString("type");
        Item item;
        if (ItemStatics.MACHINE.equals(type)) {
            item = new Machine(rs.getInt("farmer_id"), rs.getString("name"),
                    rs.getString("description"), rs.getDouble("price"), rs.getInt("quantity_available"));
        } else {
            item = new Produce(rs.getInt("farmer_id"), rs.getString("name"),
                    rs.getString("description"), rs.getDouble("price"), rs.getInt("quantity_available"));
        }
        item.setId(rs.getInt("id"));
        return item;
    }
}
