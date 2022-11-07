import java.util.*;

public class StoreFront {

    private String storeFrontName;
    private String sellerUsername;
    private ArrayList<Product> products;
    private ArrayList<Sale> sales;

    public StoreFront(String storeFrontName, String sellerUsername, ArrayList<Product> products, ArrayList<Sale> sales) {
        this.storeFrontName = storeFrontName;
        this.sellerUsername = sellerUsername;
        this.products = products;
        this.sales = sales;
    }
}
