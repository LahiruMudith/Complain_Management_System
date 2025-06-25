package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@WebServlet("/controller/complaint")
public class ComplaintServlet extends HttpServlet {
    ComplanitModel complanitModel = new ComplanitModel();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(req.getParameter("method"));
        if (req.getParameter("method").equals("post")){
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
        }else if (req.getParameter("method").equals("delete")){
            String cId = req.getParameter("cId");
            String result = complanitModel.deleteComplaint(cId, req, resp);

            List<Complaint> allComplaints = complanitModel.getAllComplaints(req, resp);
            for (Complaint complaint : allComplaints) {
                System.out.println(complaint.getComplainDate());
                System.out.println(complaint.getResolvedDate());
                if (complaint.getComplainDate().equals(complaint.getResolvedDate())){
                    System.out.println("Complaint is resolved on the same day.");
                    complaint.setResolvedDate("Not Resolved Yet");
                    complaint.setResolvedTime("Not Resolved Yet");
                }
            }

            UserDto user = new UserDto();
            user.setUId(req.getParameter("uid"));
            user.setName(req.getParameter("name"));
            user.setRole(req.getParameter("role"));

            if (result.equals("success")) {
                req.setAttribute("complaints", allComplaints);
                req.setAttribute("user", user);
                req.getRequestDispatcher("/WEB-INF/view/employeeHistory.jsp").forward(req, resp);
            }
        } else if (req.getParameter("method").equals("update")) {
            System.out.println("Update method called");
            String result = complanitModel.updateComplain(req, resp);
            List<Complaint> allComplaints = complanitModel.getAllComplaints(req, resp);
            for (Complaint complaint : allComplaints) {
                System.out.println(complaint.getComplainDate());
                System.out.println(complaint.getResolvedDate());
                if (complaint.getComplainDate().equals(complaint.getResolvedDate())){
                    System.out.println("Complaint is resolved on the same day.");
                    complaint.setResolvedDate("Not Resolved Yet");
                    complaint.setResolvedTime("Not Resolved Yet");
                }
            }

            req.setAttribute("userId", req.getParameter("uid"));
            req.setAttribute("name", req.getParameter("uName"));
            req.setAttribute("role", req.getParameter("role"));

            UserDto user = new UserDto();
            user.setUId(req.getParameter("userId"));
            user.setName(req.getParameter("userName"));
            user.setRole(req.getParameter("userRole"));

            if (result.equals("success")) {
                req.setAttribute("complaints", allComplaints);
                req.setAttribute("user", user);
                req.getRequestDispatcher("/WEB-INF/view/employeeHistory.jsp").forward(req, resp);
            }
        } else if (req.getParameter("method").equals("goEmployeeDashboard")) {
            UserDto user = new UserDto();
            user.setUId(req.getParameter("userId"));
            user.setName(req.getParameter("name"));
            user.setRole(req.getParameter("role"));

            req.setAttribute("user", user);
            req.getRequestDispatcher("/WEB-INF/view/employeeDashboard.jsp").forward(req, resp);
        }

    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Complaint> allComplaints = complanitModel.getAllComplaints(req, resp);
        for (Complaint complaint : allComplaints) {
            System.out.println(complaint.getComplainDate());
            System.out.println(complaint.getResolvedDate());
            if (complaint.getComplainDate().equals(complaint.getResolvedDate())){
                System.out.println("Complaint is resolved on the same day.");
                complaint.setResolvedDate("Not Resolved Yet");
                complaint.setResolvedTime("Not Resolved Yet");
            }
        }

        UserDto user = new UserDto();
        user.setUId(req.getParameter("userId"));
        user.setName(req.getParameter("name"));
        user.setRole(req.getParameter("role"));

        if (req.getParameter("role").equals("Employee")) {
            req.setAttribute("complaints", allComplaints);
            req.setAttribute("user", user);
            req.getRequestDispatcher("/WEB-INF/view/employeeHistory.jsp").forward(req, resp);
        }

    }

}
