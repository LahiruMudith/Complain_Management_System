package org.example.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.UserModel;

import java.io.IOException;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    UserModel userModel = new UserModel();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        System.out.println("Email: " + email);
        System.out.println("Password: " + password);

        userModel.loginUser(email, password, resp, req);
    }
}
