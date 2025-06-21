package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.dbcp2.BasicDataSource;
import org.example.dto.Complaint;
import org.example.dto.UserDto;
import org.example.model.ComplanitModel;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@WebServlet("/controller/complaint")
public class ComplaintServlet extends HttpServlet {
    ComplanitModel complanitModel = new ComplanitModel();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Complaint complaint = new Complaint(
                "01",//fake id
                req.getParameter("name"),
                req.getParameter("description"),
                req.getParameter("uid"),
                String.valueOf(LocalDate.now()),
                "pending", // Default status
                LocalDate.now().toString(), // format: YYYY-MM-DD
                LocalTime.now().toString()
        );
        String result = complanitModel.saveComplaint(complaint, req, resp);

        UserDto user = new UserDto();
        user.setUId(req.getParameter("uid"));
        user.setName(req.getParameter("uName"));
        user.setRole(req.getParameter("role"));

        if (result.equals("success")) {
            req.setAttribute("user", user);
            System.out.println(user.getName() + " has successfully submitted a complaint." + user.getUId());
            req.getRequestDispatcher("/WEB-INF/view/employeeDashboard.jsp").forward(req, resp);
        }
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Complaint> allComplaints = complanitModel.getAllComplaints(req, resp);
        System.out.println("All Complaints: " + allComplaints);
        System.out.println(req.getParameter("name"));
    }
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String,String> user = mapper.readValue(req.getInputStream(), Map.class);

            String cid = user.get("cid");
            String name = user.get("name");
            String description = user.get("description");
            String status = user.get("status");
            String resolvedDate = user.get("resolved_date");
            String resolvedTime = user.get("resolved_time");

            ServletContext servletContext = req.getServletContext();
            BasicDataSource ds = (BasicDataSource) servletContext.getAttribute("ds");

            Connection connection = ds.getConnection();
            PreparedStatement pstm = connection.prepareStatement(
                    "UPDATE complaint SET name = ?, description = ?, status = ?, resolved_date = ?, resolved_time = ? WHERE CID = ?"
            );

            pstm.setString(1, name);
            pstm.setString(2, description);
            pstm.setString(3, status);
            pstm.setString(4, resolvedDate); // format: YYYY-MM-DD
            pstm.setString(5, resolvedTime); // format: HH:MM:SS
            pstm.setString(6, cid);

            int i = pstm.executeUpdate();
            resp.setContentType("application/json");
            System.out.println(i);

            if (i > 0) {
                resp.setStatus(HttpServletResponse.SC_OK);
                mapper.writeValue(resp.getWriter(), Map.of(
                        "code", "200",
                        "status", "success",
                        "message", "ComplaintDto updated successfully"
                ));
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                mapper.writeValue(resp.getWriter(), Map.of(
                        "code", "400",
                        "status", "error",
                        "message", "ComplaintDto update failed"
                ));
            }
        } catch (SQLException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(resp.getWriter(), Map.of(
                    "code", "500",
                    "status", "error",
                    "message", "Internal server error"
            ));
            throw new RuntimeException(e);
        }
    }
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cid = req.getParameter("cid"); // e.g., /api/v1/complaint?cid=CMP001
        ServletContext servletContext = req.getServletContext();
        BasicDataSource ds = (BasicDataSource) servletContext.getAttribute("ds");
        ObjectMapper mapper = new ObjectMapper();
        resp.setContentType("application/json");

        try {
            Connection connection = ds.getConnection();
            PreparedStatement pstm = connection.prepareStatement(
                    "DELETE FROM Complaint WHERE CID = ?"
            );
            pstm.setString(1, cid);

            int i = pstm.executeUpdate();

            if (i > 0) {
                resp.setStatus(HttpServletResponse.SC_OK);
                mapper.writeValue(resp.getWriter(), Map.of(
                        "code", "200",
                        "status", "success",
                        "message", "ComplaintDto deleted successfully"
                ));
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                mapper.writeValue(resp.getWriter(), Map.of(
                        "code", "404",
                        "status", "error",
                        "message", "ComplaintDto not found"
                ));
            }

        } catch (SQLException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            mapper.writeValue(resp.getWriter(), Map.of(
                    "code", "500",
                    "status", "error",
                    "message", "Internal Server Error"
            ));
            throw new RuntimeException(e);
        }
    }

}
