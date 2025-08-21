package com.pahanaedu.bookstore.servlet;

import com.pahanaedu.bookstore.dao.UserDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class LoginServletTest extends Mockito {

    private LoginServlet loginServlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RequestDispatcher dispatcher;
    private UserDAO userDAO;

    @BeforeEach
    void setUp() {
        loginServlet = new LoginServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        dispatcher = mock(RequestDispatcher.class);
        userDAO = mock(UserDAO.class);

        // Inject mocked DAO into servlet (if using dependency injection; else you'd refactor servlet slightly)
    }

    @Test
    void testValidLogin() throws Exception {
        when(request.getParameter("username")).thenReturn("admin");
        when(request.getParameter("password")).thenReturn("1234");
        when(userDAO.validateUser("admin", "1234")).thenReturn(true);

        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher("/jsp/dashboard.jsp")).thenReturn(dispatcher);

        loginServlet.doPost(request, response);

        verify(session).setAttribute("username", "admin");
        verify(dispatcher).forward(request, response);
    }

    @Test
    void testInvalidLogin() throws Exception {
        when(request.getParameter("username")).thenReturn("wrong");
        when(request.getParameter("password")).thenReturn("wrong");
        when(userDAO.validateUser("wrong", "wrong")).thenReturn(false);

        when(request.getRequestDispatcher("/jsp/login.jsp")).thenReturn(dispatcher);

        loginServlet.doPost(request, response);

        verify(request).setAttribute(eq("error"), anyString());
        verify(dispatcher).forward(request, response);
    }

    @Test
    void testSessionExistsOnGet() throws Exception {
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("username")).thenReturn("admin");

        loginServlet.doGet(request, response);

        verify(response).sendRedirect(contains("/jsp/dashboard.jsp"));
    }

    @Test
    void testNoSessionOnGet() throws Exception {
        when(request.getSession(false)).thenReturn(null);
        when(request.getRequestDispatcher("/jsp/login.jsp")).thenReturn(dispatcher);

        loginServlet.doGet(request, response);

        verify(dispatcher).forward(request, response);
    }

    @Test
    void testExceptionDuringLogin() throws Exception {
        when(request.getParameter("username")).thenReturn("admin");
        when(request.getParameter("password")).thenReturn("1234");
        when(userDAO.validateUser(anyString(), anyString())).thenThrow(new RuntimeException("DB error"));

        try {
            loginServlet.doPost(request, response);
        } catch (ServletException e) {
            assert(e.getMessage().contains("Login failed"));
        }
    }
}
