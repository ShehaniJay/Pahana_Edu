package com.pahanaedu.bookstore.servlet;

import com.pahanaedu.bookstore.servlet.BillServlet;
import com.pahanaedu.bookstore.dao.ItemDAO;
import com.pahanaedu.bookstore.model.Item;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class BillServletTest {

    private BillServlet billServlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;

    @BeforeEach
    public void setup() {
        billServlet = new BillServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);

        when(request.getSession(false)).thenReturn(session);
    }

    @Test
    public void testAddItemToBill() throws Exception {
        Map<String, Integer> currentBill = new HashMap<>();
        when(session.getAttribute("currentBill")).thenReturn(currentBill);
        when(request.getParameter("action")).thenReturn("add");
        when(request.getParameter("accountNumber")).thenReturn("ACC001");
        when(request.getParameter("itemId")).thenReturn("ITEM001");
        when(request.getParameter("quantity")).thenReturn("2");

        billServlet.doPost(request, response);

        assertTrue(currentBill.containsKey("ITEM001"));
        assertEquals(2, currentBill.get("ITEM001"));
    }
}
