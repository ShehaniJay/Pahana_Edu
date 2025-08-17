package com.pahanaedu.bookstore.servlet;

import com.pahanaedu.bookstore.dao.ItemDAO;
import com.pahanaedu.bookstore.model.Item;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@WebServlet("/item")
public class ItemServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        ItemDAO itemDAO = new ItemDAO();
        try {
            if ("add".equals(action)) {
                Item item = new Item();
                item.setItemId(request.getParameter("itemId"));
                item.setName(request.getParameter("name"));
                item.setPrice(Double.parseDouble(request.getParameter("price")));
                item.setStock(Integer.parseInt(request.getParameter("stock")));
                itemDAO.addItem(item);
                request.setAttribute("message", "Item added successfully");
            } else if ("update".equals(action)) {
                Item item = new Item();
                item.setItemId(request.getParameter("itemId"));
                item.setName(request.getParameter("name"));
                item.setPrice(Double.parseDouble(request.getParameter("price")));
                item.setStock(Integer.parseInt(request.getParameter("stock")));
                itemDAO.updateItem(item);
                request.setAttribute("message", "Item updated successfully");
            } else if ("delete".equals(action)) {
                String itemId = request.getParameter("itemId");
                itemDAO.deleteItem(itemId);
                request.setAttribute("message", "Item deleted successfully");
            }
            doGet(request, response);
        } catch (Exception e) {
            throw new ServletException("Item operation failed", e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ItemDAO itemDAO = new ItemDAO();
        try {
            List<Item> items = itemDAO.getAllItems();
            request.setAttribute("items", items);
            request.getRequestDispatcher("/jsp/manageItems.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Item retrieval failed", e);
        }
    }
}