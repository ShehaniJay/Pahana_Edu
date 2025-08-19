package com.pahanaedu.bookstore.model;

import java.sql.Timestamp;

public class Bill {
    private int billId;
    private String accountNumber;
    private String items;
    private double total;
    private Timestamp date;

    public int getBillId() { return billId; }
    public void setBillId(int billId) { this.billId = billId; }
    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }
    public String getItems() { return items; }
    public void setItems(String items) { this.items = items; }
    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }
    public Timestamp getDate() { return date; }
    public void setDate(Timestamp date) { this.date = date; }
}