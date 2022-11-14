import org.junit.Test;
import org.junit.After;
import java.lang.reflect.Field;
import org.junit.Assert;
import org.junit.Before;
import org.junit.rules.Timeout;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import javax.swing.*;
import java.io.*;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import static org.junit.Assert.*;

public class Tests {
    private final PrintStream originalOutput = System.out;
    private final InputStream originalSysin = System.in;

    @SuppressWarnings("FieldCanBeLocal")
    private ByteArrayInputStream testIn;

    @SuppressWarnings("FieldCanBeLocal")
    private ByteArrayOutputStream testOut;

    @Before
    public void outputStart() {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
    }

    @After
    public void restoreInputAndOutput() {
        System.setIn(originalSysin);
        System.setOut(originalOutput);
    }

    private String getOutput() {
        return testOut.toString();
    }

    @SuppressWarnings("SameParameterValue")
    private void receiveInput(String str) {
        testIn = new ByteArrayInputStream(str.getBytes());
        System.setIn(testIn);
    }

    @Test(timeout = 1000)
    public void buyerSetup() {
        // cleanup files
        (new File("User1.txt")).delete();

        String username1 = "User1";
        String pass1 = "Pass1";
        User user1 = new User(username1, pass1, "buyer");

        assertTrue(user1.signUp());

        String name = "John Doe";

        Buyer buyer = new Buyer(user1, name);

        buyer.logOut();

        user1 = new User(username1, "this is the wrong pasword", "buyer");
        assertFalse((new Buyer(user1, name)).login());

        assertTrue(buyer.login());
    }

    @Test(timeout = 1000)
    public void sellerSetup() {
        // cleanup files
        (new File("User2.txt")).delete();
        (new File("sellers.txt")).delete();

        String username2 = "User2";
        String pass2 = "Pass2";
        User user2 = new User(username2, pass2, "seller");

        assertTrue(user2.signUp());

        Seller seller = new Seller(user2);

        seller.logOut();

        user2 = new User(username2, "this is the wrong pasword", "seller");

        user2 = new User(username2, pass2, "buyer");

        assertTrue(seller.login());
    }

    @Test(timeout = 1000)
    public void buyerPersistence() throws IOException {
        (new File("TheBuyer.txt")).delete();
        (new File("TheBuyerOriginal.txt")).delete();
        (new File("Tllers.txt")).delete();
        String username1 = "TheBuyer";
        String pass1 = "Pass1";
        User user1 = new User(username1, pass1, "buyer");

        String name = "John Doe";

        Buyer buyer = new Buyer(user1, name);
        buyer.login();

        Product a = new Product("PRODUCTA", "STOREA", "DESCA", 100, 2.49);
        Product a2 = new Product("PRODUCTA2", "STOREA", "DESCA2", 100, 3.49);
        Product b = new Product("PRODUCTB", "STOREB", "DESCB", 100, 4.99);
        Product b2 = new Product("PRODUCTB2", "STOREB", "DESCB2", 100, 5.99);

        // do some stuff that should persist
        buyer.addToCart(a, 1);
        buyer.addToCart(b, 2);

        Product c = new Product("PRODUCTC", "STOREC", "DESCC", 100, 6.89);
        Product c2 = new Product("PRODUCTC2", "STOREC", "DESCC2", 100, 7.89);

        String username2 = "hypothetical seller";
        StoreFront storeA = new StoreFront("STOREA", username2, new ArrayList<Product>(Arrays.asList(a, a2)),
            new ArrayList<Sale>());
        StoreFront storeB = new StoreFront("STOREB", username2, new ArrayList<Product>(Arrays.asList(b, b2)),
            new ArrayList<Sale>());
        StoreFront storeC = new StoreFront("STOREC", username2, new ArrayList<Product>(Arrays.asList(c, c2)),
            new ArrayList<Sale>());

        ArrayList<Sale> purchases = new ArrayList<>();

        Sale s = storeC.buyItem(buyer, "PRODUCTC", 1);
        Sale s2 = storeC.buyItem(buyer, "PRODUCTC2", 1);
        if (s != null && s2 != null) {
            purchases.add(s);
            purchases.add(s2);
        }

        buyer.setPurchases(purchases);

        buyer.logOut();

        // make copies of original files for later comparison
        Files.copy(Paths.get("TheBuyer.txt"), Paths.get("TheBuyerOriginal.txt"), new CopyOption[0]);

        // contents of original file are loaded again
        Buyer buyerNewLogin = new Buyer(user1, name);

        // login successful
        assertTrue(buyerNewLogin.login());

        // produce file once again
        buyerNewLogin.logOut();

        // Files.mismatch returns -1L when the contents of the two files are identical
        assertEquals(-1L, Files.mismatch(Paths.get("TheBuyer.txt"), Paths.get("TheBuyerOriginal.txt")));
    }

