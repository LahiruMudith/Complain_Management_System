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
<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
            <div class="modal-content">
                <form action="http://localhost:8080/Complain_Management_System_Web_exploded/controller/complaint" method="post">
                    <div class="modal-header">
                        <h1 class="modal-title fs-5" id="exampleModalLabel">Update Menu</h1>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <div class="m-3">
                            <label for="id" class="form-label">Id</label>
                            <input type="text" class="form-control" id="id" name="id" readonly>
                            <input type="text" class="form-control" name="userId" value="<%= userId%>" hidden>
                            <input type="text" class="form-control"  name="method" value="update" hidden>
                            <input type="text" class="form-control" name="userName" value="<%= name%>" hidden>
                            <input type="text" class="form-control" name="userRole" value="<%= role%>" hidden>
                        </div>
                        <div class="m-3">
                            <label for="name" class="form-label">Name</label>
                            <input type="text" class="form-control" id="name" name="name">
                        </div>
                        <div class="m-3">
                            <label for="description" class="form-label">Description</label>
                            <input type="text" class="form-control" id="description" name="description">
                        </div>
                        <div class="m-3">
                            <label for="complainDate" class="form-label">Complain Date</label>
                            <input type="text" class="form-control" id="complainDate" name="complainDate" readonly>
                        </div>
                        <div class="m-3">
                            <label for="status" class="form-label">Status</label>
                            <input type="text" class="form-control" id="status" name="status" readonly>
                        </div>
                        <div class="m-3">
                            <label for="resolvedDate" class="form-label">Resolved Date</label>
                            <input type="text" class="form-control" id="resolvedDate" name="resolvedDate" readonly>
                        </div>
                        <div class="m-3">
                            <label for="resolvedTime" class="form-label">Resolved Time</label>
                            <input type="text" class="form-control" id="resolvedTime" name="resolvedTime" readonly>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="submit" class="btn btn-warning">Update Complain</button>
                    </div>
                </form>
            </div>
    </div>
</div>
<section>
    <form action="http://localhost:8080/Complain_Management_System_Web_exploded/controller/complaint" method="post">
        <input type="text" class="form-control" value="<%= userId%>" name="userId" hidden>
        <input type="text" class="form-control" value="goEmployeeDashboard" name="method" hidden>
        <input type="text" class="form-control" value="<%= name%>" name="name" hidden>
        <input type="text" class="form-control" value="<%= role%>" name="role" hidden>
        <button type="submit" class="btn btn-secondary m-1"> < Back</button>
    </form>
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
                <td>
                    <button
                            type="button"
                            class="btn btn-warning btn-sm editBtn"
                            data-cid="<%= c.getCId() %>"
                            data-name="<%= c.getName() %>"
                            data-description="<%= c.getDescription() %>"
                            data-complaindate="<%= c.getComplainDate() %>"
                            data-status="<%= c.getStatus() %>"
                            data-resolveddate="<%= c.getResolvedDate() != null ? c.getResolvedDate() : "" %>"
                            data-resolvedtime="<%= c.getResolvedTime() != null ? c.getResolvedTime() : "" %>"
                            data-bs-toggle="modal"
                            data-bs-target="#exampleModal">
                        Edit
                    </button>
                </td>

                <td>
                    <form action="http://localhost:8080/Complain_Management_System_Web_exploded/controller/complaint" method="POST" style="display:inline;">
                        <input type="hidden" name="cId" value="<%= c.getCId() %>"/>
                        <input type="hidden" name="name" value="<%= name %>"/>
                        <input type="hidden" name="uid" value="<%= userId %>"/>
                        <input type="hidden" name="role" value="<%= role %>"/>
                        <input type="hidden" name="method" value="delete"/>
                        <button type="submit" class="btn btn-danger btn-sm" onsubmit="return confirm('Are you sure you want to delete this complaint?');">
                            Delete
                        </button>
                    </form>
                </td>
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

    $(document).on("click", ".editBtn", function () {
        const btn = $(this);
        const cId = btn.data("cid");
        const name = btn.data("name");
        const description = btn.data("description");
        const complainDate = btn.data("complaindate");
        const status = btn.data("status");
        const resolvedDate = btn.data("resolveddate");
        const resolvedTime = btn.data("resolvedtime");

        // Populate modal form fields
        $("#id").val(cId);
        $("#name").val(name);
        $("#description").val(description);
        $("#complainDate").val(complainDate);
        $("#status").val(status);
        $("#resolvedDate").val(resolvedDate || "-");
        $("#resolvedTime").val(resolvedTime || "-");
    });

</script>

</body>
</html>
