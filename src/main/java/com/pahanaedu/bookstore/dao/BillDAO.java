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
        String sql = "INSERT INTO bills (account_number, items, total) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, bill.getAccountNumber());
            stmt.setString(2, bill.getItems());
            stmt.setDouble(3, bill.getTotal());
            stmt.executeUpdate();


            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                bill.setBillId(generatedKeys.getInt(1));
            }
        }
    }

    public List<Bill> getBillsByCustomer(String accountNumber) throws Exception {
        List<Bill> bills = new ArrayList<>();
        String sql = "SELECT * FROM bills WHERE account_number = ? ORDER BY date DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, accountNumber);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Bill bill = new Bill();
                bill.setBillId(rs.getInt("bill_id"));
                bill.setAccountNumber(rs.getString("account_number"));
                bill.setItems(rs.getString("items"));
                bill.setTotal(rs.getDouble("total"));
                bill.setDate(rs.getTimestamp("date"));
                bills.add(bill);
            }
        }
        return bills;
    }
}