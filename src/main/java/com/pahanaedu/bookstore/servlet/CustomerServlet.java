package com.pahanaedu.bookstore.servlet;

import com.pahanaedu.bookstore.dao.CustomerDAO;
import com.pahanaedu.bookstore.model.Customer;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/customer")
public class CustomerServlet extends HttpServlet {
    private CustomerDAO customerDAO = new CustomerDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
            return;
        }

        String action = request.getParameter("action");
        String accountNumber = request.getParameter("accountNumber");

        try {
            if ("edit".equals(action) && accountNumber != null) {
                Customer customer = customerDAO.getCustomer(accountNumber);
                request.setAttribute("customer", customer);
            }
            List<Customer> customers = customerDAO.getAllCustomers();
            request.setAttribute("customers", customers);
            request.getRequestDispatcher("/jsp/addCustomer.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Failed to load customers", e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
            return;
        }

        String action = request.getParameter("action");
        String accountNumber = request.getParameter("accountNumber");
        String name = request.getParameter("name");
        String address = request.getParameter("address");
        String telephone = request.getParameter("telephone");

        try {
            if ("add".equals(action) && accountNumber != null && !accountNumber.isEmpty()) {
                Customer existingCustomer = customerDAO.getCustomer(accountNumber);
                if (existingCustomer == null) {
                    Customer customer = new Customer(accountNumber, name, address, telephone);
                    customerDAO.addCustomer(customer);
                    request.setAttribute("message", "Customer added successfully!");
                } else {
                    request.setAttribute("message", "Account number already exists!");
                }
            } else if ("edit".equals(action) && accountNumber != null) {
                Customer customer = new Customer(accountNumber, name, address, telephone);
                customerDAO.updateCustomer(customer);
                request.setAttribute("message", "Customer updated successfully!");
            } else if ("delete".equals(action) && accountNumber != null) {
                customerDAO.deleteCustomer(accountNumber);
                request.setAttribute("message", "Customer deleted successfully!");
            }
            List<Customer> customers = customerDAO.getAllCustomers();
            request.setAttribute("customers", customers);
            request.getRequestDispatcher("/jsp/addCustomer.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Operation failed", e);
        }
    }
}