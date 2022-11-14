import java.io.*;
import java.util.*;

public class Buyer extends User {

    private String name;
    private ArrayList<Product> shoppingCart;
    private ArrayList<Integer> cartQuantities;

    private ArrayList<Sale> purchases;

    public Buyer(User user, String name) {
        super(user.getUsername(), user.getPassword(), user.getType());
        this.name = name;
        shoppingCart = new ArrayList<>();
        cartQuantities = new ArrayList<>();
        purchases = new ArrayList<>();
        readBuyerFile(user.getUsername() + ".txt");
    }

    public Buyer(User user) {
        super(user.getUsername(), user.getPassword(), user.getType());
        shoppingCart = new ArrayList<>();
        cartQuantities = new ArrayList<>();
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

        //tempproductfields
        String[] tpf;
        String[] tempSalesArray;
        //tempsalesfields
        String[] tsf;

        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).charAt(0) == '-') {
                tpf = lines.get(i).substring(1).split("::");
                shoppingCart.add(new Product(tpf[0], tpf[1], tpf[2], Integer.parseInt(tpf[3]), 
                    Double.parseDouble(tpf[4]), Integer.parseInt(tpf[5])));
            }
            if (lines.get(i).charAt(0) == '=') {
                cartQuantities.add(Integer.parseInt(lines.get(i).substring(1)));
            }
            if (lines.get(i).charAt(0) == '>') {
                tempSalesArray = lines.get(i).substring(1).split(";");
                for (int k = 0; k < tempSalesArray.length; k++) {
                    tsf = tempSalesArray[k].split(",");
                    purchases.add(new Sale(tsf[0], tsf[1], Integer.parseInt(tsf[2]), 
                        Double.parseDouble(tsf[3])));
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
                pw.println("=" + cartQuantities.get(i));
            }
            pw.println("*****");
            if (purchases.size() > 0) {
                pw.print(">");
                for (int i = 0; i < purchases.size(); i++) {
                    Sale s = purchases.get(i);
                    pw.print("".format((i != 0 ? ";" : "") + "%s,%s,%d,%.2f", s.getCustomerInfo(),
                        s.getProductName(), s.getQuantity(), s.getRevenue()));
                }
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

    public void exportPurchaseList(String fileName) {
        PrintWriter pw = null;;
        try {
            File f = new File(fileName);
            FileOutputStream fos = new FileOutputStream(f, false);
            pw = new PrintWriter(fos);
            for (int i = 0; i < purchases.size(); i++) {
                pw.println(purchases.get(i));
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

    public void addToCart(Product product, int quantity) {
        shoppingCart.add(product);
        cartQuantities.add(quantity);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Product> getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ArrayList<Product> shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public ArrayList<Integer> getCartQuantities() {
        return cartQuantities;
    }

    public void setCartQuantities(ArrayList<Integer> cartQuantities) {
        this.cartQuantities = cartQuantities;
    }

    public ArrayList<Sale> getPurchases() {
        return purchases;
    }

    public void setPurchases(ArrayList<Sale> purchases) {
        this.purchases = purchases;
    }

    public void addSale(Sale sale) {
        purchases.add(sale);
    }

    public boolean removeFromCart(int i) {
        if (i < shoppingCart.size() && i >= 0) {
            shoppingCart.remove(i);
            return true;
        } else {
            return false;
        }
    }

    public void printCart() {
        for (int i = 0; i < shoppingCart.size(); i++) {
            System.out.println((i + 1) + ". " + shoppingCart.get(i).getName() + ", Amount: " + cartQuantities.get(i));
        }
    }


}