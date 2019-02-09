<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import= "Controller.Controller, Bean.userSessionBean" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="Entity.TypeOfUser" %>
<%@ page import="Controller.sampleThread" %>
<%@ page import="java.util.Timer" %>
<%@ page import="java.util.TimerTask" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="Entity.TypeOfMessage" %>
<%@ page import="Entity.TitleOfWindows" %>

<jsp:useBean id="sessionBean" scope="session" class="Bean.userSessionBean"/>

<%

    // java.util.concurrent.TimeUnit.SECONDS.sleep(5);
    // sampleThread.startTask(request.getContextPath());


    if (request.getParameter("login") != null) {
        userSessionBean login = new userSessionBean(request.getParameter("nickname"), 1, TypeOfUser.NOTLOGGED, 0, request.getParameter("password"), null);
        try {
            Controller controller = new Controller();
            login = controller.login(login);
            sessionBean.setId(login.getId());
            sessionBean.setController(controller);
            sessionBean.setNickname(login.getNickname());
            sessionBean.setUserType(login.getUserType());
            System.out.println("Tipo di utente è " + login.getUserType());
        %>
            <jsp:forward page="pannelloUtente.jsp"/>
        <%
            return;
        } catch (Exceptions.emptyResult emptyResult) {
            session.setAttribute("infoMessage", "Nessun utente associato!");
            String destination ="index.jsp";
            response.sendRedirect(response.encodeRedirectURL(destination));
            return;
        } catch (SQLException e) {
            e.printStackTrace();
            session.setAttribute("warningMessage", TypeOfMessage.DBERROR.getString());
            String destination ="index.jsp";
            response.sendRedirect(response.encodeRedirectURL(destination));
            return;
        } catch (Exceptions.dbConfigMissing missingConfig) {
            missingConfig.printStackTrace();
            session.setAttribute("warningMessage", TypeOfMessage.DBCONFIGERROR.getString());
            String destination ="index.jsp";
            response.sendRedirect(response.encodeRedirectURL(destination));
            return;
        }
}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
  <head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css" integrity="sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.4.1/css/bootstrap-datepicker3.css"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.5.0/css/bootstrap-datepicker3.min.css">
    
        <script userType='text/javascript' src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.5.0/js/bootstrap-datepicker.min.js"></script>

    <title><%= TitleOfWindows.LOGIN.getString() %></title>
             
  </head>
    
    <body>

    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <a class="navbar-brand" href="#">FERSA</a>
        <button class="navbar-toggler" userType="button" data-toggle="collapse" data-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
            <div class="navbar-nav">
                <a class="nav-item nav-link disabled" href="pannelloUtente.jsp">Pannello utente</a>
                <a class="nav-item nav-link disabled" href="visualizzaSegnalazioni.jsp">Visualizza segnalazioni</a>
                <a class="nav-item nav-link disabled" href="inoltraSegnalazione.jsp">Inoltra segnalazione</a>
                <a class="nav-item nav-link active" href="#">Login <span class="sr-only">(current)</span></a>
            </div>
        </div>
    </nav>

    	  <br>
<div class="container">
<center>
    
    <%
        if (session.getAttribute("successMessage") != null) { %>

    <div class="alert alert-success">
        <strong>Ok!</strong> <%= session.getAttribute("successMessage") %>
    </div>


    <% session.setAttribute("successMessage", null);
    }

        if (session.getAttribute("infoMessage") != null) {  %>


    <div class="alert alert-info">
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
             <div class="input-group-prepend">
                 <span class="input-group-text" id="inputGroup-sizing-default">Nome utente e Password</span>
             </div>
             <input userType="text" name="nickname" class="form-control" aria-label="Default" aria-describedby="inputGroup-sizing-default" required>
             <input userType="password" name="password" class="form-control" aria-label="Default" aria-describedby="inputGroup-sizing-default" required>

         </div>
         <button userType="sumbit" name="login" class="btn btn-primary btn-lg">Login</button></center>
     </form>
    </center>
</div>      

      
      <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    
    
    <script src="https://code.jquery.com/jquery-3.3.1.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js" integrity="sha384-wHAiFfRlMFy6i5SRaxvfOCifBUQy1xHdJ/yoi7FRNXMRBu5WHdZYu1hA6ZOblgut" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js" integrity="sha384-B0UglyR+jN6CkvvICOB2joaf5I4l3gm9GU6Hc1og6Ls7i6U/mkkaduKaBhlAXv9k" crossorigin="anonymous"></script>
    <!-- Bootstrap Date-Picker Plugin -->
    

</body>
</html>