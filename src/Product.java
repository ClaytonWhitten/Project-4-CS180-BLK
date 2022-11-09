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

    public Product(String name, String storeFrontName, String description, int availableQuantity, double price, int numAddedToCarts) {
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
    public static Comparator<Product> priceCompare = new Comparator<Product>() {

        public int compare(Product p1, Product p2) {

            double price1 = p1.getPrice();
            double price2 = p2.getPrice();

            return Double.compare(price1, price2); //I found this workaround at https://stackoverflow.com/questions/4242023/comparator-with-double-type
        }
    };

    public static Comparator<Product> availabilityCompare = new Comparator<Product>() {

        public int compare(Product p1, Product p2) {

            int num1 = p1.getAvailableQuantity();
            int num2 = p2.getAvailableQuantity();

            return num1 - num2;
        }
    };

    @Override
    public String toString() {
        return name + "::" +
                storeFrontName + "::" +
                description + "::" +
                availableQuantity + "::" +
                price + "::" +
                numAddedToCarts;
    }
}
