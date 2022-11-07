import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Buyer extends User {

    private String name;
    private ArrayList<Product> shoppingCart;

    private ArrayList<Sale> purchases;

    public Buyer(User user, String name) {
        super(user.getUsername(), user.getPassword(), user.getType());
        this.name = name;
        shoppingCart = new ArrayList<>();
        purchases = new ArrayList<>();
        readBuyerFile(user.getUsername() + ".txt");
    }

    private void readBuyerFile(String fileName) {
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

        String[] tempProductFields;
        String[] tempSalesArray;
        String[] tempSalesFields;

        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).charAt(0) == '-') {
                tempProductFields = lines.get(i).split("::");
                shoppingCart.add(new Product(tempProductFields[0], tempProductFields[1], tempProductFields[2], Integer.parseInt(tempProductFields[3]), Double.parseDouble(tempProductFields[4])));
            }
            if (lines.get(i).charAt(0) == '>') {
                tempSalesArray = lines.get(i).substring(1).split(";");
                for (int k = 0; k < tempSalesArray.length; k++) {
                    tempSalesFields = tempSalesArray[k].split(",");
                    purchases.add(new Sale(tempSalesFields[0], tempSalesFields[1], Double.parseDouble(tempSalesFields[2])));
                }
            }
        }
    }
}