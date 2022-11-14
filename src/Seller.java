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
    public void addStore(String storeFrontName, String sellerUserName, ArrayList<Product> products) {
        StoreFront storeFront = new StoreFront(storeFrontName, sellerUserName, products);
        storeFronts.add(storeFront);
        ArrayList<Sale> sales = new ArrayList<>();
        Sale sale = new Sale(null, null, 0, 0);
        sales.add(sale);
        storeFront.setSales(sales);
        storeFront.setProducts(products);
    }

    public void printStoreFronts() {
        int count = 1;
        for (int i = 0; i < storeFronts.size(); i++) {
            System.out.println(count + ". " + storeFronts.get(i).getStoreFrontName());
            count++;
        }
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
        //tempproductfields
        String[] tpf;
        ArrayList<Product> tempProductsList;
        String tempStorefrontName = null;
        String[] tempSalesArray;
        //tempsalesfields
        String[] tsf;
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
                        tpf = lines.get(j).split("::");
                        tempProductsList.add(new Product(tpf[0], tpf[1], tpf[2], Integer.parseInt(tpf[3]), Double.parseDouble(tpf[4]), Integer.parseInt(tpf[5])));
                    }
                    if (lines.get(j).charAt(0) == '>') {
                        tempSalesArray = lines.get(j).substring(1).split(";");
                        for (int k = 0; k < tempSalesArray.length; k++) {
                            tsf = tempSalesArray[k].split(",");
                            tempSalesList.add(new Sale(tsf[0], tsf[1], Integer.parseInt(tsf[2]), Double.parseDouble(tsf[3])));
                        }
                    }
                    if (lines.get(j).equalsIgnoreCase("*****")) {
                        storeFronts.add(new StoreFront(tempStorefrontName, super.getUsername(), tempProductsList, tempSalesList));
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
