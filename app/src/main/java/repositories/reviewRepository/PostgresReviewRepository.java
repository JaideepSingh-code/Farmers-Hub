package repositories.reviewRepository;

import models.RatingAndReview;
import statics.DbConfig;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostgresReviewRepository implements ReviewRepository {

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                DbConfig.DB_CONNECTION_STRING, DbConfig.DB_USER, DbConfig.DB_PASSWORD);
    }

    @Override
    public void save(RatingAndReview review) {
        String sql = "INSERT INTO reviews (customer_id, item_id, rating, review) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, review.getCustomerId());
            stmt.setInt(2, review.getItemId());
            stmt.setInt(3, review.getRating());
            stmt.setString(4, review.getReview());
            stmt.executeUpdate();
            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                review.setId(keys.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<RatingAndReview> findByItemId(int itemId) {
        return findByField("item_id", itemId);
    }

    @Override
    public List<RatingAndReview> findByCustomerId(int customerId) {
        return findByField("customer_id", customerId);
    }

    @Override
    public List<RatingAndReview> findAll() {
        List<RatingAndReview> reviews = new ArrayList<>();
        String sql = "SELECT * FROM reviews";
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) { reviews.add(mapResultSet(rs)); }
        } catch (SQLException e) { e.printStackTrace(); }
        return reviews;
    }

    private List<RatingAndReview> findByField(String field, int value) {
        List<RatingAndReview> reviews = new ArrayList<>();
        String sql = "SELECT * FROM reviews WHERE " + field + " = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, value);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) { reviews.add(mapResultSet(rs)); }
        } catch (SQLException e) { e.printStackTrace(); }
        return reviews;
    }

    private RatingAndReview mapResultSet(ResultSet rs) throws SQLException {
        RatingAndReview review = new RatingAndReview(
                rs.getInt("customer_id"), rs.getInt("item_id"),
                rs.getInt("rating"), rs.getString("review"));
        review.setId(rs.getInt("id"));
        return review;
    }
}
