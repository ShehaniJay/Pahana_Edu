package com.pahanaedu.bookstore.servlet;

import com.pahanaedu.bookstore.dao.BillDAO;
import com.pahanaedu.bookstore.dao.CustomerDAO;
import com.pahanaedu.bookstore.dao.ItemDAO;
import com.pahanaedu.bookstore.model.Bill;
import com.pahanaedu.bookstore.model.Customer;
import com.pahanaedu.bookstore.model.Item;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/bill")
public class BillServlet extends HttpServlet {
    private BillDAO billDAO = new BillDAO();
    private CustomerDAO customerDAO = new CustomerDAO();
    private ItemDAO itemDAO = new ItemDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
            return;
        }

        Map<String, Integer> currentBill = (Map<String, Integer>) session.getAttribute("currentBill");
        if (currentBill == null) {
            currentBill = new HashMap<>();
            session.setAttribute("currentBill", currentBill);
        }

        String accountNumber = request.getParameter("accountNumber");
        String previousAccountNumber = (String) session.getAttribute("selectedAccountNumber");
        if (previousAccountNumber != null && !previousAccountNumber.equals(accountNumber)) {
            currentBill.clear();
            session.setAttribute("currentBill", currentBill);
        }
        session.setAttribute("selectedAccountNumber", accountNumber);

        List<Customer> customers = null;
        Customer customer = null;
        List<Bill> bills = new ArrayList<>();
        List<Item> items = null;
        Map<String, Item> itemsMap = new HashMap<>();

        try {
            customers = customerDAO.getAllCustomers();
            items = itemDAO.getAllItems();
            for (Item item : items) {
                itemsMap.put(item.getItemId(), item);
            }

            if (accountNumber != null && !accountNumber.isEmpty()) {
                customer = customerDAO.getCustomer(accountNumber);
                bills = billDAO.getBillsByCustomer(accountNumber);
                System.out.println("Retrieved " + bills.size() + " bills for account: " + accountNumber);
            }

            double totalAmount = 0;
            if (currentBill != null && !currentBill.isEmpty()) {
                for (Map.Entry<String, Integer> entry : currentBill.entrySet()) {
                    Item item = itemDAO.getItem(entry.getKey());
                    if (item != null) {
                        totalAmount += item.getPrice() * entry.getValue();
                    }
                }
            }
            request.setAttribute("totalAmount", totalAmount);

        } catch (Exception e) {
            throw new ServletException("Failed to retrieve data", e);
        }

        request.setAttribute("customers", customers);
        request.setAttribute("customer", customer);
        request.setAttribute("bills", bills);
        request.setAttribute("items", items);
        request.setAttribute("itemsMap", itemsMap);
        request.setAttribute("currentBill", currentBill);
        request.getRequestDispatcher("/jsp/bill.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
            return;
        }

        String accountNumber = request.getParameter("accountNumber");
        String itemId = request.getParameter("itemId");
        String action = request.getParameter("action");
        int quantity = 0;

        try {
            Map<String, Integer> currentBill = (Map<String, Integer>) session.getAttribute("currentBill");
            if (currentBill == null) {
                currentBill = new HashMap<>();
                session.setAttribute("currentBill", currentBill);
            }

            Item item = itemDAO.getItem(itemId);
            if ("confirm".equals(action)) {
                generateFinalBill(currentBill, accountNumber, request);
                currentBill.clear();
                session.setAttribute("currentBill", currentBill);
                request.setAttribute("showBillOnly", true);
                doGet(request, response);
                return;
            } else if ("update".equals(action) && itemId != null && currentBill.containsKey(itemId)) {
                quantity = Integer.parseInt(request.getParameter("quantity"));
                int currentQuantity = currentBill.get(itemId);
                if (quantity > 0 && item.getStock() + currentQuantity >= quantity) {
                    int delta = quantity - currentQuantity;
                    currentBill.put(itemId, quantity);
                    item.setStock(item.getStock() - delta);
                    itemDAO.updateItem(item);
                    request.setAttribute("message", "Item quantity updated successfully");
                } else if (quantity <= 0) {
                    item.setStock(item.getStock() + currentQuantity);
                    currentBill.remove(itemId);
                    itemDAO.updateItem(item);
                    request.setAttribute("message", "Item removed from bill");
                } else {
                    request.setAttribute("error", "Insufficient stock for update");
                }
            } else if ("delete".equals(action) && itemId != null && currentBill.containsKey(itemId)) {
                int removedQuantity = currentBill.get(itemId);
                item.setStock(item.getStock() + removedQuantity);
                currentBill.remove(itemId);
                itemDAO.updateItem(item);
                request.setAttribute("message", "Item deleted from bill");
            } else if ("add".equals(action) && item != null && item.getStock() >= quantity) {
                quantity = Integer.parseInt(request.getParameter("quantity"));
                currentBill.put(itemId, currentBill.getOrDefault(itemId, 0) + quantity);
                item.setStock(item.getStock() - quantity);
                itemDAO.updateItem(item);
                request.setAttribute("message", "Item added/updated successfully");
            } else {
                request.setAttribute("error", "Insufficient stock or invalid item");
            }

            doGet(request, response);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid quantity");
            doGet(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Bill processing failed", e);
        }
    }

    private void generateFinalBill(Map<String, Integer> currentBill, String accountNumber, HttpServletRequest request) throws Exception {
        System.out.println("Generating final bill for account: " + accountNumber);
        if (currentBill == null || currentBill.isEmpty()) {
            System.out.println("No items in current bill");
            request.setAttribute("error", "No items in bill to generate");
            return;
        }

        Customer customer = customerDAO.getCustomer(accountNumber);
        if (customer == null) {
            System.out.println("Customer not found for account: " + accountNumber);
            request.setAttribute("error", "Customer not found");
            return;
        }
        String customerName = customer.getName();
        boolean billGenerated = false;

        StringBuilder itemsString = new StringBuilder();
        double totalAmount = 0.0;
        for (Map.Entry<String, Integer> entry : currentBill.entrySet()) {
            String itemId = entry.getKey();
            int quantity = entry.getValue();
            Item item = itemDAO.getItem(itemId);
            if (item == null) {
                System.out.println("Item not found for itemId: " + itemId);
                continue;
            }
            if (item.getStock() >= quantity) {
                double itemTotal = item.getPrice() * quantity;
                itemsString.append(itemId).append(":").append(quantity).append(":").append(itemTotal).append(";");
                totalAmount += itemTotal;
                item.setStock(item.getStock() - quantity);
                itemDAO.updateItem(item);
                billGenerated = true;
            } else {
                System.out.println("Insufficient stock for item: " + item.getName());
            }
        }
        if (billGenerated) {
            Bill bill = new Bill();
            bill.setAccountNumber(accountNumber);
            if (itemsString.length() > 0) {
                itemsString.setLength(itemsString.length() - 1);
            }
            bill.setItems(itemsString.toString());
            bill.setTotal(totalAmount);
            billDAO.addBill(bill);

            System.out.println("Bill generation successful");
            request.setAttribute("message", "Final bill generated successfully");
            request.setAttribute("finalCustomerName", customerName);
            request.setAttribute("finalBillDate", bill.getDate() != null ? bill.getDate() : Timestamp.valueOf(LocalDateTime.now())); // Use database date or fallback
            request.setAttribute("finalCustomer", customer);
            request.setAttribute("finalItems", itemsString.toString());
            request.setAttribute("finalTotalAmount", totalAmount);
            request.setAttribute("finalBillId", bill.getBillId());
        } else {
            System.out.println("No valid bills generated");
            request.setAttribute("error", "No valid items were generated in the bill");
        }
    }
}