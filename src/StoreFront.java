import java.io.*;
import java.util.*;

public class StoreFront {

    private String storeFrontName;
    private String sellerUsername;
    private ArrayList<Product> products;
    private ArrayList<Sale> sales;

    public StoreFront(String storeFrontName, String sellerUsername) {
        this.storeFrontName = storeFrontName;
        this.sellerUsername = sellerUsername;
        this.products = new ArrayList<>();
        this.sales = new ArrayList<>();
    }

    public StoreFront(String storeFrontName, String sellerUsername, ArrayList<Product> products) {
      this.storeFrontName = storeFrontName;
      this.sellerUsername = sellerUsername;
      this.products = products;
    }

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
            Product p = products.get(i);
            if (p.getName().equalsIgnoreCase(productName)) {
                if (p.getAvailableQuantity() - quantity <= 0) {
                    return null;
                } else {
                    p.setAvailableQuantity(p.getAvailableQuantity() - quantity);
                    sale = new Sale(buyer.getUsername(), productName, quantity, p.getPrice()*quantity);
                }

            }
        }
        if (sale != null)
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
            Sale s = sales.get(i);
            for (int j = 0; j < list.size(); j++) {
                Map<String, Integer> m = list.get(j);
                if (m.containsKey(s.getCustomerInfo())) {
                    m.put(s.getCustomerInfo(), m.get(s.getCustomerInfo()) + s.getQuantity());
                    newCustomer = false;
                }
            }
            if (newCustomer) {
                list.add(Map.of(s.getCustomerInfo(), s.getQuantity()));
            }
        }
        return list;
    }

    public void printDataByCustomer() {
        ArrayList<Map<String, Integer>> list = getDataByCustomer();
        for (int i = 0; i < list.size(); i++) {
            System.out.println("Customer: " + list.get(i).keySet());
            System.out.println("Number of Items Purchased: " + list.get(i).get(list.get(i).keySet()));
            System.out.println();
        }
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
            Sale s = sales.get(i);
            for (int j = 0; j < list.size(); j++) {
                Map<String, Integer> m = list.get(j);
                if (m.containsKey(s.getProductName())) {
                    m.put(s.getProductName(), m.get(s.getProductName()) + s.getQuantity());
                    newProduct = false;
                }
            }
            if (newProduct) {
                list.add(Map.of(sales.get(i).getProductName(), sales.get(i).getQuantity()));
            }
        }
        return list;
    }

    public void printDataByProduct() {
        ArrayList<Map<String, Integer>> list = getDataByProduct();
        for (int i = 0; i < list.size(); i++) {
            System.out.println("Product: " + list.get(i).keySet());
            System.out.println("Quantity Sold: " + list.get(i).get(list.get(i).keySet()));
            System.out.println();
        }
    }

    @Override
    public String toString() {
        String storefront = "";
        storefront += storeFrontName + "\n";
        for (int i = 0; i < products.size(); i++) {
            storefront += "-" + products.get(i) + "\n";
        }
        if (sales.size() > 0) {
            storefront += ">" + sales.get(0);
            for (int i = 1; i < sales.size(); i++) {
                storefront += ";" + sales.get(i);
            }
        }
        return storefront;
    }

    public void importProducts(String fileName) {
        BufferedReader bfr = null;
        ArrayList<String> lines = new ArrayList<>();
        try {
            File f = new File(fileName);
            FileReader fr = new FileReader(f);
            bfr = new BufferedReader(fr);
            String currentLine = bfr.readLine();
            while (currentLine != null) {
                lines.add(currentLine);
                currentLine = bfr.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bfr != null) {
                try {
                    bfr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        String[] tempProductFields = null;
        for (int i = 0; i < lines.size(); i++) {
            try {
                tempProductFields = lines.get(i).split("::");
            } catch (Exception e) {
                System.out.println("Error Adding Product");
            }
            if (tempProductFields[0].equalsIgnoreCase("") ||
                    tempProductFields[1].equalsIgnoreCase("") ||
                    !tempProductFields[1].equalsIgnoreCase(storeFrontName) ||
                    tempProductFields[2].equalsIgnoreCase("")) {
                System.out.println("Error Adding Product");
            }
            try {
                products.add(new Product(tempProductFields[0],
                        tempProductFields[1],
                        tempProductFields[2],
                        Integer.parseInt(tempProductFields[3]),
                        Double.parseDouble(tempProductFields[4])));
            } catch (Exception e) {
                System.out.println("Error Adding Product");
            }
        }
    }

    public void exportProductsList(String fileName) {
        PrintWriter pw = null;;
        try {
            File f = new File(fileName);
            FileOutputStream fos = new FileOutputStream(f, false);
            pw = new PrintWriter(fos);
            for (int i = 0; i < products.size(); i++) {
                pw.println(products.get(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pw != null) {
                try {
                    pw.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /*
    Print Store Front:
    The handout says the following - "The marketplace listing page will show the store,
    product name, and price of the available goods."
    - This method will print the name of the storefront, and then a numbered list of the products and their prices
    - This method is intended to be called for each StoreFront in the MarketPlace class
     */
    public void printStoreFront() {
        System.out.println(storeFrontName);
        System.out.println("-----------------------");

        for (int i = 0; i < products.size(); i++) {
            Product currentProduct = (products.get(i));

            System.out.print((i + 1) + ". " + currentProduct.getName());
            System.out.println(", Price: " + currentProduct.getPrice());
        }
    }

    public void printStoreFrontByList(ArrayList<Product> list) {
        System.out.println(storeFrontName);
        System.out.println("-----------------------");

        for (int i = 0; i < list.size(); i++) {
            Product currentProduct = (list.get(i));

            System.out.print((i + 1) + ". " + currentProduct.getName());
            System.out.println(", Price: " + currentProduct.getPrice());
        }
    }

    public void updateProduct(Product oldP, Product newP) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getName().equalsIgnoreCase(oldP.getName())) {
                products.set(i, newP);
            }
        }
    }

    public void addProduct(Product p) {
        products.add(p);
    }

    public void printProducts() {
        for (int i = 0; i < products.size(); i++) {
            System.out.println((i+1) + ". " + products.get(i));
        }
    }

    public void printSales() {
        for (int i = 0; i < sales.size(); i++) {
            if (sales.get(i).getQuantity() != 0) {
                System.out.println((i + 1) + ") " + sales.get(i));
            }
        }
    }

    public int totalSales() {
        int sum = 0;
        for (int i = 0; i < sales.size(); i++) {
            sum += sales.get(i).getQuantity();
        }
        return sum;
    }
}