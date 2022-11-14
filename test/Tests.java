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
    public void userSetup() {
        String username1 = "User1";
        String pass1 = "Pass1";
        String username2 = "User2";
        String pass2 = "Pass2";
        User user1 = new User(username1, pass1, "buyer");
        User user2 = new User(username2, pass2, "seller");

        assertTrue(user1.signUp());
        assertTrue(user2.signUp());

        String name = "John Doe";

        Buyer buyer = new Buyer(user1, name);

        Seller seller = new Seller(user2);

        buyer.logOut();
        seller.logOut();

        user1 = new User(username1, "this is the wrong pasword", "buyer");
        assertFalse((new Buyer(user1, name)).login());

        user1 = new User(username1, pass1, "buyer");
        buyer = new Buyer(user1, name);

        assertTrue(buyer.login());

        assertTrue(seller.login());

        // cleanup files
        File file1 = new File("User1.txt");
        file1.delete();
        File file2 = new File("User2.txt");
        file2.delete();
        File file3 = new File("sellers.txt");
        file3.delete();
    }

    @Test(timeout = 1000)
    public void userPersistence() {
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

        buyer.logOut();
        seller.logOut();

        // make copies of files for later comparison
        try {
            Files.copy(Paths.get("TheBuyer.txt"), Paths.get("TheBuyerOriginal.txt"), new CopyOption[0]);
            Files.copy(Paths.get("TheSeller.txt"), Paths.get("TheSellerOriginal.txt"), new CopyOption[0]);
        } catch (IOException e) {
            // cleanup files
            (new File("TheBuyer.txt")).delete();
            (new File("TheBuyerOriginal.txt")).delete();
            (new File("TheSeller.txt")).delete();
            (new File("TheSellerOriginal.txt")).delete();
            (new File("sellers.txt")).delete();
            e.printStackTrace();
        }

        Buyer buyerNewLogin = new Buyer(user1, name);
        Seller sellerNewLogin = new Seller(user2);

        assertTrue(buyerNewLogin.login());
        assertTrue(sellerNewLogin.login());

        buyerNewLogin.logOut();
        sellerNewLogin.logOut();


        try { 
            // Files.mismatch returns -1L when the contents of the two files are identical
            assertEquals(-1L, Files.mismatch(Paths.get("TheBuyer.txt"), Paths.get("TheBuyerOriginal.txt")));
            assertEquals(-1L, Files.mismatch(Paths.get("TheSeller.txt"), Paths.get("TheSellerOriginal.txt")));
        } catch (IOException e) {
            // cleanup files
            (new File("TheBuyer.txt")).delete();
            (new File("TheBuyerOriginal.txt")).delete();
            (new File("TheSeller.txt")).delete();
            (new File("TheSellerOriginal.txt")).delete();
            (new File("sellers.txt")).delete();
            e.printStackTrace();
        }

        // cleanup files
        (new File("TheBuyer.txt")).delete();
        (new File("TheBuyerOriginal.txt")).delete();
        (new File("TheSeller.txt")).delete();
        (new File("TheSellerOriginal.txt")).delete();
        (new File("sellers.txt")).delete();
    }
}
