package org.example.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.dto.UserDto;
import org.example.model.ComplanitModel;
import org.example.model.UserModel;

import java.io.IOException;

@WebServlet("/dashboardServlet")
public class DashboardServlet extends HttpServlet {
    ComplanitModel complanitModel = new ComplanitModel();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uId = req.getParameter("uid");
        String name = req.getParameter("name");
        String role = req.getParameter("role");

        UserDto user = new UserDto();
        user.setUId(uId);
        user.setName(name);
        user.setRole(role);

//        complanitModel.getUserComplaints(user, req, resp);
    }
}
