import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

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
            for (int j = 0; j < allSellers.get(i).getStoreFronts().size(); j++) {
                allStores.add(allSellers.get(i).getStoreFronts().get(j));
                for (int k = 0; k < allSellers.get(i).getStoreFronts().get(j).getProducts().size(); k++) {
                    allProducts.add(allSellers.get(i).getStoreFronts().get(j).getProducts().get(k));
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
            for (int j = 0; j < allSellers.get(i).getStoreFronts().size(); j++) {
                if (allSellers.get(i).getStoreFronts().get(j).getStoreFrontName().equalsIgnoreCase(storeFrontName)) {
                    sale = allSellers.get(i).getStoreFronts().get(j).buyItem(buyer, productName, quantity);
                }
            }
        }
        return sale;
    }

    public boolean addToCart(Buyer buyer, Product product, int quantity) {
        for (int i = 0; i < allSellers.size(); i++) {
            for (int j = 0; j < allSellers.get(i).getStoreFronts().size(); j++) {
                if (allSellers.get(i).getStoreFronts().get(j).getStoreFrontName().equalsIgnoreCase(product.getStoreFrontName())) {
                    for (int k = 0; k < allSellers.get(i).getStoreFronts().get(j).getProducts().size(); k++) {
                        if (allSellers.get(i).getStoreFronts().get(j).getProducts().get(k).getName().equalsIgnoreCase(product.getName())) {
                            if (allSellers.get(i).getStoreFronts().get(j).getProducts().get(k).addedToCart(quantity)) {
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

    /*
    Print Store Front:
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
}
