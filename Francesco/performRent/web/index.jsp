<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>


<jsp:useBean id="loginBean" scope="request"
              class="it.uniroma2.ispw.fersa.rentingManagement.bean.LoginBean"/>

<jsp:useBean id="sessionBean" scope="session" class="it.uniroma2.ispw.fersa.rentingManagement.bean.SessionBean"></jsp:useBean>

<jsp:setProperty name="loginBean" property="*"></jsp:setProperty>


<html>
<head>

</head>
<body>
<center>
    <%
        if (request.getParameter("login") != null) {
            if (loginBean.login()) {
                sessionBean.setUsername(loginBean.getNickname());
                sessionBean.setSessionState(true);
    %>
        <jsp:forward page="RenterPage.jsp"></jsp:forward>
    <%
    } else { %>
    <div class="alert alert-primary" role="alert">

        <%= "Dati errati"%>

    </div>
    <%
            }
        }
    %>
    <div class="container" >
        <% if (session.getAttribute("warningMessage") != null) { %>
        <div class="alert alert-primary" role="alert">

            <%= session.getAttribute("warningMessage")%>

        </div>
        <%
        }
        %>
        <form action="index.jsp" name="loginForm" method="post">
            <div class="form-group">
                <label for="nickname">Nickname</label>
                <input type="text" class="form-control" id="nickname" name="nickname" placeholder="Enter nickname">
            </div>
            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" class="form-control" id="password" name="password" placeholder="Password">
            </div>
            <button type="submit" id="login" name="login" class="btn btn-primary">Submit</button>
        </form>
    </div>
</center>


<script type='text/javascript' src='resource/jquery-1.8.3.min.js'></script>
<link rel='stylesheet' href='resource/bootstrap.min.css'>
<link rel='stylesheet' href='resource/bootstrap-datepicker3.min.css'>
<script type='text/javascript' src='resource/bootstrap-datepicker.min.js'></script>
</body>
</html>