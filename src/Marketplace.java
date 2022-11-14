import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Marketplace {
    private ArrayList<Seller> allSellers;
    private ArrayList<StoreFront> allStores;
    private ArrayList<Product> allProducts;

    /*
    Initialize Marketplace:
    This function sets up the marketplace for a buyer to interact with doing the following steps:
    - First it reads through the global sellers file sellers.txt to get a list of the usernames for all the sellers
    - with these usernames the marketplace creates an arraylist of seller objects
    - by initializing each seller, the marketplace automatically reads all of their files through the constructor for Seller
    - By reading each seller's files, in the arraylist of seller objects each seller already has all their storefronts loaded
    - With all these storefronts loaded, the Marketplace iterates through each storefront and each product
    - through this process two arraylists, one of every storefront and one of every product, are created
    - these arraylists are what will be used to create the menu for the user to interact with
     */
    public Marketplace() {
        allSellers = new ArrayList<>();
        BufferedReader bfr = null;
        ArrayList<String> lines = new ArrayList<>();
        try {
            File f = new File("sellers.txt");
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
        for (int i = 0; i < lines.size(); i++) {
            allSellers.add(new Seller(lines.get(i), "seller"));
        }
        for (int i = 0; i < allSellers.size(); i++) {
            Seller s = allSellers.get(i);
            for (int j = 0; j < s.getStoreFronts().size(); j++) {
                StoreFront sf = s.getStoreFronts().get(j);
                allStores.add(sf);
                for (int k = 0; k < sf.getProducts().size(); k++) {
                    allProducts.add(sf.getProducts().get(k));
                }
            }
        }
    }

    /*
    Buy Item:
    This version of buy item takes an additional parameter, the storefront name under which the item is being purchased.
    - First it uses the storefront name to locate which seller's storefront is being used
    - It then calls that storefront's buy item method
    - it also returns the sale object that is produced from the storefront method
     */
    public Sale buyItem(Buyer buyer, String storeFrontName, String productName, int quantity) {
        Sale sale = null;
        for (int i = 0; i < allSellers.size(); i++) {
            Seller s = allSellers.get(i);
            for (int j = 0; j < allSellers.get(i).getStoreFronts().size(); j++) {
                StoreFront sf = s.getStoreFronts().get(j);
                if (sf.getStoreFrontName().equalsIgnoreCase(storeFrontName)) {
                    sale = sf.buyItem(buyer, productName, quantity);
                }
            }
        }
        return sale;
    }

    public boolean addToCart(Buyer buyer, Product product, int quantity) {
        for (int i = 0; i < allSellers.size(); i++) {
            Seller s = allSellers.get(i);
            for (int j = 0; j < s.getStoreFronts().size(); j++) {
                StoreFront sf = s.getStoreFronts().get(j);
                if (sf.getStoreFrontName().equalsIgnoreCase(product.getStoreFrontName())) {
                    for (int k = 0; k < sf.getProducts().size(); k++) {
                        Product p = sf.getProducts().get(k);
                        if (p.getName().equalsIgnoreCase(product.getName())) {
                            if (p.addedToCart(quantity)) {
                                buyer.addToCart(product, quantity);
                                return true;
                            } else {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /*
    Close Marketplace:
    Since throughout the process of a buyer interacting with the marketplace, one or more sellers' data has been changed:
    - This method iterates through every seller in the marketplace and calls "logOut"
    - Logging the sellers out essentially re-write's their data files to hold the updated information
     */
    public void closeMarketplace() {
        for (int i = 0; i < allSellers.size(); i++) {
            allSellers.get(i).logOut();
        }
    }

    public ArrayList<Seller> getAllSellers() {
        return allSellers;
    }

    public void setAllSellers(ArrayList<Seller> allSellers) {
        this.allSellers = allSellers;
    }

    public ArrayList<StoreFront> getAllStores() {
        return allStores;
    }

    public void setAllStores(ArrayList<StoreFront> allStores) {
        this.allStores = allStores;
    }

    public ArrayList<Product> getAllProducts() {
        return allProducts;
    }

    public void setAllProducts(ArrayList<Product> allProducts) {
        this.allProducts = allProducts;
    }

    public ArrayList<Product> sort(String sortValue, boolean desc, ArrayList<Product> list) {
        ArrayList<Product> sortedList = (ArrayList<Product>) list.clone();

        if (sortValue.equalsIgnoreCase("price")) {
            Collections.sort(sortedList, Product.priceCompare(desc));
        }
        if (sortValue.equalsIgnoreCase("quantity")) {
            Collections.sort(sortedList, Product.availabilityCompare(desc));
        }

        return sortedList;
    }

    public ArrayList<Product> search(String search) {
        search = search.toLowerCase();
        ArrayList<Product> searchResults = new ArrayList<>();
        for (int i = 0; i < allProducts.size(); i++) {
            Product p = allProducts.get(i);
            if (p.getName().toLowerCase().contains(search) ||
                    p.getStoreFrontName().toLowerCase().contains(search) ||
                    p.getDescription().toLowerCase().contains(search)) {
                searchResults.add(p);
            }
        }
        return searchResults;
    }

    public void printProductList(ArrayList<Product> list) {
        for (int i = 0; i < list.size(); i++) {
            Product p = list.get(i);
            System.out.println("".format("%d. Store: %s, Name: %s, Price: %.2f",
                i + 1, p.getStoreFrontName(), p.getName(), p.getPrice()));
        }
    }

    /*
    Print Marketplace:
    The handout says the following - "The marketplace listing page will show the store,
    product name, and price of the available goods."
    - This method calls printStoreFront for each storefront in allStores
    - !IMPORTANT NOTE ABOUT IMPLEMENTATION!
        - This method will number each StoreFront and number each product in the StoreFront
        - This means that the user will have to give 2 input values instead of one to reach the desired product
     */
    public void printMarketplace() {

        for (int i = 0; i < allStores.size(); i++) {
            System.out.print((i + 1) + ". ");
            allStores.get(i).printStoreFront();
            if (i < allStores.size() - 1)
                System.out.println("");
        }
    }

    public void printListStorefronts () { //returns a numbered list of storefronts
        for (int i = 0; i < allStores.size(); i++) {
            System.out.print((i + 2) + ". ");
            System.out.println(allStores.get(i).getStoreFrontName());
        }
    }
}
