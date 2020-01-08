package sample;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Order {
    private String OrderID;
    private String CashierName;
    private LocalDateTime DateTime;
    private int Total;
    private int Cash;
    private int Change;
    private String Status;

    public Order(String orderID, String cashierName, LocalDateTime dateTime, int total, int cash, String status) {
        OrderID = orderID;
        CashierName = cashierName;
        DateTime = dateTime;
        Total = total;
        Cash = cash;
        Status = status;
    }

    // Constructor for TableView
    public Order(String orderID, String cashierName, LocalDateTime dateTime, int total, String status) {
        OrderID = orderID;
        CashierName = cashierName;
        DateTime = dateTime;
        Total = total;
        Status = status;
    }

    public String getOrderID() {
        return OrderID;
    }

    public void setOrderID(String orderID) {
        OrderID = orderID;
    }

    public void setOrderDate(LocalDateTime dateTime) {
        DateTime = dateTime;
    }
    public LocalDateTime OrderDate(LocalDateTime dateTime) {
        return DateTime;
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