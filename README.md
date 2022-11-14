# Project-4-CS180-BLK
## Compiling and Running the Marketplace
All the user interaction for this project will occur from within the terminal through the running of the main method of the Main class.
To run it, first compile all the classes and then simply run the main method of Main.java to begin using the marketplace.
The user will navgate the menu through using numbers associated with menu options, and type further details when prompted.

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