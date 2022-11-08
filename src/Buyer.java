import java.io.*;
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

    /*
    Read Buyer File:
    This function just sets up the buyer object by updating its arraylist of purchases (Sale) and shopping cart (Product)
    - The function reads through the buyer's file and in accordance to the format I determined, sorts the data and adds
    it to the arraylists.
     */
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
                    purchases.add(new Sale(tempSalesFields[0], tempSalesFields[1], Integer.parseInt(tempSalesFields[2]), Double.parseDouble(tempSalesFields[3])));
                }
            }
        }
    }

    /*
    Log Out:
    This function is called at the end of a user's session and re-writes any updated data back into their data file
     */
    public void logOut() {
        PrintWriter pw = null;;
        try {
            File f = new File(super.getUsername() + ".txt");
            FileOutputStream fos = new FileOutputStream(f, false);
            pw = new PrintWriter(fos);
            pw.println(super.getPassword() + ";" + "buyer");
            pw.println("*****");
            for (int i = 0; i < shoppingCart.size(); i++) {
                pw.println("-" + shoppingCart.get(i));
            }
            pw.println("*****");
            pw.print(">");
            pw.print(purchases.get(0));
            for (int i = 1; i < purchases.size(); i++) {
                pw.println(";" + purchases.get(i));
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
}