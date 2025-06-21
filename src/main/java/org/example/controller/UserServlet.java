package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.apache.commons.dbcp2.BasicDataSource;
import org.example.dto.UserDto;
import org.example.model.UserModel;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static java.lang.System.out;

@WebServlet("/controller/user")
@MultipartConfig
public class UserServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
            String uid = UUID.randomUUID().toString();
            String name = req.getParameter("name");
            String address = req.getParameter("address");
            String nic = req.getParameter("nic");
            String phone = req.getParameter("phone");
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            Boolean status = true;
            String role = req.getParameter("role");

            UserDto user = new UserDto(
                    uid, name, address, nic, phone, email, password, status, role
            );

            UserModel userModel = new UserModel();
        String result = userModel.saveUser(user, req, resp);
        req.setAttribute("result", result);
        req.getRequestDispatcher("/WEB-INF/view/signUp.jsp").forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext context = req.getServletContext();
        BasicDataSource ds = (BasicDataSource) context.getAttribute("ds");
        resp.setContentType("application/json");
        ObjectMapper mapper = new ObjectMapper();
        try {
            Connection connection = ds.getConnection();
            PreparedStatement pstm = connection.prepareStatement("SELECT * FROM user");
            ResultSet resultSet = pstm.executeQuery();

            List<Map<String, String>> users = new java.util.ArrayList<>();
            while (resultSet.next()) {
                Map<String, String> user = Map.of(
                        "UId", resultSet.getString("UId"),
                        "name", resultSet.getString("name"),
                        "address", resultSet.getString("address"),
                        "nic", resultSet.getString("nic"),
                        "phone", resultSet.getString("phone"),
                        "email", resultSet.getString("email"),
                        "password", resultSet.getString("password"),
                        "active_status", resultSet.getString("active_status"),
                        "role", resultSet.getString("role"),
                        "image", resultSet.getString("image")
                );
                users.add(user);
            }

            PrintWriter out = resp.getWriter();
            if (users.isEmpty()) {
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
                mapper.writeValue(out, Map.of(
                        "code", "204",
                        "status", "No Content",
                        "message", "No users found"
                ));
            } else {
                resp.setStatus(HttpServletResponse.SC_OK);
                mapper.writeValue(out, Map.of(
                        "code", "200",
                        "status", "success",
                        "data", users
                ));
            }
        } catch (SQLException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            mapper.writeValue(out, Map.of(
                    "code", "500",
                    "status", "error",
                    "message", "Internal Server Error"
            ));
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String uid = req.getParameter("uid");
            String name = req.getParameter("name");
            String address = req.getParameter("address");
            String nic = req.getParameter("nic");
            String phone = req.getParameter("phone");
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            Boolean status = true;
//            Boolean status = req.getParameter("active_status");
            String role = req.getParameter("role");

            ServletContext servletContext =req.getServletContext();
            BasicDataSource ds = (BasicDataSource) servletContext.getAttribute("ds");

            Connection connection = ds.getConnection();
            PreparedStatement pstm = connection.prepareStatement(
                    "UPDATE user SET name = ?, address = ?, nic = ?, phone = ?, email = ?, password = ?, active_status = ?, role = ? WHERE UId = ?"
            );
            pstm.setString(1, name);
            pstm.setString(2, address);
            pstm.setString(3, nic);
            pstm.setString(4, phone);
            pstm.setString(5, email);
            pstm.setString(6, password);
            pstm.setBoolean(7, status);
            pstm.setString(8, role);
            pstm.setString(9, uid);

            int i = pstm.executeUpdate();
            resp.setContentType("application/json");
            ObjectMapper mapper = new ObjectMapper();

            if (i>0){
                resp.setStatus(HttpServletResponse.SC_OK);
                mapper.writeValue(resp.getWriter(), Map.of(
                        "code", "200",
                        "status", "success",
                        "message", "User Update successfully"
                ));
            }else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                mapper.writeValue(resp.getWriter(), Map.of(
                        "code", "400",
                        "status", "error",
                        "message", "User Update Failed"
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uid = req.getParameter("uid");
        ServletContext servletContext = req.getServletContext();
        BasicDataSource ds = (BasicDataSource) servletContext.getAttribute("ds");
        ObjectMapper mapper = new ObjectMapper();

        try {
            Connection connection = ds.getConnection();
            PreparedStatement pstm = connection.prepareStatement(
                    "UPDATE user SET active_status = ? WHERE UId = ?"
            );
            pstm.setBoolean(1, false);
            pstm.setString(2, uid);

            int i = pstm.executeUpdate();
            resp.setContentType("application/json");

            if (i>0){
                resp.setStatus(HttpServletResponse.SC_OK);
                mapper.writeValue(resp.getWriter(), Map.of(
                        "code", "200",
                        "status", "success",
                        "message", "User Delete successfully"
                ));
            }else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                mapper.writeValue(resp.getWriter(), Map.of(
                        "code", "404",
                        "status", "error",
                        "message", "User Delete Failed"
                ));
            }

        } catch (SQLException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            mapper.writeValue(out, Map.of(
                    "code", "500",
                    "status", "error",
                    "message", "Internal Server Error"
            ));
            throw new RuntimeException(e);
        }
    }
}
