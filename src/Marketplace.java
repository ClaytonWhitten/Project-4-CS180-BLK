import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Marketplace {
    private ArrayList<Seller> allSellers;
    private ArrayList<StoreFront> allStores;
    private ArrayList<Product> allProducts;

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
                if (allSellers.get(i).getStoreFronts().get(i).getStoreFrontName().equalsIgnoreCase(storeFrontName)) {
                    sale = allSellers.get(i).getStoreFronts().get(i).buyItem(buyer, productName, quantity);
                }
            }
        }
        return sale;
    }

    /*
    Close Marketplace:
    Since throughout the process of a buyer interacting with the marketplace, one or more sellers' data has been changed:
    - This method iterates through every seller in the maerketplace and calls "logOut"
    - Logging the sellers out essentially re-write's their data files to hold the updated information
     */
    public void closeMarketplace() {
        for (int i = 0; i < allSellers.size(); i++) {
            allSellers.get(i).logOut();
        }
    }
}
