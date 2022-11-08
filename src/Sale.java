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

    public String getCustomerInfo() {
        return customerInfo;
    }

    public void setCustomerInfo(String customerInfo) {
        this.customerInfo = customerInfo;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getRevenue() {
        return revenue;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }
}
