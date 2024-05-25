package models;

public class Machine extends Item {

    private String manufacturer;
    private int yearOfManufacture;

    public Machine(int farmerId, String name, String description, double price, int quantityAvailable) {
        super(farmerId, name, description, price, quantityAvailable);
    }

    public Machine(int farmerId, String name, String description, double price, int quantityAvailable,
                   String manufacturer, int yearOfManufacture) {
        super(farmerId, name, description, price, quantityAvailable);
        this.manufacturer = manufacturer;
        this.yearOfManufacture = yearOfManufacture;
    }

    public String getManufacturer() { return manufacturer; }
    public void setManufacturer(String manufacturer) { this.manufacturer = manufacturer; }

    public int getYearOfManufacture() { return yearOfManufacture; }
    public void setYearOfManufacture(int yearOfManufacture) { this.yearOfManufacture = yearOfManufacture; }
}
