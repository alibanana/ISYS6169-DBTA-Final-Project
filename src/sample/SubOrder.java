package sample;

import java.sql.SQLException;

public class SubOrder {
    private int ColNo;
    private String ProductID;
    private String ProductName;
    private int Qty;
    private String Description;
    private int Price;

    public SubOrder(int ColNo, String productID, String productName, int qty, String description, int price) {
        this.ColNo = ColNo;
        ProductID = productID;
        ProductName = productName;
        Qty = qty;
        Description = description;
        Price = price;
    }

//    public SubOrder(int ColNo, String productID, int qty, String description, int price) throws SQLException {
//        this.ColNo = ColNo;
//        ProductID = productID;
//        ProductName = Database.getProductName(productID);
//        Qty = qty;
//        Description = description;
//        Price = price;
//    }

    public int getColNo() {
        return ColNo;
    }

    public void setColNo(int colNo) {
        ColNo = colNo;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public int getQty() {
        return Qty;
    }

    public void setQty(int qty) {
        Qty = qty;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public String getProductID() {
        return ProductID;
    }

    public void setProductID(String productID) {
        ProductID = productID;
    }
}
