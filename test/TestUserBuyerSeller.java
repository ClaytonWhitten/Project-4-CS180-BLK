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
    }

    // @Test(timeout = 1000)
    // public void testOne() {
    // // Set the input
    // String input = "Tower Bridge" + System.lineSeparator() + "4000" +
    // System.lineSeparator() + "Eiffel Tower" +
    // System.lineSeparator() + "2000" + System.lineSeparator() + "Ship in Bottle" +
    // System.lineSeparator()
    // + "1000" + System.lineSeparator() + "900" + System.lineSeparator() + "600" +
    // System.lineSeparator()
    // + "500" + System.lineSeparator() + "600" + System.lineSeparator() + "500" +
    // System.lineSeparator()
    // + "500" + System.lineSeparator() + "400" + System.lineSeparator() + "900" +
    // System.lineSeparator()
    // + "700" + System.lineSeparator() + "700" + System.lineSeparator() + "800" +
    // System.lineSeparator()
    // + "800" + System.lineSeparator() + "600" + System.lineSeparator() + "500" +
    // System.lineSeparator()
    // + "700" + System.lineSeparator() + "1000" + System.lineSeparator() + "350" +
    // System.lineSeparator()
    // + "550" + System.lineSeparator() + "550" + System.lineSeparator() + "850" +
    // System.lineSeparator();

    // // Expected result
    // String expected = "Welcome to the Lego Set Competition!\n"
    // + "Enter the name of Lego Set 1\n"
    // + "Enter the number of pieces in Lego Set 1\n"
    // + "Enter the name of Lego Set 2\n"
    // + "Enter the number of pieces in Lego Set 2\n"
    // + "Enter the name of Lego Set 3\n"
    // + "Enter the number of pieces in Lego Set 3\n"
    // + "Enter the number of pieces player 1 used for building on day 1\n"
    // + "Enter the number of pieces player 2 used for building on day 1\n"
    // + "Enter the number of pieces player 1 used for building on day 2\n"
    // + "Enter the number of pieces player 2 used for building on day 2\n"
    // + "Enter the number of pieces player 1 used for building on day 3\n"
    // + "Enter the number of pieces player 2 used for building on day 3\n"
    // + "Enter the number of pieces player 1 used for building on day 4\n"
    // + "Enter the number of pieces player 2 used for building on day 4\n"
    // + "Enter the number of pieces player 1 used for building on day 5\n"
    // + "Enter the number of pieces player 2 used for building on day 5\n"
    // + "Enter the number of pieces player 1 used for building on day 6\n"
    // + "Enter the number of pieces player 2 used for building on day 6\n"
    // + "Enter the number of pieces player 1 used for building on day 7\n"
    // + "Enter the number of pieces player 2 used for building on day 7\n"
    // + "Enter the number of pieces player 1 used for building on day 8\n"
    // + "Enter the number of pieces player 2 used for building on day 8\n"
    // + "Enter the number of pieces player 1 used for building on day 9\n"
    // + "Enter the number of pieces player 2 used for building on day 9\n"
    // + "Enter the number of pieces player 1 used for building on day 10\n"
    // + "Enter the number of pieces player 2 used for building on day 10\n"
    // + "Congratulations to player 2 for winning the Lego Set Competition!\n"
    // + "Additional information about the competition results is below\n"
    // + "Player 1 completed the following sets: Tower Bridge, Eiffel Tower\n"
    // + "Player 1 did not complete the following sets: Ship in Bottle\n"
    // + "Player 1 built a total of 6000 pieces\n"
    // + "Player 2 completed the following sets: Tower Bridge, Eiffel Tower, Ship in
    // Bottle\n"
    // + "Player 2 did not complete the following sets: None\n"
    // + "Player 2 built a total of 7000 pieces\n"
    // + "The competition lasted 10 days";

    // // Runs the program with the input values
    // receiveInput(input);
    // LegoSetCompetition.main(new String[0]);

    // // Retrieves the output from the program
    // String output = getOutput();

    // // Trims the output and verifies it is correct.
    // output = output.replace("\r\n", "\n");
    // assertEquals("Ensure that your results match the format of the ones given in
    // the handout!",
    // expected.trim(), output.trim());
    // }

}
