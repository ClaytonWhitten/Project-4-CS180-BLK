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
}
