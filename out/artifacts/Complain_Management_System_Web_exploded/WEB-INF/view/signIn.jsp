<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>LoginPage</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/signIn.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.7/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-LN+7fdVzj6u52u30Kp6M/trliBMCMKTyK833zpbD+pXdCLuTusPj697FH4R/5mcr" crossorigin="anonymous">
</head>
<body>
<%
    String message = (String) request.getAttribute("message");
    if (message != null) {
%>
<script>
    alert("<%= message %>");
    console.log("<%= message %>")
</script>
<%
    }
%>


<section id="login-sec">
    <div class="left-side">
        <form class="row g-3 signInForm card" action="http://localhost:8080/Complain_Management_System_Web_exploded/user" method="POST" enctype="multipart/form-data">
            <div class="row g-3">
                <div class="col">
                    <input type="text" class="form-control" placeholder="Name" aria-label="Name" id="name" name="name" required>
                    <input type="text" class="form-control" placeholder="user Id" aria-label="user Id" id="uId" name="uid" hidden>
                </div>
            </div>
            <div class="row g-3">
                <div class="col">
                    <input type="text" class="form-control" id="address" placeholder="Address" aria-label="Address" name="address" required>
                </div>
            </div>
            <div class="row g-3">
                <div class="col">
                    <input type="text" class="form-control" placeholder="NIC Number" aria-label="NIC Number" id="nic" name="nic" required>
                </div>
            </div>
            <div class="row g-3">
                <div class="col">
                    <input type="text" class="form-control" placeholder="Phone Number" aria-label="Phone Number" id="phone" name="phone" required>
                </div>
            </div>
            <div class="row g-3">
                <div class="col">
                    <input type="text" class="form-control" placeholder="Email" aria-label="Email" id="email" name="email" required>
                </div>
            </div>
            <div class="row g-3">
                <div class="col">
                    <input type="text" class="form-control" placeholder="Password" aria-label="Password" id="password" name="password" required>
                </div>
            </div>
            <div class="row g-3">
                <div class="col">
                    <select id="role" class="form-select" name="role" required>
                        <option selected>Employee</option>
                        <option>Admin</option>
                    </select>
                </div>
            </div>
            <div class="col-12 d-flex justify-content-center">
                <button type="submit" class="btn btn-primary mb-3">Sign in</button>
            </div>
        </form>
    </div>
    <div class="right-side">
        <img src="${pageContext.request.contextPath}/assets/images/loginImage.jpg" class="img-fluid" alt="...">
    </div>
</section>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.7/dist/js/bootstrap.bundle.min.js" integrity="sha384-ndDqU0Gzau9qJ1lfW4pNLlhNTkCfHzAVBReH9diLvGRem5+R9g2FzA8ZGN954O5Q" crossorigin="anonymous"></script>
</body>
</html>
