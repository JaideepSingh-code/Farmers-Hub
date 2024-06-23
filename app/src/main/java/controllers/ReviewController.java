package controllers;

import models.RatingAndReview;
import services.RatingAndReviewService;
import java.util.List;

public class ReviewController {

    private RatingAndReviewService reviewService;

    public ReviewController(RatingAndReviewService reviewService) {
        this.reviewService = reviewService;
    }

    public String addReview(int customerId, int itemId, int rating, String reviewText) {
        if (rating < 1 || rating > 5) {
            return "Rating must be between 1 and 5.";
        }
        if (reviewText == null || reviewText.trim().isEmpty()) {
            return "Review text cannot be empty.";
        }
        RatingAndReview review = new RatingAndReview(customerId, itemId, rating, reviewText);
        reviewService.addReview(review);
        return null;
    }

    public List<RatingAndReview> getReviewsForItem(int itemId) {
        return reviewService.getReviewsForItem(itemId);
    }

    public double getAverageRating(int itemId) {
        return reviewService.getAverageRating(itemId);
    }
}
