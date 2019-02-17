<%@page import="java.util.LinkedList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import= "it.uniroma2.ispw.fersa.Controller.Controller, it.uniroma2.ispw.fersa.Bean.userSessionBean" %>
<%@ page import="it.uniroma2.ispw.fersa.Entity.Enum.TypeOfUser" %>
<%@ page import="java.util.TimerTask" %>
<%@ page import="java.util.Timer" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="it.uniroma2.ispw.fersa.Entity.Enum.TypeOfMessage" %>
<%@ page import="it.uniroma2.ispw.fersa.Entity.Enum.TitleOfWindows" %>
<%@ page import="it.uniroma2.ispw.fersa.Exceptions.dbConfigMissing" %>
<%@ page import="it.uniroma2.ispw.fersa.Exceptions.emptyResult" %>

<jsp:useBean id="sessionBean" scope="session" class="it.uniroma2.ispw.fersa.Bean.userSessionBean"/>

<%
/*
    int initialDelay = 10000; // start after 30 seconds
    int period = 5000;        // repeat every 5 seconds
    Timer timer = new Timer();
    TimerTask task = new TimerTask() {
        public void run() {
            try {
                it.uniroma2.ispw.fersa.Controller controller = new it.uniroma2.ispw.fersa.Controller();
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

      <script type='text/javascript' src='${pageContext.request.contextPath}/Resource/jquery-1.8.3.min.js'></script>
      <link rel='stylesheet' href='${pageContext.request.contextPath}/Resource/bootstrap.min.css'>
      <link rel='stylesheet' href='${pageContext.request.contextPath}/Resource/bootstrap-datepicker3.min.css'>
      <script type='text/javascript' src='${pageContext.request.contextPath}/Resource/bootstrap-datepicker.min.js'></script>


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
        <a class="nav-item nav-link" href="index.jsp">Login</a>
        <a class="nav-item nav-link active" href="#">Pannello utente <span class="sr-only">(current)</span></a>
        <a class="nav-item nav-link" href="visualizzaSegnalazioni.jsp">Visualizza segnalazioni</a>
        <a class="nav-item nav-link" href="inoltraSegnalazione.jsp">Inoltra segnalazione</a>
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
        } if (session.getAttribute("successMessage") != null) { %>

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

    <h4>Bentornato <%= sessionBean.getNickname() %></h4>

        <% if(sessionBean.getUserType() == TypeOfUser.RENTER){  %>
        <a href="inoltraSegnalazione.jsp"><button type="sumbit" name="Locatore" class="btn btn-primary btn-lg">Inoltra segnalazione</button></a>
        <% } %>

        <a href="visualizzaSegnalazioni.jsp">
            <button type="submit" name="Locatario" class="btn btn-secondary btn-lg">
                Visualizza segnalazioni <%
                int counter = 0;
                try {
                    counter = sessionBean.getController().getPaymentClaimNumber(sessionBean).getNotificationNumber();
                    %><span class="badge badge-light"><%= counter%></span><%
                }catch (it.uniroma2.ispw.fersa.Exceptions.dbConfigMissing dbConfigMissing) {
                    System.out.println("Entro in errore1?");
                    session.setAttribute("warningMessage", TypeOfMessage.DBCONFIGERROR.getString());
                    String destination ="index.jsp";
                    response.sendRedirect(response.encodeRedirectURL(destination));
                    return;
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println("Entro in errore?2");
                    session.setAttribute("warningMessage", TypeOfMessage.DBERROR.getString());
                    String destination ="index.jsp";
                    response.sendRedirect(response.encodeRedirectURL(destination));
                    return;
                } catch (it.uniroma2.ispw.fersa.Exceptions.emptyResult ignored) {
                    System.out.println("Entro in errore?3");
                }                   %>
            </button>
        </a>
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