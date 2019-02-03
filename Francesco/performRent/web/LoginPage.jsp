<%@ page import="it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.bean.SessionBean" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>


<!-- Si dichiara la variabile loginBean e istanzia un oggetto LoginBean -->
<jsp:useBean id="sessionBean" scope="session"
             class="it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.bean.SessionBean"/>

<!-- Mappa automaticamente tutti gli attributi dell'oggetto loginBean e le proprietà JSP -->
<jsp:setProperty name="sessionBean" property="username"/>

<%
    if (request.getParameter("login") != null) {
        if (sessionBean.login()) {


    %>
            <!-- Passa il controllo alla nuova pagina -->
            <jsp:forward page="ContractRequests.jsp"/>
            <%
        } else {
            %>
            <p style="text-color:red;">Dati errati</p>
            <%
        }
    } else {
        %>
        <p class="text-info">Accesso non effettuato</p>
        <%
    }
%>

<!-- HTML 5 -->
<!DOCTYPE html>
<html>
<!-- Container tag for title, style, meta-information, linked resources or scripts -->
<head>
    <!-- Browser title bar, favorites, name for search engines -->
    <title>Login page</title>
    <!-- info about content, e.g.: content type, keywords, charset or description -->
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <!-- linked CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <script src="libs/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>

    <link href="css/style.css" rel="stylesheet" type="text/css">
</head>
<body class="nostroSito">
<div class="container">
    <form action="LoginPage.jsp" name="myform" method="POST">
        <form>
            <div class="form-group">
                <label for="username">Email address</label>
                <input id="username" name="username" type="text">
                <small id="emailHelp" class="form-text text-muted">We'll never share your email with anyone else.</small>
            </div>
            <div class="form-group">
                <label for="exampleInputPassword1">Password</label>
                <input type="password" class="form-control" id="exampleInputPassword1" placeholder="Password">
            </div>
            <div class="form-group form-check">
                <input type="checkbox" class="form-check-input" id="exampleCheck1">
                <label class="form-check-label" for="exampleCheck1">Check me out</label>
            </div>
            <button type="submit" id="login" name="login" value="login" class="btn btn-primary">Submit</button>
        </form>
    </form>
</div>
</body>

</html>