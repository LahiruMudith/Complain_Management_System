package org.example.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.dbcp2.BasicDataSource;
import org.example.dto.Complaint;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ComplanitModel {
    public String saveComplaint(Complaint complaint, HttpServletRequest req, HttpServletResponse resp) {
        ServletContext sc = req.getServletContext();
        BasicDataSource ds = (BasicDataSource) sc.getAttribute("ds");
        String uId = req.getParameter("uid");
        String name = req.getParameter("name");
        String role = req.getParameter("role");
        try {
            Connection connection = ds.getConnection();
            PreparedStatement pstm = connection.prepareStatement("INSERT INTO Complaint (description, name, user_id, complain_date, status, resolved_date, resolved_time) VALUES (?,?,?,?,?,?,?)");
            pstm.setString(1, complaint.getDescription());
            pstm.setString(2, complaint.getName());
            pstm.setString(3, complaint.getUserId());
            pstm.setString(4, complaint.getComplainDate());
            pstm.setString(5, complaint.getStatus()); // Default status
            pstm.setString(6, complaint.getResolvedDate());
            pstm.setString(7, complaint.getResolvedTime());

            int i = pstm.executeUpdate();
            resp.setContentType("text/html");
            connection.close();
            if (i > 0) {
                return "success";
            } else {
                return "fail";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "error";
        }
    }

    public List<Complaint> getAllComplaints(HttpServletRequest req, HttpServletResponse resp) {
        ServletContext context = req.getServletContext();
        BasicDataSource ds = (BasicDataSource) context.getAttribute("ds");
        resp.setContentType("application/json");

        try {
            Connection connection = ds.getConnection();
            PreparedStatement pstm = connection.prepareStatement("SELECT * FROM Complaint");
            ResultSet resultSet = pstm.executeQuery();

            List<Complaint> complaints = new ArrayList<>();
            while (resultSet.next()) {
                Complaint complaint = new Complaint(
                        resultSet.getString("CID"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getString("user_id"),
                        resultSet.getString("complain_date"),
                        resultSet.getString("status"),
                        resultSet.getString("resolved_date"),
                        resultSet.getString("resolved_time")
                );
                complaints.add(complaint);
            }

            if (!complaints.isEmpty()) {
                return complaints;
            }
        } catch (SQLException e) {

            throw new RuntimeException(e);
        }
        return null;
    }

    public String deleteComplaint(String cid, HttpServletRequest req, HttpServletResponse resp) {
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
                return "success";
            } else {
                return "fail";
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return "error";
        }
    }
    public String updateComplain(HttpServletRequest req, HttpServletResponse resp) {
        try {

            String cid = req.getParameter("id");
            String name = req.getParameter("name");
            String description = req.getParameter("description");
            String status = req.getParameter("status");
            String resolvedDate = req.getParameter("complainDate"); // format: YYYY-MM-DD
            String resolvedTime = LocalTime.now().toString(); // format: HH:MM:SS

            System.out.println("Updating complaint with ID: " + cid);

            ServletContext servletContext = req.getServletContext();
            BasicDataSource ds = (BasicDataSource) servletContext.getAttribute("ds");

            Connection connection = ds.getConnection();
            PreparedStatement pstm = connection.prepareStatement(
                    "UPDATE complaint SET name = ?, description = ?, status = ?, resolved_date = ?, resolved_time = ? WHERE CID = ?"
            );
            System.out.println("PreparedStatement created for updating complaint.");

            pstm.setString(1, name);
            pstm.setString(2, description);
            pstm.setString(3, status);
            pstm.setString(4, resolvedDate); // format: YYYY-MM-DD
            pstm.setString(5, resolvedTime); // format: HH:MM:SS
            pstm.setString(6, cid);

            System.out.println("Parameters set for updating complaint: " + name + ", " + description + ", " + status + ", " + resolvedDate + ", " + resolvedTime + ", CID: " + cid);

            int i = pstm.executeUpdate();
            resp.setContentType("application/json");
            System.out.println(i);

            if (i > 0) {
                System.out.println("Update successful.");
                return "success";
            } else {
                System.out.println("Update failed, no rows affected.");
                return "fail";
            }
        } catch (SQLException e) {
            return "error";
        }
    }
}
