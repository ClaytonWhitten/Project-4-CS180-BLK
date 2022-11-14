# Project-4-CS180-BLK
## Compiling and Running the Marketplace
All the user interaction for this project will occur from within the terminal through the running of the main method of the Main class.
To run it, first compile all the classes and then simply run the main method of Main.java to begin using the marketplace.
The user will navigate the menu through using numbers associated with menu options, and type further details when prompted.

## Who submitted what?

## Class descriptions
### User:
The user class is the superclass of the Buyer and Seller classes. This class holds information about each user's login info, and also houses
the log in and sign up methods. We have verified that this class functions properly by making sure the user files and any global files were all
created, written to, and formatted properly. We also checked to make sure that a user cannot sgn up under an email that already exists or
log in with incorrect details.

### Buyer:
The buyer class is a subclass of the User class, with additional fields for the buyer's shopping cart (Product class) and previous purchases (Sale class). Some of the most important methods in this class include the
methods to read and write to the buyer's personal data file with the proper formatting along with methods to add items to their cart, purchase items, and export
their purchase history. This class also interacts with the Marketplace class that houses many of the methods that a buyer
would use to interact with sellers' storefronts. We tested this method by ensuring that the output to and input from the buyer's files worked
properly and also tested to make sure that the interactions a buyer had with the marketplace were properly reflected in the sellers' files too.

### Seller:
The seller class is another subclass of the User class, this time with an additional field for the seller's list of storefronts (Storefront class).
Once again this class includes methods to read and write to the seller's data file with the proper formatting, along with methods to add
new storefronts. This class is used by the Marketplace class because the marketplace creates seller objects to be able to read each seller's
data when setting up the marketplace for a buyer. We tested this method by ensuring that the output to and input from the seller's files worked
properly and also tested to make sure that the interactions a buyer had with the marketplace were properly reflected in the sellers' files too.

### Product:
The product class holds basic information about a project within a seller's storefront. This info includes the product's name, store, description, available quantity, price, and the
number of carts it's been added into. The class also has various methods for printint to be called in other places within the program. Overall, Product interacts with the whole project,
since it is held within a list of products for a storefront which is in a list of stores for a seller in the marketplace. We tested the methods of this class simply by testing the print
and toString methods to verify outputs into the terminal and user files.

### Sale:
The sale class is a class simply meant to store information about any purchase made. This information includes the customer info, product name, amount purhcased, and the revenue from the purchase.
These sale objects are added to lists in both a buyer's data ans also in every storefront. This means that
the object instances of the Sale class can then be used to provide statistics for both buyers and sellers as to what purchases had been made. In terms of testing, the only type of testing we had
to do for this class was to verify that the getter, setters, and toString worked properly and outputted the right stuff.

### Storefront:
The Storefront class is meant to be a store that is created by a Seller and holds multiple products in a list (Product class) along with a list of purchase history of that storefront (Sale class). 
Naturally, the class interacts with product and sale, however it also interacts with Marketplace and therefore, indirectly with us. The way we test the methods in this class is through testing the Marketplace
class's methods along with this method's print methods and such. Finally we also had to make sure that the storefronts were being properly added to a seller's file.

### Marketplace:
The Marketplace class acts as the means for a buyer to interact with storefronts made by sellers. The class contains methods that allow 
buyers to use the program. Testing for this class was conducted through the use of print methods. The methods in Marketplace are called 
in the main method.

### Main:
The Main class contains the main method and is where the application is run. It calls methods from all the other classes in order to allow
users to use the marketplace. After logging in or signing up, sellers and buyers are able to and then either set up stores and list products 
or buy items from stores that already exist, respectively. This class is tested through print statements and verifying it put out the desired results.