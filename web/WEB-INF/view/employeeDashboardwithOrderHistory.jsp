<%@ page import="java.util.Objects" %>
<%@ page import="org.example.dto.ComplaintDto" %>
<%@ page import="java.util.List" %>
<%@ page import="org.example.dto.ComplaintDto" %><%--
  Created by IntelliJ IDEA.
  User: lahir
  Date: 6/17/2025
  Time: 9:23 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>LoginPage</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/employeeDashboard.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.7/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-LN+7fdVzj6u52u30Kp6M/trliBMCMKTyK833zpbD+pXdCLuTusPj697FH4R/5mcr" crossorigin="anonymous">
</head>
<body>
<%
    String message = (String) request.getAttribute("message");
    String role = (String) request.getAttribute("role");
    String uId = (String) request.getAttribute("uId");
    String name = (String) request.getAttribute("name");

    List<ComplaintDto> complaints = (List<ComplaintDto>) request.getAttribute("complaints");

%>
<section class="employee-section">
    <div class="left-side d-flex flex-column align-items-center justify-content-center">
        <h1>Welcome, <%= name%></h1>
        <form action="http://localhost:8080/Complain_Management_System_Web_exploded/complaint" method="get">
            <button type="button" class="btn mb-3 historyBtn"><img src="${pageContext.request.contextPath}/assets/icon/history.png" width="35px"></button>
        </form>
        <img src="${pageContext.request.contextPath}/assets/images/employeeDashboard.jpg" class="img-fluid" alt="vector image">
    </div>
    <div class="right-side d-flex align-items-center justify-content-center">
        <div class="complaintHistoryTable">
            <table class="table historyTable">
                <thead>
                <tr>
                    <th scope="col">ID</th>
                    <th scope="col">Name</th>
                    <th scope="col">Description</th>
                    <th scope="col">Complaint Date</th>
                    <th scope="col">Status</th>
                    <th scope="col">Resolved Date</th>
                    <th scope="col">Resolved Time</th>
                    <th scope="col"></th>
                    <th scope="col"></th>
                </tr>
                </thead>
                <tbody>
                <%
                    if (complaints != null) {
                        for (ComplaintDto c : complaints) {
                %>
                <tr>
                    <td><%= c.getCId() %></td>
                    <td><%= c.getName() %></td>
                    <td><%= c.getDescription() %></td>
                    <td><%= c.getComplainDate() %></td>
                    <td><%= c.getStatus() %></td>
                    <td><%= c.getResolvedDate() != null ? c.getResolvedDate() : "-" %></td>
                    <td><%= c.getResolvedTime() != null ? c.getResolvedTime() : "-" %></td>
                    <td><button class="btn btn-warning btn-sm">Edit</button></td>
                    <td><button class="btn btn-danger btn-sm">Delete</button></td>
                </tr>
                <%
                        }
                    }
                %>
                </tbody>
            </table>
        </div>
    </div>
</section>

<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.7/dist/js/bootstrap.bundle.min.js" integrity="sha384-ndDqU0Gzau9qJ1lfW4pNLlhNTkCfHzAVBReH9diLvGRem5+R9g2FzA8ZGN954O5Q" crossorigin="anonymous"></script>
<script>
    $(document).ready(function() {
        // Check if user is logged in
        var uid = localStorage.getItem('uId');
        var name = localStorage.getItem('name');
        if (!uid) {
            window.location.href = 'login.jsp';
        } else {
            alert("Login Successful");
        }
    });

    localStorage.setItem("uId", "<%= uId %>");
    localStorage.setItem("role", "<%= role %>");
    localStorage.setItem("name", "<%= name %>");

    const uid = localStorage.getItem("uId");
    if (uid) {
        document.getElementById("uId").value = uid;
        console.log("User ID set in form: " + uid);
    }

    $(".historyBtn").on("click", function() {

    });
</script>
</body>
</html>
