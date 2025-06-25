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

<section class="employee-section" style="height: 95vh">
    <div class="left-side d-flex flex-column align-items-center justify-content-center">
        <h1>Welcome, <%= name%></h1>
        <form action="http://localhost:8080/Complain_Management_System_Web_exploded/controller/complaint">
            <button type="submit" class="btn mb-3 text-white">
                <img src="<%= request.getContextPath() %>/assets/icon/history.png" width="30px">
            </button>
            <input type="text" class="form-control is-valid" name="name" value="<%= name%>" hidden>
            <input type="text" class="form-control is-valid" name="role" value="<%= role%>" hidden>
            <input type="text" class="form-control is-valid" name="userId" value="<%= userId%>" hidden>
        </form>
        <img src="${pageContext.request.contextPath}/assets/images/employeeDashboard.jpg" class="img-fluid" alt="vector image">
    </div>
    <div class="right-side d-flex align-items-center justify-content-center">
        <div class="card shadow complaintCard">
            <div class="card-body">
                <form class="was-validated complaintForm" action="http://localhost:8080/Complain_Management_System_Web_exploded/controller/complaint" method="post">
                    <div class="col-md-12">
                        <label for="name" class="form-label">Compliant Name</label>
                        <input type="text" class="form-control is-valid" id="name" name="name" required>
                        <input type="text" class="form-control is-valid" id="uId" name="uid" hidden>
                        <input type="text" class="form-control is-valid" id="method" name="method" hidden>
                        <input type="text" class="form-control is-valid" id="uName" name="uName" hidden>
                        <input type="text" class="form-control is-valid" id="role" name="role" hidden>
                        <div class="valid-feedback">
                            Looks good!
                        </div>
                    </div>
                    <div class="mb-3 h-50">
                        <label for="description" class="form-label">Description</label>
                        <textarea class="form-control h-75" id="description" name="description" required></textarea>
                        <div class="invalid-feedback">
                            Please enter a message in the description.
                        </div>
                    </div>
                    <div class="col-12 d-flex justify-content-center">
                        <div>
                            <button type="submit" class="btn mb-3 text-white" style="background-color: #6f42c1;">
                                Send
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</section>
<form>

    <button type="button" class="btn btn-danger ms-2">Logout</button>
</form>



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
    $("#method").val("post");
</script>

</body>
</html>
