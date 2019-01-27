<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import= "Control.controller" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="Control.controller" %>
<%@ page import="Bean.renterBean" %>
<%@ page import="Exceptions.emptyResult" %>

<jsp:useBean id="sessionBean" scope="session" class="Bean.renterBean"/>

<% 
    
    if (request.getParameter("login") != null) {

        renterBean login = new renterBean();
        login.setNickname(request.getParameter("nickname"));
        login.setPassword(request.getParameter("password"));
        controller parentController = null;

        renterBean loggedUser = null;
        try {
            parentController = new controller();
            loggedUser = parentController.loginLocatore(login);
            sessionBean.setID(loggedUser.getID());
            sessionBean.setNickname(loggedUser.getNickname());
            sessionBean.setCF(loggedUser.getCF());
            sessionBean.setController(parentController);
        } catch (SQLException e) {

        %>
        <jsp:forward page="LoginPage.jsp">
            <jsp:param name="error" value="databaseConnection" />
        </jsp:forward> <%

        } catch (Exceptions.emptyResult emptyResult) {

            session.setAttribute("emptyResult", "");

        }


        %>
        <jsp:forward page="seeRentable.jsp"/>

<%
} %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
  <head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">


    
        <script type='text/javascript' src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.5.0/js/bootstrap-datepicker.min.js"></script>

        
    <title>Hello, world!</title>
             
  </head>
  
    
    <body>
      
      

        <nav class="navbar navbar-expand-lg navbar-light bg-light">
  <a class="navbar-brand" href="#">Navbar</a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>
  <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
    <div class="navbar-nav">
      <a class="nav-item nav-link active" href="#">Home <span class="sr-only">(current)</span></a>
      <a class="nav-item nav-link" href="#">Features</a>
      <a class="nav-item nav-link" href="#">Pricing</a>
      <a class="nav-item nav-link disabled" href="#" tabindex="-1" aria-disabled="true">Disabled</a>
    </div>
  </div>
</nav>

    	  <br>
<div class="container">

<%

    if (session.getAttribute("emptyResult") != null) { %>


    <div class="alert alert-warning">
        <strong>Errore!</strong> Nickname e/o password errati.
    </div>


    <% session.setAttribute("emptyResult", null);
    }


        if (request.getParameter("error") != null && request.getParameter("error").equals("emptyResultRentable")) { %>


    <div class="alert alert-warning">
        <strong>Errore!</strong> Nessun risorsa al momento affitabile.
    </div>

    <% }
    if (request.getParameter("error") != null && request.getParameter("error").equals("makeLogin")) { %>


    <div class="alert alert-warning">
        <strong>Errore!</strong> Efettua l'accesso prima di continuare.
    </div>

    <% }


        if (request.getParameter("error") != null && request.getParameter("error").equals("databaseConnection")) {  %>


    <div class="alert alert-warning">
        <strong>Errore!</strong> Errore nella connessione con il Database!
    </div>

        <% } %>

     <form action="LoginPage.jsp" method="POST">
         <div class="input-group mb-3">

                  <div class="input-group mb-3">
  <div class="input-group-prepend">
    <span class="input-group-text" id="inputGroup-sizing-default">Nome utente e Password</span>
  </div>
  <input type="text" name="nickname" class="form-control" aria-label="Default" aria-describedby="inputGroup-sizing-default" required>
   <input type="password" name="password" class="form-control" aria-label="Default" aria-describedby="inputGroup-sizing-default" required>

                  </div>
         <button type="sumbit" name="login" class="btn btn-primary btn-lg">Login</button>
     </form>
</div>      

      
      <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->


        <script type='text/javascript' src='${pageContext.request.contextPath}/src/jquery-1.8.3.min.js'></script>
        <link rel='stylesheet' href='${pageContext.request.contextPath}/src/bootstrap.min.css'>
        <link rel='stylesheet' href='${pageContext.request.contextPath}/src/bootstrap-datepicker3.min.css'>
        <script type='text/javascript' src='${pageContext.request.contextPath}/src/bootstrap-datepicker.min.js'></script>
    

</body>
</html>