public class Product {

    private String name;
    private String storeFrontName;
    private String description;
    private int availableQuantity;
    private double price;

    public Product(String name, String storeFrontName, String description, int availableQuantity, double price) {
        this.name = name;
        this.storeFrontName = storeFrontName;
        this.description = description;
        this.availableQuantity = availableQuantity;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStoreFrontName() {
        return storeFrontName;
    }

    public void setStoreFrontName(String storeFrontName) {
        this.storeFrontName = storeFrontName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
