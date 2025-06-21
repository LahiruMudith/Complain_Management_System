package org.example.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.dto.UserDto;
import org.example.model.UserModel;

import java.io.IOException;

@WebServlet("/controller/login")
public class LoginServlet extends HttpServlet {
    UserModel userModel = new UserModel();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        System.out.println("Email: " + email);
        System.out.println("Password: " + password);

        UserDto userDto = userModel.loginUser(email, password, resp, req);
        if (!userDto.getUId().equals("null")) {
            req.setAttribute("user", userDto);
            req.getRequestDispatcher("/WEB-INF/view/employeeDashboard.jsp").forward(req, resp);
        } else {
            req.setAttribute("error", "Invalid email or password");
            req.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(req, resp);
        }
    }
}
