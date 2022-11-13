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
import java.util.concurrent.ThreadLocalRandom;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

import static org.junit.Assert.*;

public class TestUserBuyerSeller {
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

        File file1 = new File("User1.txt");
        file1.delete();
        File file2 = new File("User2.txt");
        file2.delete();
        File file3 = new File("sellers.txt");
        file3.delete();
    }
}
