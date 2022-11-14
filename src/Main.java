import java.io.IOException;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        int choice = 0;

        do {

            System.out.println("Welcome to The Marketplace!");

            do { // initial prompt

                System.out.printf("1. Login\n2. Signup\n3. Exit\n");
                try {
                    choice = scan.nextInt();
                    scan.nextLine();
                } catch (Exception e) {
                    System.out.println("Please enter an integer!");
                }

            } while (choice != 1 && choice != 2 && choice != 3);

            int answer = 0;

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

                        do {
                            System.out.printf("1. Try again?\n2. Signup\n");
                            try {
                                answer = scan.nextInt();
                                scan.nextLine();
                            } catch (Exception e) {
                                System.out.println("Please enter an integer!");
                            }
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

                                int createOrLogout = 0;
                                System.out.println("You don't have any stores!");
                                do {
                                    System.out.printf("1. Create a store\n2. Logout\n");
                                    try {
                                        createOrLogout = scan.nextInt();
                                        scan.nextLine();
                                    } catch (Exception e) {
                                        System.out.println("Please enter an integer!");
                                    }
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
                                int sellerOptions = 0;
                                int storeFrontOptions = 0;

                                do { // for the "back" functionality

                                    do {
                                        System.out.printf("Seller Menu\n********\n");
                                        System.out.printf("1. View all stores\n2. Create a store\n3. Logout\n");
                                        try {
                                            sellerOptions = scan.nextInt();
                                            scan.nextLine();
                                        } catch (Exception e) {
                                            System.out.println("Please enter an integer!");
                                        }
                                    } while (sellerOptions != 1 && sellerOptions != 2 && sellerOptions != 3);

                                    if (sellerOptions == 1) { // user views all stores
                                        int count = 0;
                                        int storeSelection = 0;

                                        for (int i = 0; i < seller.getStoreFronts().size(); i++) {
                                            count++;
                                        }

                                        do {
                                            System.out.println("Store Selection:");
                                            seller.printStoreFronts();
                                            try {
                                                storeSelection = scan.nextInt();
                                                scan.nextLine();
                                            } catch (Exception e) {
                                                System.out.println("Please enter an integer!");
                                            }
                                        } while (storeSelection < 1 && storeSelection > count);

                                        boolean back = true;
                                        do { // the "back" functionality for once you have selected a store

                                            do {
                                                System.out.println("Current store: " + seller.getStoreFronts().get(storeSelection - 1).getStoreFrontName());
                                                System.out.printf("1. Import list of products\n2. Edit products\n3. View history\n4. Export list of products\n5. Back\n");
                                                try {
                                                    storeFrontOptions = scan.nextInt();
                                                    scan.nextLine();
                                                } catch (Exception e) {
                                                    System.out.println("Please enter an integer!");
                                                }
                                            } while (storeFrontOptions != 1 && storeFrontOptions != 2 && storeFrontOptions != 3 && storeFrontOptions != 4 && storeFrontOptions != 5);

                                            if (storeFrontOptions == 1) { // user wants to import a list of products
                                                int fileOrBack = 0;
                                                System.out.println("Correct file format:\nname::storeFrontName::description::availableQuantity::price");
                                                do {
                                                    System.out.printf("1. Enter file\n2. Back\n");
                                                    try {
                                                        fileOrBack = scan.nextInt();
                                                        scan.nextLine();
                                                    } catch (Exception e) {
                                                        System.out.println("Please enter an integer!");
                                                    }
                                                } while (fileOrBack != 1 && fileOrBack != 2);

                                                if (fileOrBack == 1) {
                                                    System.out.println("Enter your file name:");
                                                    String fileName = scan.nextLine();
                                                    for (int i = 0; i < seller.getStoreFronts().size(); i++) {
                                                        try {
                                                            seller.getStoreFronts().get(i).importProducts(fileName);
                                                            System.out.println("Products added!");
                                                        } catch (Exception e) {
                                                            System.out.println("File is invalid");
                                                        }
                                                    }
                                                    seller.logOut();
                                                } else if (fileOrBack == 2) {
                                                    back = true;
                                                }

                                            } else if (storeFrontOptions == 2) { // user wants to edit products
                                                int productOptions = 0;
                                                do {
                                                    System.out.printf("1. Choose product\n2. Add product\n3. Back\n");
                                                    try {
                                                        productOptions = scan.nextInt();
                                                        scan.nextLine();
                                                    } catch (Exception e) {
                                                        System.out.println("Please enter an integer!");
                                                    }
                                                } while (productOptions != 1 && productOptions != 2 && productOptions != 3);

                                                int productSelection = 0;
                                                if (productOptions == 1) { // user wants to choose a product
                                                    System.out.println("Product format:");
                                                    System.out.println("name::storeFrontName::description::availableQuantity::price::amountInCarts");
                                                    do {
                                                        seller.getStoreFronts().get(storeSelection - 1).printProducts();
                                                        try {
                                                            productSelection = scan.nextInt();
                                                            scan.nextLine();
                                                        } catch (Exception e) {
                                                            System.out.println("Please enter an integer!");
                                                        }
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
                                                int viewHistOrBack = 0;
                                                do {
                                                    System.out.println("Sales format:");
                                                    System.out.println("customerInfo,productName,quantity,revenue");
                                                    seller.getStoreFronts().get(storeSelection - 1).printSales();
                                                    System.out.println("-----------------------");
                                                    System.out.printf("1. View history again\n2. Back\n");
                                                    try {
                                                        viewHistOrBack = scan.nextInt();
                                                        scan.nextLine();
                                                    } catch (Exception e) {
                                                        System.out.println("Please enter an integer!");
                                                    }
                                                } while (viewHistOrBack == 1 || (viewHistOrBack != 1 && viewHistOrBack != 2));

                                                if (viewHistOrBack == 2) {
                                                    back = true;
                                                    continue;
                                                }

                                            } else if (storeFrontOptions == 4) { // user wants to export products

                                                int fileOrBack = 0;
                                                System.out.println("File format will be as follows:\nname::storeFrontName::description::availableQuantity::price");
                                                do {
                                                    System.out.printf("1. Export products from %s\n2. Back\n", seller.getStoreFronts().get(storeSelection - 1).getStoreFrontName());
                                                    try {
                                                        fileOrBack = scan.nextInt();
                                                        scan.nextLine();
                                                    } catch (Exception e) {
                                                        System.out.println("Please enter an integer!");
                                                    }
                                                } while (fileOrBack != 1 && fileOrBack != 2);

                                                if (fileOrBack == 1) {
                                                    System.out.println("Enter a filename");
                                                    String fileName = scan.nextLine();
                                                    seller.getStoreFronts().get(storeSelection - 1).exportProductsList(fileName);
                                                } else if (fileOrBack == 2) {
                                                    back = true;
                                                }

                                            } else if (storeFrontOptions == 5) { // user wants to go back
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

                                } while (storeFrontOptions == 5);

                            }

                        } else if (user.getType().equals("buyer")) { // marketplace for buyers
                            Buyer buyer = new Buyer(user);
                            Marketplace marketplace = new Marketplace();
                            int selectedProduct;
                            boolean productChoice = false;
                            boolean buyerChoice = true;
                            int buyOrBack;
                            do {
                                System.out.println("Which storefront would you like to access?");
                                System.out.println("1. All products");
                                marketplace.printListStorefronts();
                                System.out.println((marketplace.getAllStores().size() + 2) + ". Search");
                                System.out.println((marketplace.getAllStores().size() + 3) + ". View Cart");
                                System.out.println((marketplace.getAllStores().size() + 4) + ". View Statistics");
                                System.out.println((marketplace.getAllStores().size() + 5) + ". Export Purchase History");
                                System.out.println((marketplace.getAllStores().size() + 6) + ". Logout");
                                try {
                                    int selectedStore = Integer.parseInt(scan.nextLine());
                                    if (selectedStore == 1) { // user selected all products
                                        boolean sorted = false;
                                        ArrayList<Product> currentList = new ArrayList<>();
                                        do {
                                            if (!sorted) {
                                                currentList = marketplace.getAllProducts();
                                            }
                                            marketplace.printProductList(currentList);
                                            System.out.println((currentList.size() + 1) + ". Sort by Price");
                                            System.out.println((marketplace.getAllProducts().size() + 2) + ". Sort by Quantity");
                                            System.out.println((marketplace.getAllProducts().size() + 3) + ". Back");
                                            try {
                                                selectedProduct = Integer.parseInt(scan.nextLine());
                                                boolean worked = true;
                                                int ascOrDes;
                                                if (selectedProduct <= marketplace.getAllProducts().size() && selectedProduct >= 1) { // user picked a product
                                                    boolean check = true;
                                                    do {
                                                        currentList.get(selectedProduct - 1).printProductDetails();
                                                        System.out.println("1. Buy");
                                                        System.out.println("2. Back");
                                                        try {
                                                            buyOrBack = Integer.parseInt(scan.nextLine());
                                                            if (buyOrBack == 1) { // user wants to buy
                                                                check = true;
                                                                boolean check2 = true;
                                                                do {
                                                                    System.out.println("Select quantity: ");
                                                                    try {
                                                                        int quantityDesired = Integer.parseInt(scan.nextLine());
                                                                        if (quantityDesired > currentList.get(selectedProduct - 1).getAvailableQuantity()) {
                                                                            System.out.println("Not enough available items");
                                                                            check2 = false;
                                                                        } else if (quantityDesired > 0) {
                                                                            check2 = true;
                                                                            marketplace.addToCart(buyer, currentList.get(selectedProduct - 1), quantityDesired);
                                                                        } else {
                                                                            check2 = false;
                                                                        }
                                                                    } catch (Exception e) {
                                                                        check2 = false;
                                                                    }
                                                                } while (check2 == false);
                                                            } else if (buyOrBack == 2) { // user wants to go back
                                                                check = true;
                                                                productChoice = true;
                                                            } else {
                                                                check = false;
                                                            }
                                                        } catch (Exception e) {
                                                            check = false;
                                                        }
                                                    } while (check = false);
                                                } else if (selectedProduct == marketplace.getAllProducts().size() + 1) { // sorting by price
                                                    productChoice = true;
                                                    sorted = true;
                                                    do {
                                                        System.out.println("How would you like to sort?");
                                                        System.out.println("1. Ascending");
                                                        System.out.println("2. Descending");
                                                        try {
                                                            ascOrDes = Integer.parseInt(scan.nextLine());
                                                            if (ascOrDes == 1) {
                                                                worked = true;
                                                                currentList = marketplace.sort("price", false, marketplace.getAllProducts());
                                                            } else if (ascOrDes == 2) {
                                                                worked = true;
                                                                currentList = marketplace.sort("price", true, marketplace.getAllProducts());
                                                            } else {
                                                                worked = false;
                                                            }
                                                        } catch (Exception e) {
                                                            worked = false;
                                                        }
                                                    } while (worked = false);
                                                } else if (selectedProduct == marketplace.getAllProducts().size() + 2) { // sorting by quantity
                                                    productChoice = true;
                                                    sorted = true;
                                                    do {
                                                        System.out.println("How would you like to sort?");
                                                        System.out.println("1. Ascending");
                                                        System.out.println("2. Descending");
                                                        try {
                                                            ascOrDes = Integer.parseInt(scan.nextLine());
                                                            if (ascOrDes == 1) {
                                                                worked = true;
                                                                currentList = marketplace.sort("quantity", false, marketplace.getAllProducts());
                                                            } else if (ascOrDes == 2) {
                                                                worked = true;
                                                                currentList = marketplace.sort("quantity", true, marketplace.getAllProducts());
                                                            } else {
                                                                worked = false;
                                                            }
                                                        } catch (Exception e) {
                                                            worked = false;
                                                        }
                                                    } while (worked = false);
                                                } else if (selectedProduct == marketplace.getAllProducts().size() + 3) { // going back
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
                                            boolean sorted1 = false;
                                            int ascOrDes1;
                                            boolean worked1 = true;
                                            ArrayList<Product> currentList = new ArrayList<>();
                                            do {
                                                if (selectedStore == i + 2) {
                                                    if (!sorted1) {
                                                        currentList = marketplace.getAllStores().get(i).getProducts();
                                                    }
                                                    marketplace.getAllStores().get(i).printStoreFrontByList(currentList);
                                                    System.out.println((marketplace.getAllStores().get(i).getProducts().size() + 1) + ". Sort by Price");
                                                    System.out.println((marketplace.getAllStores().get(i).getProducts().size() + 2) + ". Sort by Quantity");
                                                    System.out.println((marketplace.getAllStores().get(i).getProducts().size() + 3) + ". Back");
                                                    try {
                                                        selectedProduct = Integer.parseInt(scan.nextLine());
                                                        if (selectedProduct <= marketplace.getAllStores().get(i).getProducts().size() && selectedProduct >= 1) { // user picked a product
                                                            storeFrontSelection = true;
                                                            boolean check1 = true;
                                                            int buyOrBack1;
                                                            do {
                                                                currentList.get(selectedProduct - 1).printProductDetails();
                                                                System.out.println("1. Buy");
                                                                System.out.println("2. Back");
                                                                try {
                                                                    buyOrBack1 = Integer.parseInt(scan.nextLine());
                                                                    if (buyOrBack1 == 1) { // user wants to buy
                                                                        check1 = true;
                                                                        boolean check3 = true;
                                                                        do {
                                                                            System.out.println("Select quantity: ");
                                                                            try {
                                                                                int quantityDesired = Integer.parseInt(scan.nextLine());
                                                                                if (quantityDesired > currentList.get(selectedProduct).getAvailableQuantity()) {
                                                                                    System.out.println("Not enough available items");
                                                                                    check3 = false;
                                                                                } else if (quantityDesired > 0) {
                                                                                    check3 = true;
                                                                                    marketplace.addToCart(buyer, currentList.get(selectedProduct), quantityDesired);
                                                                                } else {
                                                                                    check3 = false;
                                                                                    System.out.println("Try again");
                                                                                }
                                                                            } catch (Exception e) {
                                                                                check3 = false;
                                                                                System.out.println("Try again");
                                                                            }
                                                                        } while (check3 == false);
                                                                    } else if (buyOrBack1 == 2) { // user wants to go back
                                                                        check1 = true;
                                                                        storeFrontSelection = true;
                                                                    } else {
                                                                        check1 = false;
                                                                    }
                                                                } catch (Exception e) {
                                                                    check1 = false;
                                                                }
                                                            } while (check1 = false);
                                                        } else if (selectedProduct == marketplace.getAllStores().get(i).getProducts().size() + 1) { // sorting by price
                                                            storeFrontSelection = true;
                                                            sorted1 = true;
                                                            do {
                                                                System.out.println("How would you like to sort?");
                                                                System.out.println("1. Ascending");
                                                                System.out.println("2. Descending");
                                                                try {
                                                                    ascOrDes1 = Integer.parseInt(scan.nextLine());
                                                                    if (ascOrDes1 == 1) {
                                                                        worked1 = true;
                                                                        currentList = marketplace.sort("price", false, marketplace.getAllStores().get(i).getProducts());
                                                                    } else if (ascOrDes1 == 2) {
                                                                        worked1 = true;
                                                                        currentList = marketplace.sort("price", true, marketplace.getAllStores().get(i).getProducts());
                                                                    } else {
                                                                        worked1 = false;
                                                                    }
                                                                } catch (Exception e) {
                                                                    worked1 = false;
                                                                }
                                                            } while (worked1 = false);
                                                        } else if (selectedProduct == marketplace.getAllStores().get(i).getProducts().size() + 2) { // sorting by quantity
                                                            storeFrontSelection = true;
                                                            sorted1 = true;
                                                            do {
                                                                System.out.println("How would you like to sort?");
                                                                System.out.println("1. Ascending");
                                                                System.out.println("2. Descending");
                                                                try {
                                                                    ascOrDes1 = Integer.parseInt(scan.nextLine());
                                                                    if (ascOrDes1 == 1) {
                                                                        worked1 = true;
                                                                        currentList = marketplace.sort("quantity", false, marketplace.getAllStores().get(i).getProducts());
                                                                    } else if (ascOrDes1 == 2) {
                                                                        worked1 = true;
                                                                        currentList = marketplace.sort("quantity", true, marketplace.getAllStores().get(i).getProducts());
                                                                    } else {
                                                                        worked1 = false;
                                                                    }
                                                                } catch (Exception e) {
                                                                    worked1 = false;
                                                                }
                                                            } while (worked1 = false);
                                                        } else if (selectedProduct == marketplace.getAllStores().get(i).getProducts().size() + 3) { // going back
                                                            storeFrontSelection = false;
                                                        } else {
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
                                        boolean search1 = false;
                                        int ascOrDes2;
                                        boolean worked2 = true;
                                        boolean worked3 = true;
                                        do {
                                            System.out.println("Enter search: ");
                                            String buyerSearch = scan.nextLine();
                                            ArrayList<Product> currentList = new ArrayList<>();
                                            currentList = marketplace.search(buyerSearch);
                                            do {
                                                marketplace.printProductList(currentList);
                                                System.out.println((currentList.size() + 1) + ". Sort by Price");
                                                System.out.println((currentList.size() + 2) + ". Sort by Quantity");
                                                System.out.println((currentList.size() + 3) + ". Back");
                                                try {
                                                    int searchInt = Integer.parseInt(scan.nextLine());
                                                    if (searchInt >= 1 && searchInt <= currentList.size()) { // user selected a product
                                                        worked2 = true;
                                                        boolean check4 = true;
                                                        int buyOrBack1;
                                                        do {
                                                            currentList.get(searchInt - 1).printProductDetails();
                                                            System.out.println("1. Buy");
                                                            System.out.println("2. Back");
                                                            try {
                                                                buyOrBack1 = Integer.parseInt(scan.nextLine());
                                                                if (buyOrBack1 == 1) { // user wants to buy
                                                                    check4 = true;
                                                                    boolean check3 = true;
                                                                    do {
                                                                        System.out.println("Select quantity: ");
                                                                        try {
                                                                            int quantityDesired = Integer.parseInt(scan.nextLine());
                                                                            if (quantityDesired > currentList.get(searchInt - 1).getAvailableQuantity()) {
                                                                                System.out.println("Not enough available items");
                                                                                check3 = false;
                                                                            } else if (quantityDesired > 0) {
                                                                                check3 = true;
                                                                                marketplace.addToCart(buyer, currentList.get(searchInt - 1), quantityDesired);
                                                                            } else {
                                                                                check3 = false;
                                                                            }
                                                                        } catch (Exception e) {
                                                                            check3 = false;
                                                                        }
                                                                    } while (check3 == false);
                                                                    search1 = false;
                                                                } else if (buyOrBack1 == 2) { // user wants to go back
                                                                    check4 = true;
                                                                    search1 = true;
                                                                } else {
                                                                    check4 = false;
                                                                }
                                                            } catch (Exception e) {
                                                                check4 = false;
                                                            }
                                                        } while (check4 = false);
                                                    } else if (searchInt == currentList.size() + 1) { // sort by price
                                                        search1 = true;
                                                        do {
                                                            System.out.println("How would you like to sort?");
                                                            System.out.println("1. Ascending");
                                                            System.out.println("2. Descending");
                                                            try {
                                                                ascOrDes2 = Integer.parseInt(scan.nextLine());
                                                                if (ascOrDes2 == 1) {
                                                                    worked3 = true;
                                                                    currentList = marketplace.sort("price", false, currentList);
                                                                } else if (ascOrDes2 == 2) {
                                                                    worked3 = true;
                                                                    currentList = marketplace.sort("price", true, currentList);
                                                                } else {
                                                                    worked3 = false;
                                                                }
                                                            } catch (Exception e) {
                                                                worked3 = false;
                                                            }
                                                        } while (worked3 == false);
                                                        worked2 = false;
                                                    } else if (searchInt == currentList.size() + 2) { // sort by quantity
                                                        search1 = true;
                                                        do {
                                                            System.out.println("How would you like to sort?");
                                                            System.out.println("1. Ascending");
                                                            System.out.println("2. Descending");
                                                            try {
                                                                ascOrDes2 = Integer.parseInt(scan.nextLine());
                                                                if (ascOrDes2 == 1) {
                                                                    worked3 = true;
                                                                    currentList = marketplace.sort("quantity", false, currentList);
                                                                } else if (ascOrDes2 == 2) {
                                                                    worked3 = true;
                                                                    currentList = marketplace.sort("quantity", true, currentList);
                                                                } else {
                                                                    worked3 = false;
                                                                }
                                                            } catch (Exception e) {
                                                                worked3 = false;
                                                            }
                                                        } while (worked3 == false);
                                                        worked2 = false;
                                                    } else if (searchInt == currentList.size() + 3) { // going back
                                                        worked2 = true;
                                                        search1 = false;

                                                    } else {
                                                        System.out.println("Try again");
                                                        search1 = true;
                                                        worked2 = false;
                                                    }
                                                } catch (Exception e) {
                                                    System.out.println("Try again");
                                                    search1 = true;
                                                    worked2 = false;
                                                }
                                            } while (worked2 == false);
                                        } while (search1 == true);
                                    } else if (selectedStore == marketplace.getAllStores().size() + 6) { // user selected to log out
                                        choice = 3;
                                        buyerChoice = false;
                                        continue;
                                    } else if (selectedStore == marketplace.getAllStores().size() + 3) { // user selected to view cart
                                        boolean viewCart = true;
                                        do {
                                            buyer.printCart();
                                            System.out.println("-----------------------");
                                            System.out.println("1. Checkout");
                                            System.out.println("2. Remove product");
                                            System.out.println("3. Back");
                                            boolean productSelection = false;
                                            try {
                                                int cartSelection = Integer.parseInt(scan.nextLine());
                                                if (cartSelection == 1) { // checking out
                                                    viewCart = false;
                                                    marketplace.buyCart(buyer);
                                                } else if (cartSelection == 2) { // removing product
                                                    do {
                                                        System.out.println("Which product: ");
                                                        buyer.printCart();
                                                        try {
                                                            int cartProduct = Integer.parseInt(scan.nextLine());
                                                            if (cartProduct >= 1 && cartProduct <= buyer.getShoppingCart().size()) {
                                                                for (int i = 0; i < buyer.getShoppingCart().size(); i++) {
                                                                    if (i == cartProduct - 1) {
                                                                        buyer.removeFromCart(i);
                                                                    }
                                                                }
                                                                productSelection = false;
                                                            } else {
                                                                System.out.println("Try again");
                                                                productSelection = true;
                                                            }
                                                        } catch (Exception e) {
                                                            System.out.println("Try again");
                                                            productSelection = true;
                                                        }
                                                    } while (productSelection == true);
                                                    viewCart = true;
                                                } else if (cartSelection == 3) { // going back
                                                    viewCart = false;
                                                } else {
                                                    System.out.println("Try again");
                                                    viewCart = true;
                                                }
                                            } catch (Exception e) {
                                                System.out.println("Try again");
                                                viewCart = true;
                                            }
                                        } while (viewCart == true);

                                    } else if (selectedStore == marketplace.getAllStores().size() + 4) { // user selected to view statistics
                                        boolean viewStats = false;
                                        do {
                                            System.out.println("Which database would you like to view?");
                                            System.out.println("1. All stores");
                                            System.out.println("2. Your purchases");
                                            System.out.println("3. Back");
                                            try {
                                                int database = Integer.parseInt(scan.nextLine());
                                                if (database == 1) {
                                                    ArrayList<StoreFront> currentList = new ArrayList<>();
                                                    currentList = marketplace.getAllStores();
                                                    boolean sortDatabase = true;
                                                    do {
                                                        marketplace.printStoreStatistics(currentList);
                                                        System.out.println("How would you like to sort the database");
                                                        System.out.println("1. Ascending");
                                                        System.out.println("2. Descending");
                                                        System.out.println("3. Back");
                                                        try {
                                                            int sorter = Integer.parseInt(scan.nextLine());
                                                            if (sorter == 1) {

                                                                sortDatabase = true;
                                                            } else if (sorter == 2) {

                                                                sortDatabase = true;
                                                            } else if (sorter == 3) {
                                                                sortDatabase = false;
                                                            } else {
                                                                System.out.println("Try again");
                                                                sortDatabase = true;
                                                            }
                                                        } catch (Exception e) {
                                                            System.out.println("Try again");
                                                            sortDatabase = true;
                                                        }
                                                    } while (sortDatabase == true);
                                                    viewStats = true;
                                                } else if (database == 2) {
                                                    marketplace.printStoreStatsBuyer(buyer);
                                                    System.out.println();
                                                    viewStats = true;
                                                } else if (database == 3) {
                                                    viewStats = false;
                                                } else {
                                                    System.out.println("Try again");
                                                    viewStats = true;
                                                }
                                            } catch (Exception e) {
                                                System.out.println("Try again");
                                                viewStats = true;
                                            }
                                        } while (viewStats == true);
                                    } else if (selectedStore == marketplace.getAllStores().size() + 5) { // export purchase history
                                        boolean works = true;
                                        do {
                                            System.out.println("Enter file name");
                                            try {
                                                buyer.exportPurchaseList(scan.nextLine());
                                                works = true;
                                            } catch (Exception e) {
                                                System.out.println("Try again");
                                                works = false;
                                            }
                                        } while (works == false);
                                    } else {
                                        System.out.println("Try again");
                                    }
                                } catch (Exception e) {
                                    buyerChoice = true;
                                }
                            } while (buyerChoice == true);
                            buyer.logOut();
                        }

                    }

                } else if (choice == 2) { // user chooses to signup

                    String newUsername;
                    String newPassword;
                    String agentType = "";
                    int sellOrBuy = 0;
                    User user = null;

                    do {

                        System.out.printf("Signup Menu\n********\n");
                        System.out.println("Enter a username:");
                        newUsername = scan.nextLine();
                        System.out.println("Enter a strong password:");
                        newPassword = scan.nextLine();

                        do {
                            System.out.printf("Are you a seller or buyer?\n1. Seller\n2. Buyer\n");
                            try {
                                sellOrBuy = scan.nextInt();
                                scan.nextLine();
                            } catch (Exception e) {
                                System.out.println("Please enter an integer!");
                            }
                        } while (sellOrBuy != 1 && sellOrBuy != 2);

                        if (sellOrBuy == 1) {
                            agentType = "seller";
                        } else if (sellOrBuy == 2) {
                            agentType = "buyer";
                        }

                        user = new User(newUsername, newPassword, agentType);

                        if (user.signUp()) {
                            System.out.println("Please restart the program to register your account");
                            choice = 3;
                            break;
                        } else {

                            int temp = 0;
                            do {
                                System.out.println("User already exists!\n1. Login\n2. Signup\n3. Exit\n");
                                try {
                                    temp = scan.nextInt();
                                    scan.nextLine();
                                } catch (Exception e) {
                                    System.out.println("Please enter an integer!");
                                }
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
