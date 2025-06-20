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

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@WebServlet("/complaint")
public class ComplaintServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext sc = req.getServletContext();
        BasicDataSource ds = (BasicDataSource) sc.getAttribute("ds");
        String uId = req.getParameter("uid");
        String name = req.getParameter("name");
        String role = req.getParameter("role");
        try {
            Connection connection = ds.getConnection();
            PreparedStatement pstm = connection.prepareStatement("INSERT INTO Complaint (description, name, user_id, complain_date, status, resolved_date, resolved_time) VALUES (?,?,?,?,?,?,?)");
            pstm.setString(1, req.getParameter("description"));
            pstm.setString(2, req.getParameter("name"));
            pstm.setString(3, req.getParameter("uid"));
            pstm.setString(4, String.valueOf(LocalDate.now()));
            pstm.setString(5, "pending"); // Default status
            pstm.setString(6, LocalDate.now().toString());
            pstm.setString(7, LocalTime.now().toString());

            int i = pstm.executeUpdate();
            resp.setContentType("text/html");
            System.out.println(i);
            if (i > 0) {
                req.setAttribute("uId", uId);
                req.setAttribute("name", name);
                req.setAttribute("role", role);

                resp.setHeader("message", "ComplaintDto registered successfully");
                RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/view/employeeDashboard.jsp");
                dispatcher.forward(req, resp);
            } else {
                resp.setHeader("message", "ComplaintDto registered fail");
                RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/view/employeeDashboard.jsp");
                dispatcher.forward(req, resp);
            }
        } catch (SQLException e) {
            resp.setHeader("message", "Internal Server Error");
            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/view/employeeDashboard.jsp");
            dispatcher.forward(req, resp);
            throw new RuntimeException(e);
        }
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext context = req.getServletContext();
        BasicDataSource ds = (BasicDataSource) context.getAttribute("ds");
        resp.setContentType("application/json");
        ObjectMapper mapper = new ObjectMapper();
        PrintWriter out = resp.getWriter();

        try {
            Connection connection = ds.getConnection();
            PreparedStatement pstm = connection.prepareStatement("SELECT * FROM Complaint");
            ResultSet resultSet = pstm.executeQuery();

            List<Map<String, String>> complaints = new ArrayList<>();
            while (resultSet.next()) {
                Map<String, String> complaint = Map.of(
                        "cid", resultSet.getString("CID"),
                        "name", resultSet.getString("name"),
                        "description", resultSet.getString("description"),
                        "user_id", resultSet.getString("user_id"),
                        "complain_date", resultSet.getString("complain_date"),
                        "status", resultSet.getString("status"),
                        "resolved_date", resultSet.getString("resolved_date"),
                        "resolved_time", resultSet.getString("resolved_time")
                );
                complaints.add(complaint);
            }

            if (complaints.isEmpty()) {
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
                mapper.writeValue(out, Map.of(
                        "code", "204",
                        "status", "No Content",
                        "message", "No complaints found"
                ));
            } else {
                resp.setStatus(HttpServletResponse.SC_OK);
                mapper.writeValue(out, Map.of(
                        "code", "200",
                        "status", "success",
                        "data", complaints
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
