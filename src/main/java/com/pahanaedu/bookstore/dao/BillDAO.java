package com.pahanaedu.bookstore.dao;

import com.pahanaedu.bookstore.model.Bill;
import com.pahanaedu.bookstore.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BillDAO {
    public void addBill(Bill bill) throws Exception {
        String sql = "INSERT INTO bills (bill_id, account_number, item_id, quantity, total) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, bill.getBillId());
            stmt.setString(2, bill.getAccountNumber());
            stmt.setString(3, bill.getItemId());
            stmt.setInt(4, bill.getQuantity());
            stmt.setDouble(5, bill.getTotal());
            stmt.executeUpdate();
        }
    }

    public List<Bill> getBillsByCustomer(String accountNumber) throws Exception {
        List<Bill> bills = new ArrayList<>();
        String sql = "SELECT * FROM bills WHERE account_number = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, accountNumber);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Bill bill = new Bill();
                bill.setBillId(rs.getString("bill_id"));
                bill.setAccountNumber(rs.getString("account_number"));
                bill.setItemId(rs.getString("item_id"));
                bill.setQuantity(rs.getInt("quantity"));
                bill.setTotal(rs.getDouble("total"));
                bills.add(bill);
            }
        }
        return bills;
    }

}