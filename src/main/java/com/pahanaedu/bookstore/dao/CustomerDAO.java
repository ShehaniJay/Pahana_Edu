package com.pahanaedu.bookstore.dao;

import com.pahanaedu.bookstore.model.Customer;
import com.pahanaedu.bookstore.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {
    public void addCustomer(Customer customer) throws Exception {
        String sql = "INSERT INTO customers (account_number, name, address, telephone) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, customer.getAccountNumber());
            stmt.setString(2, customer.getName());
            stmt.setString(3, customer.getAddress());
            stmt.setString(4, customer.getTelephone());
            stmt.executeUpdate();
        }
    }

    public void updateCustomer(Customer customer) throws Exception {
        String sql = "UPDATE customers SET name = ?, address = ?, telephone = ? WHERE account_number = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, customer.getName());
            stmt.setString(2, customer.getAddress());
            stmt.setString(3, customer.getTelephone());
            stmt.setString(4, customer.getAccountNumber());
            stmt.executeUpdate();
        }
    }

    public Customer getCustomer(String accountNumber) throws Exception {
        String sql = "SELECT account_number, name, address, telephone FROM customers WHERE account_number = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, accountNumber);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Customer(
                        rs.getString("account_number"),
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getString("telephone")
                );
            }
            return null;
        }
    }

    public List<Customer> getAllCustomers() throws Exception {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT account_number, name, address, telephone FROM customers";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                customers.add(new Customer(
                        rs.getString("account_number"),
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getString("telephone")
                ));
            }
        }
        return customers;
    }

    public void deleteCustomer(String accountNumber) throws Exception {
        String sql = "DELETE FROM customers WHERE account_number = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, accountNumber);
            stmt.executeUpdate();
        }
    }
}