package models.composite_responses;

import models.Item;
import models.RatingAndReview;
import java.util.List;

public class ItemWithReviews {

    private Item item;
    private List<RatingAndReview> reviews;
    private double averageRating;

    public ItemWithReviews(Item item, List<RatingAndReview> reviews) {
        this.item = item;
        this.reviews = reviews;
        this.averageRating = calculateAverageRating();
    }

    private double calculateAverageRating() {
        if (reviews == null || reviews.isEmpty()) {
            return 0.0;
        }
        return reviews.stream().mapToInt(RatingAndReview::getRating).average().orElse(0.0);
    }

    public Item getItem() { return item; }
    public List<RatingAndReview> getReviews() { return reviews; }
    public double getAverageRating() { return averageRating; }
}
