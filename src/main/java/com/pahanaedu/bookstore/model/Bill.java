package com.pahanaedu.bookstore.model;

public class Bill {
    private String billId;
    private String accountNumber;
    private String itemId;
    private int quantity;
    private double total;

    public Bill() {}

    public Bill(String billId, String accountNumber, String itemId, int quantity, double total) {
        this.billId = billId;
        this.accountNumber = accountNumber;
        this.itemId = itemId;
        this.quantity = quantity;
        this.total = total;
    }

    public String getBillId() { return billId; }
    public void setBillId(String billId) { this.billId = billId; }
    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }
    public String getItemId() { return itemId; }
    public void setItemId(String itemId) { this.itemId = itemId; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }
}
