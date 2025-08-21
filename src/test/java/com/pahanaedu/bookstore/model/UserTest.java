package com.pahanaedu.bookstore.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testDefaultConstructor() {
        User user = new User();
        assertAll(
                () -> assertNull(user.getUsername(), "Username should be null initially"),
                () -> assertNull(user.getPassword(), "Password should be null initially")
        );
    }

    @Test
    void testSetAndGetUsername() {
        User user = new User();
        user.setUsername("admin");
        assertEquals("admin", user.getUsername(), "Username should match the set value");
    }

    @Test
    void testSetAndGetPassword() {
        User user = new User();
        user.setPassword("admin1234");
        assertEquals("admin1234", user.getPassword(), "Password should match the set value");
    }

    @Test
    void testUpdateUsername() {
        User user = new User();
        user.setUsername("admin");
        user.setUsername("user1");
        assertEquals("user1", user.getUsername(), "Username should be updated to new value");
    }

    @Test
    void testUpdatePassword() {
        User user = new User();
        user.setPassword("admin1234");
        user.setPassword("abcd");
        assertEquals("abcd", user.getPassword(), "Password should be updated to new value");
    }

    @Test
    void testEmptyValues() {
        User user = new User();
        user.setUsername("");
        user.setPassword("");
        assertAll(
                () -> assertEquals("", user.getUsername(), "Username should accept empty string"),
                () -> assertEquals("", user.getPassword(), "Password should accept empty string")
        );
    }

    @Test
    void testNullValues() {
        User user = new User();
        user.setUsername(null);
        user.setPassword(null);
        assertAll(
                () -> assertNull(user.getUsername(), "Username should be null"),
                () -> assertNull(user.getPassword(), "Password should be null")
        );
    }
}
