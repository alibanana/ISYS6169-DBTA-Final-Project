package sample;

import java.time.LocalDateTime;

public class Order {
    private String OrderID;
    private String CashierName;
    private LocalDateTime DateTime;
    private String Branch;
    private int Total;
    private int Cash;

    public Order(String orderID, String cashierName, LocalDateTime dateTime, int total, int cash) {
        OrderID = orderID;
        CashierName = cashierName;
        DateTime = dateTime;
        Total = total;
        Cash = cash;
    }

    // Constructor for TableView
    public Order(String orderID, String cashierName, LocalDateTime dateTime, String branch, int total, int cash) {
        OrderID = orderID;
        CashierName = cashierName;
        DateTime = dateTime;
        Branch = branch;
        Total = total;
        Cash = cash;
    }

    public String getOrderID() {
        return OrderID;
    }

    public void setOrderID(String orderID) {
        OrderID = orderID;
    }

    public String getCashierName() {
        return CashierName;
    }

    public void setCashierName(String cashierName) {
        CashierName = cashierName;
    }

    public String getDateTime() {
        return DateTime.toString().replace("T", " ");
    }

    public LocalDateTime getLocalDateTime() {
        return DateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        DateTime = dateTime;
    }

    public String getBranch() {
        return Branch;
    }

    public void setBranch(String branch) {
        Branch = branch;
    }

    public int getTotal() {
        return Total;
    }

    public void setTotal(int total) {
        Total = total;
    }

    public int getCash() {
        return Cash;
    }

    public void setCash(int cash) {
        Cash = cash;
    }
}