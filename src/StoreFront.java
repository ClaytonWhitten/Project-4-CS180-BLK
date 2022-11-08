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

    public String getStoreFrontName() {
        return storeFrontName;
    }

    public void setStoreFrontName(String storeFrontName) {
        this.storeFrontName = storeFrontName;
    }

    public String getSellerUsername() {
        return sellerUsername;
    }

    public void setSellerUsername(String sellerUsername) {
        this.sellerUsername = sellerUsername;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public ArrayList<Sale> getSales() {
        return sales;
    }

    public void setSales(ArrayList<Sale> sales) {
        this.sales = sales;
    }

    public Sale buyItem(Buyer buyer, String productName, int quantity) {
        Sale sale = null;
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getName().equalsIgnoreCase(productName)) {
                if (products.get(i).getAvailableQuantity() - quantity <= 0) {
                    return null;
                } else {
                    products.get(i).setAvailableQuantity(products.get(i).getAvailableQuantity() - quantity);
                    sale = new Sale(buyer.getUsername(), productName, quantity, products.get(i).getPrice()*quantity);
                }

            }
        }
        sales.add(sale);
        return sale;
    }
}
