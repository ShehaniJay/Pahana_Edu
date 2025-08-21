package com.pahanaedu.bookstore.dao;

import com.pahanaedu.bookstore.dao.BillDAO;
import com.pahanaedu.bookstore.dao.ItemDAO;
import com.pahanaedu.bookstore.model.Bill;
import com.pahanaedu.bookstore.model.Item;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BillDAOTest {

    private BillDAO billDAO;
    private ItemDAO itemDAO;

    @BeforeEach
    public void setUp() {
        billDAO = new BillDAO();
        itemDAO = new ItemDAO();
    }

    @Test
    @DisplayName("TC01: Add Bill")
    public void testAddBill() throws Exception {
        Bill bill = new Bill();
        bill.setAccountNumber("ACC001");
        bill.setItems("ITEM001:2:200;ITEM002:1:100");
        bill.setTotal(300.0);

        billDAO.addBill(bill);
        assertTrue(bill.getBillId() > 0, "Bill ID should be generated");
    }

    @Test
    @DisplayName("TC02: Get Bills By Customer")
    public void testGetBillsByCustomer() throws Exception {
        List<Bill> bills = billDAO.getBillsByCustomer("ACC001");
        assertNotNull(bills);
        assertTrue(bills.size() >= 1, "Should return at least one bill");
    }

    @Test
    @DisplayName("TC03: Update Stock")
    public void testUpdateItemStock() throws Exception {
        Item item = itemDAO.getItem("ITEM001");
        int originalStock = item.getStock();
        item.setStock(originalStock - 1);
        itemDAO.updateItem(item);

        Item updatedItem = itemDAO.getItem("ITEM001");
        assertEquals(originalStock - 1, updatedItem.getStock());
    }
}
