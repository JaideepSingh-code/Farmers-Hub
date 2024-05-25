package models;

public class RatingAndReview {

    private int id;
    private int customerId;
    private int itemId;
    private int rating;
    private String review;
    private String customerName;

    public RatingAndReview(int customerId, int itemId, int rating, String review) {
        this.customerId = customerId;
        this.itemId = itemId;
        this.rating = rating;
        this.review = review;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }

    public int getItemId() { return itemId; }
    public void setItemId(int itemId) { this.itemId = itemId; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    public String getReview() { return review; }
    public void setReview(String review) { this.review = review; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
}
