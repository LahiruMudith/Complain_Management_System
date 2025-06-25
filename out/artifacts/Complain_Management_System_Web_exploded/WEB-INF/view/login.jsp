<%--
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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.7/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-LN+7fdVzj6u52u30Kp6M/trliBMCMKTyK833zpbD+pXdCLuTusPj697FH4R/5mcr" crossorigin="anonymous">
</head>
<body>
<%
    String error = (String) request.getAttribute("error");
    if (error != null) {
%>
<script>
    alert("<%= error %>");
</script>
<%
    }
%>

<section id="login-sec">
    <div class="left-side">
        <form class="d-flex flex-column justify-content-center card" action="http://localhost:8080/Complain_Management_System_Web_exploded/controller/login" method="POST">
            <div class="m-3">
                <label for="loginEmail" class="form-label">Email</label>
                <input type="email" class="form-control" id="loginEmail" aria-describedby="emailHelp" name="email">
            </div>
            <div class="m-3">
                <label for="loginPassword" class="form-label">Password</label>
                <input type="password" class="form-control" id="loginPassword" name="password">
            </div>
            <button type="submit" class="btn btn-primary m-3">Login</button>
            <lable>You Don'tHave An Account ? <a href="http://localhost:8080/Complain_Management_System_Web_exploded/signUp">Sign Up</a></lable>
        </form>
    </div>
    <div class="right-side">
        <img src="${pageContext.request.contextPath}/assets/images/loginImage.jpg" class="img-fluid" alt="...">
    </div>
</section>

<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.7/dist/js/bootstrap.bundle.min.js" integrity="sha384-ndDqU0Gzau9qJ1lfW4pNLlhNTkCfHzAVBReH9diLvGRem5+R9g2FzA8ZGN954O5Q" crossorigin="anonymous"></script>
<script>
    <%--$(document).ready(function() {--%>
    <%--    // Check if user is logged in--%>
    <%--    var userId = localStorage.getItem('userId');--%>
    <%--    if (userId) {--%>
    <%--        window.location.href = "<%= request.getContextPath() %>/employee";--%>
    <%--    }--%>
    <%--});--%>
</script>
</body>
</html>
