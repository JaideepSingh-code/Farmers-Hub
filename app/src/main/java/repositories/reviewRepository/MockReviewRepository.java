package repositories.reviewRepository;

import models.RatingAndReview;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MockReviewRepository implements ReviewRepository {

    private Map<Integer, RatingAndReview> reviews = new HashMap<>();
    private int nextId = 1;

    @Override
    public void save(RatingAndReview review) {
        review.setId(nextId);
        reviews.put(nextId++, review);
    }

    @Override
    public List<RatingAndReview> findByItemId(int itemId) {
        return reviews.values().stream()
                .filter(r -> r.getItemId() == itemId)
                .collect(Collectors.toList());
    }

    @Override
    public List<RatingAndReview> findByCustomerId(int customerId) {
        return reviews.values().stream()
                .filter(r -> r.getCustomerId() == customerId)
                .collect(Collectors.toList());
    }

    @Override
    public List<RatingAndReview> findAll() {
        return new ArrayList<>(reviews.values());
    }
}
