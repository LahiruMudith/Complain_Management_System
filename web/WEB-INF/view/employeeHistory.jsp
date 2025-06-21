<%@ page import="java.util.Objects" %>
<%@ page import="java.util.List" %>
<%@ page import="org.example.dto.Complaint" %>
<%@ page import="org.example.dto.UserDto" %><%--
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
    UserDto user = (UserDto) request.getAttribute("user");
    List<Complaint> complaints = (List<Complaint>) request.getAttribute("complaints");
    String name = user.getName();
    String role = user.getRole();
    String userId = user.getUId();
%>
<section>
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
                    for (Complaint c : complaints) {
                        if (c.getUserId().equals(userId)) {
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
                }
            %>
            </tbody>
        </table>
    </div>
</section>

<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.7/dist/js/bootstrap.bundle.min.js" integrity="sha384-ndDqU0Gzau9qJ1lfW4pNLlhNTkCfHzAVBReH9diLvGRem5+R9g2FzA8ZGN954O5Q" crossorigin="anonymous"></script>
<script>
    $(document).ready(function () {
        const userId = localStorage.getItem('userId');
        const username = localStorage.getItem('username');
        const role = localStorage.getItem('role');

        if (!userId || !username || !role) {
            alert("You must log in first!");
            window.location.href = "<%= request.getContextPath() %>/login";
        }else{
            <% user.setUId(userId);%>
            <% user.setName(name);%>
            <% user.setRole(role);%>
        }
    });


    // Get data from backend (Java)
    const username = "<%= name %>";
    const role = "<%= role %>";
    const userId = "<%= userId %>";

    // Store in browser's localStorage
    localStorage.setItem("username", username);
    localStorage.setItem("role", role);
    localStorage.setItem("userId", userId);

    // Optional: confirm in console
    console.log("Stored in browser:", username, role, userId);
    $("#uId").val(userId);
    $("#uName").val(username);
    $("#role").val(role);
</script>

</body>
</html>
