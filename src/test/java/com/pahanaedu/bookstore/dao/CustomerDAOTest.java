package com.pahanaedu.bookstore.dao;

import com.pahanaedu.bookstore.model.Customer;
import org.junit.jupiter.api.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerDAOTest {
    private CustomerDAO customerDAO;

    @BeforeEach
    void setUp() {
        customerDAO = new CustomerDAO();
    }

    @Test
    void testAddCustomer() throws Exception {
        Customer customer = new Customer("C001", "John Doe", "Colombo", "0771234567");
        customerDAO.addCustomer(customer);

        Customer fetched = customerDAO.getCustomer("C001");
        assertNotNull(fetched);
        assertEquals("John Doe", fetched.getName());
    }

    @Test
    void testUpdateCustomer() throws Exception {
        Customer customer = new Customer("C002", "Jane Doe", "Kandy", "0712345678");
        customerDAO.addCustomer(customer);

        customer.setName("Jane Smith");
        customerDAO.updateCustomer(customer);

        Customer updated = customerDAO.getCustomer("C002");
        assertEquals("Jane Smith", updated.getName());
    }

    @Test
    void testDeleteCustomer() throws Exception {
        Customer customer = new Customer("C003", "Mark", "Galle", "0759876543");
        customerDAO.addCustomer(customer);

        customerDAO.deleteCustomer("C003");
        Customer deleted = customerDAO.getCustomer("C003");
        assertNull(deleted);
    }

    @Test
    void testGetAllCustomers() throws Exception {
        List<Customer> customers = customerDAO.getAllCustomers();
        assertNotNull(customers);
        assertTrue(customers.size() >= 0);
    }
}
