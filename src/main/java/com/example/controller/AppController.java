package com.example.controller;

import com.example.util.DatabaseConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/app")
public class AppController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        try {
            Connection conn = DatabaseConnection.getConnection();
            
            if ("get-data".equals(action)) {
                // Example query to fetch data
                String query = "SELECT * FROM your_table";
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery();
                
                // Process the results and set them in request
                // This is a placeholder - you'll need to modify based on your actual table structure
                while (rs.next()) {
                    // Process your data here
                }
                
                // Forward to JSP or send JSON response
                request.getRequestDispatcher("/WEB-INF/views/data.jsp").forward(request, response);
            } else if ("update-data".equals(action)) {
                // Handle data updates
                // You'll need to modify this based on your actual table structure
                String query = "UPDATE your_table SET column = ? WHERE id = ?";
                PreparedStatement stmt = conn.prepareStatement(query);
                // Set parameters
                stmt.executeUpdate();
                
                response.sendRedirect("/app?action=get-data");
            }
            
        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }
    }

    @Override
    public void destroy() {
        try {
            DatabaseConnection.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        super.destroy();
    }
}
