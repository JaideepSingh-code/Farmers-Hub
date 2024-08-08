package app;

import models.RatingAndReview;
import repositories.reviewRepository.MockReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class MockReviewRepositoryTest {

    private MockReviewRepository repository;

    @BeforeEach
    void setUp() {
        repository = new MockReviewRepository();
    }

    @Test
    void testSave() {
        RatingAndReview review = new RatingAndReview(1, 1, 5, "Great produce!");
        repository.save(review);
        assertNotEquals(0, review.getId());
    }

    @Test
    void testFindByItemId() {
        repository.save(new RatingAndReview(1, 1, 5, "Excellent"));
        repository.save(new RatingAndReview(2, 1, 4, "Very good"));
        repository.save(new RatingAndReview(1, 2, 3, "Okay"));

        List<RatingAndReview> reviews = repository.findByItemId(1);
        assertEquals(2, reviews.size());
    }

    @Test
    void testFindByCustomerId() {
        repository.save(new RatingAndReview(1, 1, 5, "Excellent"));
        repository.save(new RatingAndReview(1, 2, 4, "Good"));
        repository.save(new RatingAndReview(2, 1, 3, "Average"));

        List<RatingAndReview> reviews = repository.findByCustomerId(1);
        assertEquals(2, reviews.size());
    }

    @Test
    void testFindAll_Empty() {
        assertTrue(repository.findAll().isEmpty());
    }

    @Test
    void testFindAll_WithReviews() {
        repository.save(new RatingAndReview(1, 1, 5, "Great"));
        repository.save(new RatingAndReview(2, 2, 4, "Good"));
        assertEquals(2, repository.findAll().size());
    }
}
