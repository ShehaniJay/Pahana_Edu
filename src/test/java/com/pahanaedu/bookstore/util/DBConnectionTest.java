package com.pahanaedu.bookstore.util;

import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DBConnectionTest {

    @BeforeEach
    void resetConnection() throws Exception {
        java.lang.reflect.Field field = DBConnection.class.getDeclaredField("connection");
        field.setAccessible(true);
        field.set(null, null);
    }

    @Test
    void testGetConnectionSuccess() throws Exception {
        Connection mockConnection = mock(Connection.class);
        try (MockedStatic<DriverManager> mockedDriverManager = Mockito.mockStatic(DriverManager.class)) {
            mockedDriverManager.when(() -> DriverManager.getConnection(anyString(), anyString(), anyString()))
                    .thenReturn(mockConnection);

            Connection conn = DBConnection.getConnection();
            assertNotNull(conn, "Connection should not be null");
        }
    }

    @Test
    void testGetConnectionReusesSameInstance() throws Exception {
        Connection mockConnection = mock(Connection.class);
        try (MockedStatic<DriverManager> mockedDriverManager = Mockito.mockStatic(DriverManager.class)) {
            mockedDriverManager.when(() -> DriverManager.getConnection(anyString(), anyString(), anyString()))
                    .thenReturn(mockConnection);

            Connection conn1 = DBConnection.getConnection();
            Connection conn2 = DBConnection.getConnection();

            assertSame(conn1, conn2, "Connections should be the same instance");
        }
    }

    @Test
    void testClosedConnectionCreatesNewOne() throws Exception {
        Connection mockConnection1 = mock(Connection.class);
        Connection mockConnection2 = mock(Connection.class);

        when(mockConnection1.isClosed()).thenReturn(true);

        try (MockedStatic<DriverManager> mockedDriverManager = Mockito.mockStatic(DriverManager.class)) {
            mockedDriverManager.when(() -> DriverManager.getConnection(anyString(), anyString(), anyString()))
                    .thenReturn(mockConnection1)
                    .thenReturn(mockConnection2);

            Connection conn1 = DBConnection.getConnection();
            Connection conn2 = DBConnection.getConnection();
            assertNotSame(conn1, conn2, "A new connection should be created if old one is closed");
        }
    }

    @Test
    void testInvalidPropertiesThrowsException() {
        try (MockedStatic<DriverManager> mockedDriverManager = Mockito.mockStatic(DriverManager.class)) {
            mockedDriverManager.when(() -> DriverManager.getConnection(anyString(), anyString(), anyString()))
                    .thenThrow(new RuntimeException("Invalid credentials"));

            Exception ex = assertThrows(Exception.class, DBConnection::getConnection);
            assertTrue(ex.getMessage().contains("Invalid"), "Should fail with invalid DB credentials");
        }
    }
}
