public class Sale {

    private String customerInfo;

    private String productName;
    private int quantity;
    private double revenue;

    public Sale(String customerInfo, String productName, int quantity, double revenue) {
        this.customerInfo = customerInfo;
        this.productName = productName;
        this.quantity = quantity;
        this.revenue = revenue;
    }

}
