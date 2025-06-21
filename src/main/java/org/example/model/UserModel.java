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
    public String saveUser(UserDto user, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
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

            connection.close();
            if (i > 0) {
                return "success";
            } else {
                return "Fail";
            }
        } catch (SQLException e) {
            return "Internal Server Error";
        }
    }
    public UserDto loginUser(String email, String password, HttpServletResponse resp, HttpServletRequest req) throws ServletException, IOException {
        ServletContext context = req.getServletContext();
        BasicDataSource ds = (BasicDataSource) context.getAttribute("ds");
        try{
            Connection connection = ds.getConnection();
            PreparedStatement pstm = connection.prepareStatement("SELECT * FROM user WHERE email = ? AND password = ? AND active_status = TRUE");
            pstm.setString(1, email);
            pstm.setString(2, password);

            UserDto user = new UserDto();

            ResultSet resultSet = pstm.executeQuery();
            if (resultSet.next()){
                user.setUId(resultSet.getString("UId"));
                user.setName(resultSet.getString("name"));
                user.setAddress(resultSet.getString("address"));
                user.setNic(resultSet.getString("nic"));
                user.setPhone(resultSet.getString("phone"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setActiveStatus(resultSet.getBoolean("active_status"));
                user.setRole(resultSet.getString("role"));
            }
            connection.close();
            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
