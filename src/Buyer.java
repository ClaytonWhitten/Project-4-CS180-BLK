import java.util.*;

public class Buyer extends User {

    private String name;
    private ArrayList<Product> shoppingCart;

    private ArrayList<Sale> purchases;

    public Buyer(String username, String password, String name) {
        super(username, password);
        this.name = name;
    }

}
