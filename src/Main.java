import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        int choice;

        do {

            System.out.println("Welcome to The Marketplace!");

            do {

                System.out.printf("1. Login\n2. Signup\n3. Exit\n");
                choice = scan.nextInt();
                scan.nextLine();

            } while (choice != 1 && choice != 2 && choice != 3);

            if (choice == 1) {

                String username;
                String password;

                System.out.printf("Login Menu\n********\n");
                System.out.println("Enter your username:");
                username = scan.nextLine();
                System.out.println("Enter your password:");
                password = scan.nextLine();

                User user = new User(username, password);

                if (!(user.login())) {

                    System.out.println("Your username or password is incorrect.");
                    System.out.println("1. Try again?\n2. Signup\n");

                }

            } else if (choice == 2) {

                String newUsername;
                String newPassword;
                String agentType = "";
                int sellOrBuy;
                User user = null;

                do {

                    System.out.printf("Signup Menu\n********\n");
                    System.out.println("Enter a username:");
                    newUsername = scan.nextLine();
                    System.out.println("Enter a strong password:");
                    newPassword = scan.nextLine();

                    do {
                        System.out.printf("Are you a seller or buyer?\n1. Seller\n2. Buyer");
                        sellOrBuy = scan.nextInt();
                        scan.nextLine();
                    } while (sellOrBuy != 1 && sellOrBuy != 2);

                    if (sellOrBuy == 1) {
                        agentType = "seller";
                    } else if (sellOrBuy == 2) {
                        agentType = "buyer";
                    }

                    user = new User(newUsername, newPassword, agentType);

                } while (!(user.signUp()));

            } else if (choice == 3) {
                System.out.println("Goodbye!");
                break;
            }

        } while (choice != 3);

    }

}
