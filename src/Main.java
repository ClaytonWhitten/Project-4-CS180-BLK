import java.io.IOException;
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

                                    ArrayList<Product> products = new ArrayList<>();

                                    System.out.println("How many products will this store have?");
                                    int productAmount = scan.nextInt();
                                    scan.nextLine();

                                    for (int i = 0; i < productAmount; i++) {

                                        System.out.printf("What is the name of product %d:\n", (i+1));
                                        String productName = scan.nextLine();
                                        System.out.printf("Give a short description of %s\n", productName);
                                        String productDescription = scan.nextLine();
                                        System.out.printf("What quantity of %s will be available?\n", productName);
                                        int availableQuantity = scan.nextInt();
                                        scan.nextLine();
                                        System.out.printf("How much will %s cost?\n", productName);
                                        double productPrice = scan.nextDouble();
                                        scan.nextLine();
                                        Product product = new Product(productName, storeName, productDescription, availableQuantity, productPrice);
                                        products.add(product);

                                    }

                                    seller.addStore(storeName, username, products);

                                    seller.logOut();

                                } else if (createOrLogout == 2) {
                                    choice = 3;
                                    continue;
                                }
                            }

                            if (!(seller.getStoreFronts().isEmpty())) { // the seller has stores
                                int sellerOptions;
                                int storeFrontOptions = 0;

                                do { // for the "back" functionality

                                    do {
                                        System.out.printf("Seller Menu\n********\n");
                                        System.out.printf("1. View all stores\n2. Create a store\n3. Logout\n");
                                        sellerOptions = scan.nextInt();
                                        scan.nextLine();
                                    } while (sellerOptions != 1 && sellerOptions != 2 && sellerOptions != 3);

                                    if (sellerOptions == 1) { // user views all stores
                                        int count = 0;
                                        int storeSelection;

                                        for (int i = 0; i < seller.getStoreFronts().size(); i++) {
                                            count++;
                                        }

                                        do {
                                            System.out.println("Store Selection:");
                                            seller.printStoreFronts();
                                            storeSelection = scan.nextInt();
                                            scan.nextLine();
                                        } while (storeSelection < 1 && storeSelection > count);

                                        boolean back = true;
                                        do { // the "back" functionality for once you have selected a store

                                            do {
                                                System.out.println("Current store: " + seller.getStoreFronts().get(storeSelection - 1).getStoreFrontName());
                                                System.out.printf("1. Import list of products\n2. Edit products\n3. View History\n4. Back\n");
                                                storeFrontOptions = scan.nextInt();
                                                scan.nextLine();
                                            } while (storeFrontOptions != 1 && storeFrontOptions != 2 && storeFrontOptions != 3 && storeFrontOptions != 4);

                                            if (storeFrontOptions == 1) { // user wants to import a list of products
                                                int fileOrBack;
                                                System.out.println("Correct file format:\nname::storeFrontName::description::availableQuantity::price");
                                                do {
                                                    System.out.printf("1. Enter file\n2. Back\n");
                                                    fileOrBack = scan.nextInt();
                                                    scan.nextLine();
                                                } while (fileOrBack != 1 && fileOrBack != 2);

                                                if (fileOrBack == 1) {
                                                    String fileName = scan.nextLine();
                                                    for (int i = 0; i < seller.getStoreFronts().size(); i++) {
                                                        try {
                                                            seller.getStoreFronts().get(i).importProducts(fileName);
                                                            System.out.println("Products added!");
                                                        } catch (Exception e) {
                                                            // nothing
                                                        }
                                                    }
                                                    seller.logOut();
                                                } else if (fileOrBack == 2) {
                                                    back = true;
                                                }

                                            } else if (storeFrontOptions == 2) { // user wants to edit products
                                                int productOptions;
                                                do {
                                                    System.out.printf("1. Choose product\n2. Add product\n3. Back\n");
                                                    productOptions = scan.nextInt();
                                                    scan.nextLine();
                                                } while (productOptions != 1 && productOptions != 2 && productOptions != 3);

                                                int productSelection;
                                                if (productOptions == 1) { // user wants to choose a product
                                                    System.out.println(seller.getStoreFronts().get(storeSelection - 1).getProducts());
                                                    System.out.println("Product format:");
                                                    System.out.println("name::storeFrontName::description::availableQuantity::price::amountInCarts");
                                                    do {
                                                        seller.getStoreFronts().get(storeSelection - 1).printProducts();
                                                        productSelection = scan.nextInt();
                                                        scan.nextLine();
                                                    } while (productSelection < 1 && productSelection > seller.getStoreFronts().get(storeSelection - 1).getProducts().size());

                                                    System.out.println("What is the name of the product?");
                                                    String productName = scan.nextLine();
                                                    seller.getStoreFronts().get(storeSelection - 1).getProducts().get(productSelection - 1).setName(productName);
                                                    System.out.printf("Give a short description of %s\n", productName);
                                                    String productDescription = scan.nextLine();
                                                    seller.getStoreFronts().get(storeSelection - 1).getProducts().get(productSelection - 1).setDescription(productDescription);
                                                    System.out.printf("What quantity of %s will be available?\n", productName);
                                                    int availableQuantity = scan.nextInt();
                                                    scan.nextLine();
                                                    seller.getStoreFronts().get(storeSelection - 1).getProducts().get(productSelection - 1).setAvailableQuantity(availableQuantity);
                                                    System.out.printf("How much will %s cost?\n", productName);
                                                    double productPrice = scan.nextDouble();
                                                    scan.nextLine();
                                                    seller.getStoreFronts().get(storeSelection - 1).getProducts().get(productSelection - 1).setPrice(productPrice);
                                                    System.out.println("Product edited!");
                                                    seller.logOut();
                                                    back = true;
                                                    continue;

                                                } else if (productOptions == 2) { // user wants to add a product

                                                    System.out.println("What is the name of the product?");
                                                    String productName = scan.nextLine();
                                                    System.out.printf("Give a short description of %s\n", productName);
                                                    String productDescription = scan.nextLine();
                                                    System.out.printf("What quantity of %s will be available?\n", productName);
                                                    int availableQuantity = scan.nextInt();
                                                    scan.nextLine();
                                                    System.out.printf("How much will %s cost?\n", productName);
                                                    double productPrice = scan.nextDouble();
                                                    scan.nextLine();
                                                    Product product = new Product(productName, seller.getStoreFronts().get(storeSelection - 1).getStoreFrontName(),
                                                        productDescription, availableQuantity, productPrice);
                                                    seller.getStoreFronts().get(storeSelection - 1).addProduct(product);
                                                    System.out.println("Product added!");
                                                    seller.logOut();
                                                    back = true;
                                                    continue;

                                                } else if (productOptions == 3) {
                                                    back = true;
                                                    continue;
                                                }

                                            } else if (storeFrontOptions == 3) { // user wants to view history of sales
                                                int viewHistOrBack;
                                                do {
                                                    System.out.println("Sales format:");
                                                    System.out.println("customerInfo,productName,quantity,revenue");
                                                    seller.getStoreFronts().get(storeSelection - 1).printSales();
                                                    System.out.printf("1. View history again\n2. Back\n");
                                                    viewHistOrBack = scan.nextInt();
                                                    scan.nextLine();
                                                } while (viewHistOrBack == 1 || (viewHistOrBack != 1 && viewHistOrBack != 2));

                                                if (viewHistOrBack == 2) {
                                                    back = true;
                                                    continue;
                                                }

                                            } else if (storeFrontOptions == 4) { // user wants to go back
                                                break;
                                            }
                                        } while (back);

                                    } else if (sellerOptions == 2) { // user creates a store

                                        System.out.println("What is the name of your store?");
                                        String storeName = scan.nextLine();
                                        storeName = storeName.substring(0, 1).toUpperCase() + storeName.substring(1);

                                        ArrayList<Product> products = new ArrayList<>();

                                        System.out.println("How many products will this store have?");
                                        int productAmount = scan.nextInt();
                                        scan.nextLine();

                                        for (int i = 0; i < productAmount; i++) {

                                            System.out.printf("What is the name of product %d:\n", (i+1));
                                            String productName = scan.nextLine();
                                            System.out.printf("Give a short description of %s\n", productName);
                                            String productDescription = scan.nextLine();
                                            System.out.printf("What quantity of %s will be available?\n", productName);
                                            int availableQuantity = scan.nextInt();
                                            scan.nextLine();
                                            System.out.printf("How much will %s cost?\n", productName);
                                            double productPrice = scan.nextDouble();
                                            scan.nextLine();
                                            Product product = new Product(productName, storeName, productDescription, availableQuantity, productPrice);
                                            products.add(product);

                                        }

                                        seller.addStore(storeName, username, products);
                                        seller.logOut();
                                        storeFrontOptions = 4;

                                    } else if (sellerOptions == 3) { // user logs out
                                        choice = 3;
                                        break;
                                    }

                                } while (storeFrontOptions == 4);

                            }

                        } else if (user.getType().equals("buyer")) { // marketplace for buyers
                            Buyer buyer = new Buyer(user);
                            Marketplace marketplace = new Marketplace();
                            int selectedProduct;
                            boolean productChoice = false;
                            boolean buyerChoice = false;
                            do {
                                System.out.println("Which storefront would you like to access?");
                                System.out.println("1. All products");
                                marketplace.printListStorefronts();
                                System.out.println((marketplace.getAllStores().size() + 2) + ". Search");
                                System.out.println((marketplace.getAllStores().size() + 3) + ". View Cart");
                                System.out.println((marketplace.getAllStores().size() + 4) + ". Logout");
                                try {
                                    int selectedStore = Integer.parseInt(scan.nextLine());
                                    if (selectedStore == 1) { // user selected all products
                                        boolean sorted = false;
                                        do {
                                            ArrayList<Product> currentList = new ArrayList<>();
                                            if (!sorted) {
                                                currentList = marketplace.getAllProducts();
                                            }
                                            marketplace.printProductList(currentList);
                                            System.out.println((marketplace.getAllProducts().size() + 1) + ". Sort by Price");
                                            System.out.println((marketplace.getAllProducts().size() + 2) + ". Sort by Quantity");
                                            System.out.println((marketplace.getAllProducts().size() + 3) + ". Back");
                                            try {
                                                selectedProduct = Integer.parseInt(scan.nextLine());
                                                boolean worked = true;
                                                String ascOrDes;
                                                if (selectedProduct <= marketplace.getAllProducts().size() && selectedProduct >= 1) { // user picked a product

                                                } else if (selectedProduct == marketplace.getAllProducts().size() + 1) { // sorting by price
                                                    productChoice = true;
                                                    sorted = true;
                                                    do {
                                                        System.out.println("Would you like to sort ascending or descending");
                                                        ascOrDes = scan.nextLine();
                                                        if (ascOrDes.equals("ascending")) {
                                                            currentList = marketplace.sort("price", false, marketplace.getAllProducts());
                                                        } else if (ascOrDes.equals("descending")) {
                                                            currentList = marketplace.sort("price", true, marketplace.getAllProducts());
                                                        } else {
                                                            worked = false;
                                                        }
                                                    } while (worked = false);
                                                } else if (selectedProduct == marketplace.getAllProducts().size() + 2) { // sorting by quantity
                                                    sorted = true;
                                                    productChoice = true;
                                                    do {
                                                        System.out.println("Would you like to sort ascending or descending");
                                                        ascOrDes = scan.nextLine();
                                                        if (ascOrDes.equals("ascending")) {
                                                            currentList = marketplace.sort("quantity", false, marketplace.getAllProducts());
                                                        } else if (ascOrDes.equals("descending")) {
                                                            currentList = marketplace.sort("quantity", true, marketplace.getAllProducts());
                                                        } else {
                                                            worked = false;
                                                        }
                                                    } while (worked = false);
                                                } else if (selectedProduct == marketplace.getAllProducts().size() + 3) { // going back
                                                    buyerChoice = true;
                                                    productChoice = false;
                                                } else {
                                                    System.out.println("Try again");
                                                    productChoice = true;
                                                }
                                            } catch (Exception e) {
                                                System.out.println("Try again");
                                                productChoice = true;
                                            }
                                        } while (productChoice == true);
                                    } else if (selectedStore <= marketplace.getAllStores().size() + 1 && selectedStore >= 2) { // user selected a storefront
                                        for (int i = 0; i < marketplace.getAllStores().size(); i++) {
                                            boolean storeFrontSelection = false;
                                            do {
                                                if (selectedStore == i + 2) {
                                                    marketplace.getAllStores().get(i).printStoreFront();
                                                    try {
                                                        selectedProduct = Integer.parseInt(scan.nextLine());
                                                        if (selectedProduct > marketplace.getAllStores().get(i).getProducts().size() || selectedProduct < 1) {
                                                            System.out.println("Try again");
                                                            storeFrontSelection = true;
                                                        }
                                                    } catch (Exception e) {
                                                        System.out.println("Try again");
                                                        storeFrontSelection = true;
                                                    }
                                                }
                                            } while (storeFrontSelection == true);
                                        }
                                    } else if (selectedStore == marketplace.getAllStores().size() + 2) { // user selected to search
                                        System.out.println("Enter search: ");
                                        String buyerSearch = scan.nextLine();
                                    } else if (selectedStore == marketplace.getAllStores().size() + 4) { // user selected to logout
                                        choice = 3;
                                        continue;
                                    } else if (selectedStore == marketplace.getAllStores().size() + 3) { // user selected to view cart

                                    } else {
                                        System.out.println("Try again");
                                        buyerChoice = true;
                                    }
                                } catch (Exception e) {
                                    buyerChoice = true;
                                }
                            } while (buyerChoice == true);

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
