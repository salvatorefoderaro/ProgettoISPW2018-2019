<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import= "Control.controller" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="Control.controller" %>
<%@ page import="Exceptions.emptyResult" %>
<%@ page import="Bean.userBean" %>
<%@ page import="java.util.Timer" %>
<%@ page import="java.util.TimerTask" %>
<%@ page import="Entity.TypeOfMessage" %>

<jsp:useBean id="sessionBean" scope="session" class="Bean.userBean"/>

<%
    if (request.getParameter("login") != null) {

        userBean login = new userBean();
        login.setNickname(request.getParameter("nickname"));
        login.setPassword(request.getParameter("password"));
        controller parentController = null;

        userBean loggedUser = null;
        try {
            parentController = new controller();
            loggedUser = parentController.loginRenter(login);
            sessionBean.setID(loggedUser.getID());
            sessionBean.setNickname(loggedUser.getNickname());
            sessionBean.setCF(loggedUser.getCF());
            sessionBean.setController(parentController);
        } catch (SQLException e) {
            String destination ="index.jsp";
            response.sendRedirect(response.encodeRedirectURL(destination));
            session.setAttribute("warningMessage", TypeOfMessage.DBERROR.getString());
            return;
        } catch (Exceptions.emptyResult emptyResult) {
            String destination ="index.jsp";
            response.sendRedirect(response.encodeRedirectURL(destination));
            session.setAttribute("infoMessage", "Controllare nome utente e/o password, nessun utente associato!");
            return;
        } catch (Exceptions.dbConfigMissing dbConfigMissing) {
            session.setAttribute("warningMessage", TypeOfMessage.DBCONFIGERROR.getString());
            String destination ="index.jsp";
            response.sendRedirect(response.encodeRedirectURL(destination));
            return;
        }

        %>
        <jsp:forward page="seeRentable.jsp"/>

<%
return; } %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
  <head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        
    <title>Hello, world!</title>
             
  </head>
  
    
    <body>



    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <a class="navbar-brand" href="#">FERSA</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
            <div class="navbar-nav">
                <a class="nav-item nav-link active" href="#"> Login <span class="sr-only">(current)</span></a>
                <a class="nav-item nav-link disabled" href="seeRentable.jsp">Pannello utente</a>
                <a class="nav-item nav-link disabled" href="importContract.jsp">Importa contratto </a> </div>
        </div>
    </nav>

    	  <br>
<div class="container" style="">
    <center>
<%

    // Error handling

    if (session.getAttribute("successMessage") != null) { %>

        <div class="alert alert-warning">
            <strong>Ok!</strong> <%= session.getAttribute("successMessage") %>
        </div>


        <% session.setAttribute("successMessage", null);
        }

            if (session.getAttribute("infoMessage") != null) {  %>


        <div class="alert alert-warning">
            <strong>Attenzione!</strong> <%= session.getAttribute("infoMessage") %>
        </div>


        <% session.setAttribute("infoMessage", null);
        }

            if (session.getAttribute("warningMessage") != null) {  %>


        <div class="alert alert-warning">
            <strong>Errore!</strong> <%= session.getAttribute("warningMessage") %>
        </div>


        <% session.setAttribute("warningMessage", null);
        } %>

     <form action="index.jsp" method="POST">
         <div class="input-group mb-3">

                  <div class="input-group mb-3">
  <div class="input-group-prepend">
    <span class="input-group-text" id="inputGroup-sizing-default">Nome utente e Password</span>
  </div>
  <input type="text" name="nickname" class="form-control" aria-label="Default" aria-describedby="inputGroup-sizing-default" required>
   <input type="password" name="password" class="form-control" aria-label="Default" aria-describedby="inputGroup-sizing-default" required>

                  </div>
         <button type="sumbit" name="login" class="btn btn-primary btn-lg">Effettua il Login</button>
     </form></center>
</div>      

      
      <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->


        <script type='text/javascript' src='${pageContext.request.contextPath}/Resource/jquery-1.8.3.min.js'></script>
        <link rel='stylesheet' href='${pageContext.request.contextPath}/Resource/bootstrap.min.css'>
        <link rel='stylesheet' href='${pageContext.request.contextPath}/Resource/bootstrap-datepicker3.min.css'>
        <script type='text/javascript' src='${pageContext.request.contextPath}/Resource/bootstrap-datepicker.min.js'></script>
    

</body>
</html>