import java.io.*;
import java.util.*;

public class Seller extends User {

    private ArrayList<StoreFront> storeFronts;

    public Seller(User user) {
        super(user.getUsername(), user.getPassword(), user.getType());
        storeFronts = new ArrayList<>();
        readSellerFile(user.getUsername() + ".txt");
    }

    public Seller(String username, String type) {
        super(username, null, type);
        storeFronts = new ArrayList<>();
        readSellerFile(username + ".txt");
    }


    /*
    Add Store:
    This function allows a seller to create a new storeFront
     */
    public void addStore(String storeFrontName, String sellerUserName) {
        StoreFront storeFront = new StoreFront(storeFrontName, sellerUserName);
        storeFronts.add(storeFront);
    }

    public void printStoreFronts() {

    }

    /*
    Read Seller File:
    This function just sets up the seller object by updating its arraylist of store (StoreFront)
    - The function reads through the seller's file and in accordance to the format I determined, sorts the data and adds
    it to the arraylists.
     */
    private void readSellerFile(String fileName) {
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
        ArrayList<Product> tempProductsList;
        String tempStorefrontName = null;
        String[] tempSalesArray;
        String[] tempSalesFields;
        ArrayList<Sale> tempSalesList;
        for (int i = 0; i < lines.size(); i++) {
            if (i == 0 && super.getPassword() == null) {
                super.setPassword(lines.get(i).substring(0, lines.get(i).indexOf(";")));
            }
            if (lines.get(i).equalsIgnoreCase("*****")) {
                tempProductsList = new ArrayList<>();
                tempSalesList = new ArrayList<>();
                for (int j = i+1; j < lines.size(); j++) {
                    if (j == i+1) {
                        tempStorefrontName = lines.get(j);
                    }
                    if (lines.get(j).charAt(0) == '-') {
                        tempProductFields = lines.get(j).split("::");
                        tempProductsList.add(new Product(tempProductFields[0], tempProductFields[1], tempProductFields[2], Integer.parseInt(tempProductFields[3]), Double.parseDouble(tempProductFields[4]), Integer.parseInt(tempProductFields[5])));
                    }
                    if (lines.get(j).charAt(0) == '>') {
                        tempSalesArray = lines.get(j).substring(1).split(";");
                        for (int k = 0; k < tempSalesArray.length; k++) {
                            tempSalesFields = tempSalesArray[k].split(",");
                            tempSalesList.add(new Sale(tempSalesFields[0], tempSalesFields[1], Integer.parseInt(tempSalesFields[2]), Double.parseDouble(tempSalesFields[3])));
                        }
                    }
                    if (lines.get(j).equalsIgnoreCase("*****")) {
                        storeFronts.add(new StoreFront(tempStorefrontName, super.getUsername(), (ArrayList<Product>) tempProductsList.clone(), (ArrayList<Sale>) tempSalesList.clone()));
                        tempProductsList.clear();
                        tempSalesList.clear();
                        i = j;
                        break;
                    }
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
            pw.println(super.getPassword() + ";" + "seller");
            for (int i = 0; i < storeFronts.size(); i++) {
                pw.println("*****");
                pw.println(storeFronts.get(i));
                pw.println("*****");
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

    public ArrayList<StoreFront> getStoreFronts() {
        return storeFronts;
    }
}
