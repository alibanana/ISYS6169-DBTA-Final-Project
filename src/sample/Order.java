package sample;

import java.time.LocalDate;

public class Order {
    private String OrderID;
    private String CashierName;
    private LocalDate OrderDate;
    private int Total;
    private int Cash;
    private int Change;
    private String Status;

    public Order(String orderID, String cashierName, LocalDate orderDate, int total, int cash, String status) {
        OrderID = orderID;
        CashierName = cashierName;
        OrderDate = orderDate;
        Total = total;
        Cash = cash;
        Status = status;
    }

    // Constructor for TableView
    public Order(String orderID, String cashierName, LocalDate orderDate, int total, String status) {
        OrderID = orderID;
        CashierName = cashierName;
        OrderDate = orderDate;
        Total = total;
        Status = status;
    }

    public String getOrderID() {
        return OrderID;
    }

    public void setOrderID(String orderID) {
        OrderID = orderID;
    }

    public LocalDate getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        OrderDate = orderDate;
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

    public int getChange() {
        return Change;
    }

    public void setChange(int change) {
        Change = change;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getCashierName() {
        return CashierName;
    }

    public void setCashierName(String cashierName) {
        CashierName = cashierName;
    }
}
