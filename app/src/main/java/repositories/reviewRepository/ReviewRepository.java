package repositories.reviewRepository;

import models.RatingAndReview;
import java.util.List;

public interface ReviewRepository {
    void save(RatingAndReview review);
    List<RatingAndReview> findByItemId(int itemId);
    List<RatingAndReview> findByCustomerId(int customerId);
    List<RatingAndReview> findAll();
}
