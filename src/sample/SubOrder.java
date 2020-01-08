package sample;

public class SubOrder {
    private int ColNo;
    private String OrderID;
    private String ProductID;
    private String ProductName;
    private int Qty;
    private String Description;
    private int Price;

    public SubOrder(int ColNo, String orderID, String productName, int qty, String description, int price) {
        this.ColNo = ColNo;
        OrderID = orderID;
        ProductName = productName;
        Qty = qty;
        Description = description;
        Price = price;
    }


    // Constructor for TableView
    public SubOrder(int ColNo, String orderID, String productName, int qty, String description) {
        this.ColNo = ColNo;
        OrderID = orderID;
        ProductName = productName;
        Qty = qty;
        Description = description;
    }

    public int getColNo() {
        return ColNo;
    }

    public void setColNo(int colNo) {
        ColNo = colNo;
    }

    public String getOrderID() {
        return OrderID;
    }

    public void setOrderID(String orderID) {
        OrderID = orderID;
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
