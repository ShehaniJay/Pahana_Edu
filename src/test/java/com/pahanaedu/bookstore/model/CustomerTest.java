package com.pahanaedu.bookstore.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {

    @Test
    void testDefaultConstructor() {
        Customer customer = new Customer();
        assertAll(
                () -> assertNull(customer.getAccountNumber(), "Account number should be null"),
                () -> assertNull(customer.getName(), "Name should be null"),
                () -> assertNull(customer.getAddress(), "Address should be null"),
                () -> assertNull(customer.getTelephone(), "Telephone should be null")
        );
    }

    @Test
    void testParameterizedConstructor() {
        Customer customer = new Customer("CUST001", "Shehani", "Colombo", "0712345678");
        assertAll(
                () -> assertEquals("CUST001", customer.getAccountNumber()),
                () -> assertEquals("Shehani", customer.getName()),
                () -> assertEquals("Colombo", customer.getAddress()),
                () -> assertEquals("0712345678", customer.getTelephone())
        );
    }

    @Test
    void testAccountNumber() {
        Customer customer = new Customer();
        customer.setAccountNumber("CUST002");
        assertEquals("CUST002", customer.getAccountNumber(), "Account number should match the set value");
    }

    @Test
    void testName() {
        Customer customer = new Customer();
        customer.setName("Fernando");
        assertEquals("Fernando", customer.getName(), "Name should match the set value");
    }

    @Test
    void testAddress() {
        Customer customer = new Customer();
        customer.setAddress("Kandy");
        assertEquals("Kandy", customer.getAddress(), "Address should match the set value");
    }

    @Test
    void testTelephone() {
        Customer customer = new Customer();
        customer.setTelephone("0771234567");
        assertEquals("0771234567", customer.getTelephone(), "Telephone should match the set value");
    }

    @Test
    void testObjectConsistency() {
        Customer customer = new Customer();
        customer.setAccountNumber("CUST010");
        customer.setName("Nimal");
        customer.setAddress("Galle");
        customer.setTelephone("0755555555");

        assertAll(
                () -> assertEquals("CUST010", customer.getAccountNumber()),
                () -> assertEquals("Nimal", customer.getName()),
                () -> assertEquals("Galle", customer.getAddress()),
                () -> assertEquals("0755555555", customer.getTelephone())
        );
    }
}