    @Test(timeout = 1000)
    public void sellerPersistence() throws IOException {
        (new File("TheSeller.txt")).delete();
        (new File("TheSellerOriginal.txt")).delete();
        (new File("sellers.txt")).delete();
        String username1 = "TheBuyer";
        String pass1 = "Pass1";
        String username2 = "TheSeller";
        String pass2 = "Pass2";
        User user1 = new User(username1, pass1, "buyer");
        User user2 = new User(username2, pass2, "seller");

        String name = "John Doe";

        Buyer buyer = new Buyer(user1, name);
        Seller seller = new Seller(user2);
        buyer.login();
        seller.login();

        Product a = new Product("PRODUCTA", "STOREA", "DESCA", 100, 2.49);
        Product a2 = new Product("PRODUCTA2", "STOREA", "DESCA2", 100, 3.49);
        Product b = new Product("PRODUCTB", "STOREB", "DESCB", 100, 4.99);
        Product b2 = new Product("PRODUCTB2", "STOREB", "DESCB2", 100, 5.99);

        // do some stuff that should persist
        buyer.addToCart(a, 1);
        buyer.addToCart(b, 2);

        Product c = new Product("PRODUCTC", "STOREC", "DESCC", 100, 6.89);
        Product c2 = new Product("PRODUCTC2", "STOREC", "DESCC2", 100, 7.89);

        seller.addStore("STOREA", username2,
                new ArrayList<Product>(Arrays.asList(a, a2)));
        seller.addStore("STOREB", username2,
                new ArrayList<Product>(Arrays.asList(b, b2)));
        seller.addStore("STOREC", username2,
                new ArrayList<Product>(Arrays.asList(c, c2)));

        ArrayList<Sale> purchases = new ArrayList<>();

        for (StoreFront store : seller.getStoreFronts()) {
            Sale s = store.buyItem(buyer, "PRODUCTC", 1);
            Sale s2 = store.buyItem(buyer, "PRODUCTC2", 1);
            if (s != null && s2 != null) {
                purchases.add(s);
                purchases.add(s2);
            }
        }

        buyer.setPurchases(purchases);

        seller.logOut();

        // make copies of original files for later comparison
        Files.copy(Paths.get("TheSeller.txt"), Paths.get("TheSellerOriginal.txt"), new CopyOption[0]);

        // contents of original file are loaded again
        Seller sellerNewLogin = new Seller(user2);

        // login successful
        assertTrue(sellerNewLogin.login());

        // produce file once again
        sellerNewLogin.logOut();

        // Files.mismatch returns -1L when the contents of the two files are identical
        assertEquals(-1L, Files.mismatch(Paths.get("TheSeller.txt"), Paths.get("TheSellerOriginal.txt")));
    }

    @Test(timeout = 1000)
    public void marketplaceFunctions() throws IOException {
        (new File("User.txt")).delete();
        (new File("SellerA.txt")).delete();
        (new File("SellerB.txt")).delete();
        (new File("SellerC.txt")).delete();
        (new File("sellers.txt")).delete();
        (new File("sellersOriginal.txt")).delete();
        Product a = new Product("PRODUCTA", "STOREA", "DESCA", 10, 2.49);
        Product a2 = new Product("PRODUCTA2", "STOREA", "DESCA2", 20, 3.49);
        Product b = new Product("PRODUCTB", "STOREB", "DESCB", 30, 4.99);
        Product b2 = new Product("PRODUCTB2", "STOREB", "DESCB2", 40, 5.99);
        Product c = new Product("PRODUCTC", "STOREC", "DESCC", 50, 6.89);
        Product c2 = new Product("PRODUCTC2", "STOREC", "DESCC2", 60, 7.89);

        Seller sellerA = new Seller("SellerA", "seller");
        Seller sellerB = new Seller("SellerB", "seller");
        Seller sellerC = new Seller("SellerC", "seller");

        sellerA.signUp();
        sellerB.signUp();
        sellerC.signUp();

        sellerA.addStore("STOREA", "SellerA",
            new ArrayList<Product>(Arrays.asList(a, a2)));
        sellerB.addStore("STOREB", "SellerB",
            new ArrayList<Product>(Arrays.asList(b, b2)));
        sellerC.addStore("STOREC", "SellerC",
            new ArrayList<Product>(Arrays.asList(c, c2)));
        
        sellerA.logOut();
        sellerB.logOut();
        sellerC.logOut();

        Files.copy(Paths.get("sellers.txt"), Paths.get("sellersOriginal.txt"), new CopyOption[0]);

        Marketplace marketplace = new Marketplace();

        User user = new User("User", "Pass", "buyer");
        user.signUp();

        Buyer buyer = new Buyer(user, "John Doe");

        // add to cart succeeds
        assertEquals(true, marketplace.addToCart(buyer, a2, 2));

        Product product = buyer.getShoppingCart().get(0);
        // properties of product added match
        assertEquals(a2.getDescription(), product.getDescription());
        assertEquals(a2.getName(), product.getName());
        assertEquals(a2.getPrice(), product.getPrice(), 0.001);

        Sale sale = marketplace.buyItem(buyer, "STOREA", "PRODUCTA2", 2);

        // sale's info is correct
        assertEquals("User", sale.getCustomerInfo());
        assertEquals("PRODUCTA2", sale.getProductName());
        // revenue equal with 1/10th cent epsilon
        assertEquals(3.49 * 2, sale.getRevenue(), 0.001);

        ArrayList<Product> priceAsc = marketplace.sort("price", false, marketplace.getAllProducts());
        ArrayList<Product> priceDesc = marketplace.sort("price", true, marketplace.getAllProducts());
        ArrayList<Product> quantAsc = marketplace.sort("quantity", false, marketplace.getAllProducts());
        ArrayList<Product> quantDesc = marketplace.sort("quantity", true, marketplace.getAllProducts());
        Collections.reverse(priceDesc);
        Collections.reverse(quantDesc);

        // product list ordering works properly
        for (int i = 0; i < priceAsc.size(); i++) {
            assertEquals(priceAsc.get(i).toString(), priceDesc.get(i).toString());
            assertEquals(quantAsc.get(i).toString(), quantDesc.get(i).toString());
        }

        // search method works
        ArrayList<Product> someResults = marketplace.search("DESC");
        assertEquals(6, someResults.size());

        ArrayList<Product> noResults = marketplace.search("PRODUCTD");
        assertEquals(0, noResults.size());

        marketplace.closeMarketplace();
        // sellers file persistence works
        assertEquals(-1L, Files.mismatch(Paths.get("sellers.txt"), Paths.get("sellersOriginal.txt")));
    }
}
