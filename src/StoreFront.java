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

    /*
    Buy Item:
    Takes a buyer's info, the name of a product their buying, and the quantity being bought and does the following:
    - returns null if there is more being bought than available
    - if not, then the available amount is decremented by the quantity bought
    - a new sale object is created and added to the storefront's sales array
    - the sale item is also returned to be added to the Buyer's array
     */
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

    /*
    Get Data By Customer:
    The handout says the following - "Sellers can view a dashboard that lists statistics for each of their stores.
    Data will include a list of customers with the number of items that they have purchased and a list of products
    with the number of sales."
    - In response to the first part, this method will return a list of customers and the number of products they have bought.
    - It will do this by returning an arraylist of HashMaps, where the key is the customer info and the value mapped to each
    key is the total number of products they have bought.
    - If the customer has multiple sales to their name, it will simply add the quantity of the new sale to the total quantity
    in the arraylist
     */
    public ArrayList<Map<String, Integer>> getDataByCustomer() {
        ArrayList<Map<String, Integer>> list = new ArrayList<>();
        boolean newCustomer = true;
        for (int i = 0; i < sales.size(); i++) {
            for (int j = 0; j < list.size(); j++) {
                if (list.get(j).containsKey(sales.get(i).getCustomerInfo())) {
                    list.get(j).put(sales.get(i).getCustomerInfo(), list.get(j).get(sales.get(i).getCustomerInfo()) + sales.get(i).getQuantity());
                    newCustomer = false;
                }
            }
            if (newCustomer) {
                list.add(Map.of(sales.get(i).getCustomerInfo(), sales.get(i).getQuantity()));
            }
        }
        return list;
    }

    /*
    Get Data By Product:
    The handout says the following - "Sellers can view a dashboard that lists statistics for each of their stores.
    Data will include a list of customers with the number of items that they have purchased and a list of products
    with the number of sales."
    - In response to the second part, this method will return a list of products and the quantity sold of each.
    - It will do this by returning an arraylist of HashMaps, where the key is the product name and the value mapped to each
    key is the number of that product that has been purchased.
    - If the product has multiple sales to their name, it will simply add the quantity of the new sale to the total quantity
    in the arraylist
     */
    public ArrayList<Map<String, Integer>> getDataByProduct() {
        ArrayList<Map<String, Integer>> list = new ArrayList<>();
        boolean newProduct = true;
        for (int i = 0; i < sales.size(); i++) {
            for (int j = 0; j < list.size(); j++) {
                if (list.get(j).containsKey(sales.get(i).getProductName())) {
                    list.get(j).put(sales.get(i).getProductName(), list.get(j).get(sales.get(i).getProductName()) + sales.get(i).getQuantity());
                    newProduct = false;
                }
            }
            if (newProduct) {
                list.add(Map.of(sales.get(i).getProductName(), sales.get(i).getQuantity()));
            }
        }
        return list;
    }

    @Override
    public String toString() {
        String storefront = "";
        storefront += storeFrontName + "\n";
        for (int i = 0; i < products.size(); i++) {
            storefront += "-" + products.get(i) + "\n";
        }
        storefront += ">" + sales.get(0);
        for (int i = 1; i < sales.size(); i++) {
            storefront += ";" + sales.get(i);
        }
        return storefront;
    }
}
