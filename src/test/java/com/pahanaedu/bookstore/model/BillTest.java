package com.pahanaedu.bookstore.model;

import org.junit.jupiter.api.Test;
import java.sql.Timestamp;
import static org.junit.jupiter.api.Assertions.*;

class BillTest {

    @Test
    void testBillId() {
        Bill bill = new Bill();
        bill.setBillId(101);
        assertEquals(101, bill.getBillId(), "Bill ID should match the set value");
    }

    @Test
    void testAccountNumber() {
        Bill bill = new Bill();
        bill.setAccountNumber("CUST001");
        assertEquals("CUST001", bill.getAccountNumber(), "Account number should match the set value");
    }

    @Test
    void testItems() {
        Bill bill = new Bill();
        bill.setItems("Book A, Book B");
        assertEquals("Book A, Book B", bill.getItems(), "Items should match the set value");
    }

    @Test
    void testTotal() {
        Bill bill = new Bill();
        bill.setTotal(2500.50);
        assertEquals(2500.50, bill.getTotal(), 0.001, "Total should match the set value");
    }

    @Test
    void testDate() {
        Bill bill = new Bill();
        Timestamp now = new Timestamp(System.currentTimeMillis());
        bill.setDate(now);
        assertEquals(now, bill.getDate(), "Date should match the set value");
    }

    @Test
    void testObjectConsistency() {
        Bill bill = new Bill();
        Timestamp now = new Timestamp(System.currentTimeMillis());

        bill.setBillId(200);
        bill.setAccountNumber("CUST123");
        bill.setItems("Novel, Pen");
        bill.setTotal(999.99);
        bill.setDate(now);

        assertAll(
                () -> assertEquals(200, bill.getBillId(), "Bill ID should match"),
                () -> assertEquals("CUST123", bill.getAccountNumber(), "Account number should match"),
                () -> assertEquals("Novel, Pen", bill.getItems(), "Items should match"),
                () -> assertEquals(999.99, bill.getTotal(), 0.001, "Total should match"),
                () -> assertEquals(now, bill.getDate(), "Date should match")
        );
    }
}
