package com.pahanaedu.bookstore.servlet;

import com.pahanaedu.bookstore.dao.CustomerDAO;
import com.pahanaedu.bookstore.model.Customer;
import org.junit.jupiter.api.*;
import org.mockito.*;

import jakarta.servlet.http.*;
import jakarta.servlet.RequestDispatcher;

import static org.mockito.Mockito.*;

public class CustomerServletTest {

    private CustomerServlet servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RequestDispatcher dispatcher;

    @BeforeEach
    void setUp() {
        servlet = new CustomerServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        dispatcher = mock(RequestDispatcher.class);

        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("username")).thenReturn("admin");
    }

    @Test
    void testAddCustomerServlet() throws Exception {
        when(request.getParameter("action")).thenReturn("add");
        when(request.getParameter("accountNumber")).thenReturn("C100");
        when(request.getParameter("name")).thenReturn("Alex");
        when(request.getParameter("address")).thenReturn("Matara");
        when(request.getParameter("telephone")).thenReturn("0701234567");
        when(request.getRequestDispatcher("/jsp/addCustomer.jsp")).thenReturn(dispatcher);

        servlet.doPost(request, response);

        verify(request).setAttribute(eq("message"), contains("successfully"));
        verify(dispatcher).forward(request, response);
    }

    @Test
    void testDeleteCustomerServlet() throws Exception {
        when(request.getParameter("action")).thenReturn("delete");
        when(request.getParameter("accountNumber")).thenReturn("C100");
        when(request.getRequestDispatcher("/jsp/addCustomer.jsp")).thenReturn(dispatcher);

        servlet.doPost(request, response);

        verify(request).setAttribute(eq("message"), contains("deleted"));
        verify(dispatcher).forward(request, response);
    }
}
