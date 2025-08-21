package com.pahanaedu.bookstore.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ItemTest {

    @Test
    void testDefaultConstructor() {
        Item item = new Item();
        assertAll(
                () -> assertNull(item.getItemId(), "ItemId should be null"),
                () -> assertNull(item.getName(), "Name should be null"),
                () -> assertEquals(0.0, item.getPrice(), "Price should default to 0.0"),
                () -> assertEquals(0, item.getStock(), "Stock should default to 0")
        );
    }

    @Test
    void testItemId() {
        Item item = new Item();
        item.setItemId("I001");
        assertEquals("I001", item.getItemId(), "ItemId should match the set value");
    }

    @Test
    void testName() {
        Item item = new Item();
        item.setName("Book");
        assertEquals("Book", item.getName(), "Name should match the set value");
    }

    @Test
    void testPrice() {
        Item item = new Item();
        item.setPrice(250.75);
        assertEquals(250.75, item.getPrice(), "Price should match the set value");
    }

    @Test
    void testStock() {
        Item item = new Item();
        item.setStock(50);
        assertEquals(50, item.getStock(), "Stock should match the set value");
    }

    @Test
    void testObjectConsistency() {
        Item item = new Item();
        item.setItemId("I002");
        item.setName("Notebook");
        item.setPrice(99.99);
        item.setStock(30);

        assertAll(
                () -> assertEquals("I002", item.getItemId()),
                () -> assertEquals("Notebook", item.getName()),
                () -> assertEquals(99.99, item.getPrice()),
                () -> assertEquals(30, item.getStock())
        );
    }

    @Test
    void testNegativeStock() {
        Item item = new Item();
        item.setStock(-10);
        assertEquals(-10, item.getStock(), "Stock should store negative values since no validation exists");
    }

    @Test
    void testZeroPrice() {
        Item item = new Item();
        item.setPrice(0.0);
        assertEquals(0.0, item.getPrice(), "Price should allow zero value");
    }
}
