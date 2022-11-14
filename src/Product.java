import java.util.Comparator;

public class Product {

    private String name;
    private String storeFrontName;
    private String description;
    private int availableQuantity;
    private double price;

    private int numAddedToCarts;

    public Product(String name, String storeFrontName, String description, int availableQuantity, double price) {
        this.name = name;
        this.storeFrontName = storeFrontName;
        this.description = description;
        this.availableQuantity = availableQuantity;
        this.price = price;
        this.numAddedToCarts = 0;
    }

    public Product(String name, String storeFrontName, String description,
                   int availableQuantity, double price, int numAddedToCarts) {
        this.name = name;
        this.storeFrontName = storeFrontName;
        this.description = description;
        this.availableQuantity = availableQuantity;
        this.price = price;
        this.numAddedToCarts = numAddedToCarts;
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

    public int getNumAddedToCarts() {
        return numAddedToCarts;
    }

    public void setNumAddedToCarts(int numAddedToCarts) {
        this.numAddedToCarts = numAddedToCarts;
    }

    public boolean addedToCart(int quantity) {
        if (quantity > availableQuantity) {
            return false;
        } else {
            numAddedToCarts += quantity;
            return true;
        }
    }

    //Found this method usage at https://www.geeksforgeeks.org/how-to-sort-an-arraylist-of-objects-by-property-in-java/
    // desc - true for descending order, false for ascending order
    public static Comparator<Product> priceCompare(boolean desc) {
        return (Product a, Product b) ->
            (desc ? -1 : 1) * Double.compare(a.getPrice(), b.getPrice());
    }

    // desc - true for descending order, false for ascending order
    public static Comparator<Product> availabilityCompare(boolean desc) {
        return (Product a, Product b) ->
            (desc ? -1 : 1) * Integer.compare(a.getAvailableQuantity(), b.getAvailableQuantity());
    }

    public void printProductDetails() {
        System.out.println("Name: " + name);
        System.out.println("Store: " + storeFrontName);
        System.out.println(description);
        System.out.println("Quantity Available: " + availableQuantity);
        System.out.printf("Price: $%.2f%n", price);
    }

    @Override
    public String toString() {
        return "".format("%s::%s::%s::%d::%.2f::%d", name, storeFrontName, 
            description, availableQuantity, price, numAddedToCarts);
    }
}