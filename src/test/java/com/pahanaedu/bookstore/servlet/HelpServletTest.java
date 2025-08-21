package com.pahanaedu.bookstore.servlet;

import com.pahanaedu.bookstore.servlet.HelpServlet;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.mockito.Mockito.*;

class HelpServletTest {

    private HelpServlet helpServlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private RequestDispatcher dispatcher;

    @BeforeEach
    void setUp() {
        helpServlet = new HelpServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        dispatcher = mock(RequestDispatcher.class);
    }

    @Test
    void testDoGetForwardsToHelpJsp() throws Exception {
        when(request.getRequestDispatcher("/jsp/help.jsp")).thenReturn(dispatcher);

        helpServlet.doGet(request, response);

        verify(request, times(1)).getRequestDispatcher("/jsp/help.jsp");
        verify(dispatcher, times(1)).forward(request, response);
    }
}
