import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        int choice;

        do {

            System.out.println("Welcome to The Marketplace!");

            do { // initial prompt

                System.out.printf("1. Login\n2. Signup\n3. Exit\n");
                choice = scan.nextInt();
                scan.nextLine();

            } while (choice != 1 && choice != 2 && choice != 3);

            int answer;

            do {

                if (choice == 1) { // user chooses to login

                    String username;
                    String password;

                    System.out.printf("Login Menu\n********\n");
                    System.out.println("Enter your username:");
                    username = scan.nextLine();
                    System.out.println("Enter your password:");
                    password = scan.nextLine();

                    User user = new User(username, password);

                    if (!(user.login())) { // user login fails

                        System.out.println("Your username or password is incorrect.");
                        System.out.printf("1. Try again?\n2. Signup\n");

                        do {
                            answer = scan.nextInt();
                            scan.nextLine();
                        } while (answer != 1 && answer != 2);

                        if (answer == 1) {
                            choice = 1;
                            continue;
                        } else if (answer == 2) {
                            choice = 2;
                            continue;
                        }

                    } else { // user login is successful

                        if (user.getType().equals("seller")) { // marketplace for sellers
                            Seller seller = new Seller(user);

                            if (seller.getStoreFronts().isEmpty()) { // the seller has no stores

                                int createOrLogout;
                                System.out.println("You don't have any stores!");
                                do {
                                    System.out.printf("1. Create a store\n2. Logout\n");
                                    createOrLogout = scan.nextInt();
                                    scan.nextLine();
                                } while (createOrLogout != 1 && createOrLogout != 2);

                                if (createOrLogout == 1) {
                                    System.out.println("What is the name of your store?");
                                    String storeName = scan.nextLine();
                                    storeName = storeName.substring(0,1).toUpperCase() + storeName.substring(1);

                                    seller.addStore(storeName, username);
                                    seller.logOut();

                                } else if (createOrLogout == 2) {
                                    choice = 3;
                                    continue;
                                }
                            }

                            if (!(seller.getStoreFronts().isEmpty())) { // the seller has stores
                                // TODO
                            }

                        }

                    }

                } else if (choice == 2) { // user chooses to signup

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
                            System.out.printf("Are you a seller or buyer?\n1. Seller\n2. Buyer\n");
                            sellOrBuy = scan.nextInt();
                            scan.nextLine();
                        } while (sellOrBuy != 1 && sellOrBuy != 2);

                        if (sellOrBuy == 1) {
                            agentType = "seller";
                        } else if (sellOrBuy == 2) {
                            agentType = "buyer";
                        }

                        user = new User(newUsername, newPassword, agentType);

                        if (user.signUp()) {
                            choice = 1;
                            break;
                        } else {

                            System.out.println("User already exists!\n1. Login\n2. Signup\n3. Exit\n");
                            int temp;
                            do {
                                temp = scan.nextInt();
                            } while (temp != 1 && temp != 2 && temp != 3);

                            if (temp == 1) {
                                choice = 1;
                                break;
                            } else if (temp == 2) {
                                choice = 2;
                                break;
                            } else {
                                choice = 3;
                                break;
                            }

                        }

                    } while (true);

                } else if (choice == 3) { // user chooses to exit
                    System.out.println("Goodbye!");
                    break;
                }

            } while (true);

        } while (choice != 3);

    }

}
