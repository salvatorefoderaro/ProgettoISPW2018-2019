<%@page import="java.util.LinkedList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import= "Controller.Controller, Bean.userSessionBean" %>
<%@ page import="Entity.Enum.TypeOfUser" %>
<%@ page import="java.util.TimerTask" %>
<%@ page import="java.util.Timer" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="Entity.Enum.TypeOfMessage" %>
<%@ page import="Entity.Enum.TitleOfWindows" %>

<jsp:useBean id="sessionBean" scope="session" class="Bean.userSessionBean"/>

<%
/*
    int initialDelay = 10000; // start after 30 seconds
    int period = 5000;        // repeat every 5 seconds
    Timer timer = new Timer();
    TimerTask task = new TimerTask() {
        public void run() {
            try {
                Controller controller = new Controller();
                controller.checkPaymentClaimDateScadenza();
            } catch (SQLException e) {
                System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + " - Errore nella connessione al database");
            }
        }
    };
    timer.scheduleAtFixedRate(task, initialDelay, period);
*/
    if (sessionBean.getId() == 0){
        session.setAttribute("infoMessage", TypeOfMessage.NOTLOGGED.getString());
        String destination ="index.jsp";
        response.sendRedirect(response.encodeRedirectURL(destination));
        return;
    } %>

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


      <title><%= TitleOfWindows.USERPANEL.getString() %></title>
             
  </head>
  <body>
      
      

<nav class="navbar navbar-expand-lg navbar-dark bg-dark">  
    <a class="navbar-brand" href="#">FERSA</a>
  <button class="navbar-toggler" userType="button" data-toggle="collapse" data-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>
  <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
    <div class="navbar-nav">
        <a class="nav-item nav-link active" href="#">Pannello utente <span class="sr-only">(current)</span></a>
        <a class="nav-item nav-link" href="visualizzaSegnalazioni.jsp">Visualizza segnalazioni</a>
        <a class="nav-item nav-link" href="inoltraSegnalazione.jsp">Inoltra segnalazione</a>
        <a class="nav-item nav-link" href="index.jsp">Login</a>
    </div>
  </div>
</nav>
      
    	  <br>
<div class="container">
<center>

    <%
        if (session.getAttribute("successMessage") != null) { %>

    <div class="alert alert-warning">
        <strong>Ok!</strong> <%= session.getAttribute("successMessage") %>
    </div>


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

Bentornato <%= sessionBean.getNickname() %> <br>

        <% if(sessionBean.getUserType() == TypeOfUser.RENTER){  %>
        <a href="inoltraSegnalazione.jsp"><button userType="sumbit" name="Locatore" class="btn btn-primary btn-lg">Inoltra segnalazione</button></a>
        <% } %>

        <a href="visualizzaSegnalazioni.jsp"><button userType="sumbit" name="Locatario" class="btn btn-secondary btn-lg">Visualizza segnalazioni</button></a>
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