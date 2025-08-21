package com.pahanaedu.bookstore.servlet;

import com.pahanaedu.bookstore.servlet.LogoutServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class LogoutServletTest {

    private LogoutServlet logoutServlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;

    @BeforeEach
    public void setUp() {
        logoutServlet = new LogoutServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
    }

    @Test
    public void testLogoutWithActiveSession() throws Exception {
        when(request.getSession(false)).thenReturn(session);

        logoutServlet.doGet(request, response);

        verify(session, times(1)).invalidate();

        verify(response, times(1)).sendRedirect("login");
    }

    @Test
    public void testLogoutWithNoSession() throws Exception {
        when(request.getSession(false)).thenReturn(null);

        logoutServlet.doGet(request, response);

        verify(session, never()).invalidate();

        verify(response, times(1)).sendRedirect("login");
    }

    @Test
    public void testLogoutSessionAttributesCleared() throws Exception {
        when(request.getSession(false)).thenReturn(session);

        logoutServlet.doGet(request, response);

        verify(session).invalidate();

        verify(response).sendRedirect("login");
    }
}
