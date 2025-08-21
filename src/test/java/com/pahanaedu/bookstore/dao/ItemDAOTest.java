package com.pahanaedu.bookstore.dao;

import com.pahanaedu.bookstore.model.Item;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ItemDAOTest {

    private static ItemDAO itemDAO;

    @BeforeAll
    static void setup() {
        itemDAO = new ItemDAO();
    }

    @Test
    void testAddItem() throws Exception {
        Item item = new Item();
        item.setItemId("T001");
        item.setName("Test Book");
        item.setPrice(150.0);
        item.setStock(10);

        itemDAO.addItem(item);
        Item fetched = itemDAO.getItem("T001");
        assertNotNull(fetched);
        assertEquals("Test Book", fetched.getName());
    }

    @Test
    void testUpdateItem() throws Exception {
        Item item = itemDAO.getItem("T001");
        item.setPrice(200.0);
        item.setStock(20);

        itemDAO.updateItem(item);
        Item updated = itemDAO.getItem("T001");
        assertEquals(200.0, updated.getPrice());
        assertEquals(20, updated.getStock());
    }

    @Test
    void testDeleteItem() throws Exception {
        itemDAO.deleteItem("T001");
        Item deleted = itemDAO.getItem("T001");
        assertNull(deleted);
    }

    @Test
    void testGetAllItems() throws Exception {
        List<Item> items = itemDAO.getAllItems();
        assertNotNull(items);
        assertTrue(items.size() >= 0);
    }
}
