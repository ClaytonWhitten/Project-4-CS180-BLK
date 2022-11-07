import java.util.*;

public class Seller extends User {

    private ArrayList<StoreFront> storeFront;


    public Seller(String username, String password, ArrayList<StoreFront> storeFront) {
        super(username, password);
        this.storeFront = storeFront;
    }

}
