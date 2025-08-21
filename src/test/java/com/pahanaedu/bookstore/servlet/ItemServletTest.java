package com.pahanaedu.bookstore.servlet;

import com.pahanaedu.bookstore.dao.ItemDAO;
import com.pahanaedu.bookstore.model.Item;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import jakarta.servlet.http.*;

import static org.mockito.Mockito.*;

public class ItemServletTest extends Mockito {

    @Test
    void testAddItemServlet() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getParameter("action")).thenReturn("add");
        when(request.getParameter("itemId")).thenReturn("S001");
        when(request.getParameter("name")).thenReturn("Marker");
        when(request.getParameter("price")).thenReturn("50");
        when(request.getParameter("stock")).thenReturn("30");

        ItemServlet servlet = new ItemServlet();
        servlet.doPost(request, response);

        verify(request, atLeastOnce()).getParameter("action");
    }
}
