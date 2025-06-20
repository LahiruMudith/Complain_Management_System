package org.example.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.dbcp2.BasicDataSource;
import org.example.dto.UserDto;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserModel {
    public void saveUser(UserDto user, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            ServletContext servletContext = req.getServletContext();
            BasicDataSource ds = (BasicDataSource) servletContext.getAttribute("ds");

            Connection connection = ds.getConnection();

            PreparedStatement pstm = connection.prepareStatement(
                    "INSERT INTO user (UId, name, address, nic, phone, email, password, active_status, role) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"
            );
            pstm.setString(1, user.getUId());
            pstm.setString(2, user.getName());
            pstm.setString(3, user.getAddress());
            pstm.setString(4, user.getNic());
            pstm.setString(5, user.getPhone());
            pstm.setString(6, user.getEmail());
            pstm.setString(7, user.getPassword()); // Ensure password is hashed before saving
            pstm.setBoolean(8, user.isActiveStatus());
            pstm.setString(9, user.getRole());

            int i = pstm.executeUpdate();
            resp.setContentType("text/html");

            if (i > 0) {
                req.setAttribute("message", "User saved successfully");
                RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/view/login.jsp"); // Or your original JSP
                    dispatcher.forward(req, resp);
            } else {
                req.setAttribute("message", "User save failed");
                RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/view/signIn.jsp");
                dispatcher.forward(req, resp);
            }
            connection.close();
        } catch (SQLException e) {
            req.setAttribute("message", "Internal Server Error: ");
            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/view/signIn.jsp");
            dispatcher.forward(req, resp);
            throw new RuntimeException(e);
        }
    }
    public void loginUser(String email, String password, HttpServletResponse resp, HttpServletRequest req) throws ServletException, IOException {
        ServletContext context = req.getServletContext();
        BasicDataSource ds = (BasicDataSource) context.getAttribute("ds");
        try{
            Connection connection = ds.getConnection();
            PreparedStatement pstm = connection.prepareStatement("SELECT * FROM user WHERE email = ? AND password = ? AND active_status = TRUE");
            pstm.setString(1, email);
            pstm.setString(2, password);

            ResultSet resultSet = pstm.executeQuery();
            if (resultSet.next()){
                String role = resultSet.getString("role");
                String name = resultSet.getString("name");
                String uId = resultSet.getString("UId");

                req.setAttribute("message", "success");
                req.setAttribute("uId", uId);
                req.setAttribute("name", name);

                System.out.println("User logged in successfully");
                if (role.equals("Admin")){
                    System.out.println("Admin logged in");

                    req.setAttribute("role", role);

                    RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/view/adminDashboard.jsp");
                    dispatcher.forward(req, resp);
                }else if (role.equals("Employee")) {
                    System.out.println("Employee logged in");

                    req.setAttribute("role", role);

                    RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/view/employeeDashboard.jsp");
                    dispatcher.forward(req, resp);
                }
            }

            connection.close();
        } catch (SQLException e) {
            req.setAttribute("message", "error");
            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/view/login.jsp");
            dispatcher.forward(req, resp);
            throw new RuntimeException(e);
        }
    }
}
