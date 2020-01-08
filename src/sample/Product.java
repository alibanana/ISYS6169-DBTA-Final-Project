package sample;

public class Product {
    private int columnNo;
    private String ProductID;
    private String ProductName;
    private int Price;

    public Product(int No, String productID, String productName, int price) {
        columnNo = No;
        ProductID = productID;
        ProductName = productName;
        Price = price;
    }

    public int getColumnNo() {
        return columnNo;
    }

    public void setColumnNo(int columnNo) {
        this.columnNo = columnNo;
    }

    public String getProductID() {
        return ProductID;
    }

    public void setProductID(String productID) {
        ProductID = productID;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }
}
