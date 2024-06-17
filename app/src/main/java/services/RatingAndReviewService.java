package services;

import models.RatingAndReview;
import repositories.reviewRepository.ReviewRepository;
import java.util.List;

public class RatingAndReviewService {

    private ReviewRepository reviewRepository;

    public RatingAndReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public void addReview(RatingAndReview review) {
        reviewRepository.save(review);
    }

    public List<RatingAndReview> getReviewsForItem(int itemId) {
        return reviewRepository.findByItemId(itemId);
    }

    public List<RatingAndReview> getReviewsByCustomer(int customerId) {
        return reviewRepository.findByCustomerId(customerId);
    }

    public double getAverageRating(int itemId) {
        List<RatingAndReview> reviews = reviewRepository.findByItemId(itemId);
        if (reviews.isEmpty()) return 0.0;
        return reviews.stream().mapToInt(RatingAndReview::getRating).average().orElse(0.0);
    }
}
