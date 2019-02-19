<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import= "it.uniroma2.ispw.fersa.Controller.controller, it.uniroma2.ispw.fersa.Bean.userSessionBean" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="it.uniroma2.ispw.fersa.Entity.Enum.typeOfUser" %>
<%@ page import="it.uniroma2.ispw.fersa.projectThread.checkPaymentclaimDate" %>
<%@ page import="java.util.Timer" %>
<%@ page import="java.util.TimerTask" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="it.uniroma2.ispw.fersa.Entity.Enum.typeOfMessage" %>
<%@ page import="it.uniroma2.ispw.fersa.Entity.Enum.titleOfWindows" %>

<jsp:useBean id="sessionBean" scope="session" class="it.uniroma2.ispw.fersa.Bean.userSessionBean"/>

<%

    java.util.concurrent.TimeUnit.SECONDS.sleep(5);
    checkPaymentclaimDate.startTask();


    if (request.getParameter("login") != null) {
        userSessionBean login = new userSessionBean(request.getParameter("nickname"), 1, typeOfUser.NOTLOGGED, 0, request.getParameter("password"), null);
        try {
            controller controller = new controller();
            login = controller.login(login);
            sessionBean.setId(login.getId());
            sessionBean.setController(controller);
            sessionBean.setNickname(login.getNickname());
            sessionBean.setUserType(login.getUserType());
            controller.setTypeOfUSer(login.getUserType());
        %>
            <jsp:forward page="pannelloUtente.jsp"/>
        <%
            return;
        } catch (it.uniroma2.ispw.fersa.Exceptions.emptyResult emptyResult) {
            session.setAttribute("infoMessage", "Nessun utente associato!");
            String destination ="index.jsp";
            response.sendRedirect(response.encodeRedirectURL(destination));
            return;
        } catch (SQLException e) {
            e.printStackTrace();
            session.setAttribute("warningMessage", typeOfMessage.DBERROR.getString());
            String destination ="index.jsp";
            response.sendRedirect(response.encodeRedirectURL(destination));
            return;
        } catch (it.uniroma2.ispw.fersa.Exceptions.dbConfigMissing missingConfig) {
            missingConfig.printStackTrace();
            session.setAttribute("warningMessage", typeOfMessage.DBCONFIGERROR.getString());
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



    <title><%= titleOfWindows.LOGIN.getString() %></title>
             
  </head>
    
    <body>

    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <a class="navbar-brand" href="#">FERSA</a>
        <button class="navbar-toggler" userType="button" data-toggle="collapse" data-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
            <div class="navbar-nav">
                <a class="nav-item nav-link active" href="#">Login <span class="sr-only">(current)</span></a>
                <a class="nav-item nav-link disabled" href="pannelloUtente.jsp">Pannello utente</a>
                <a class="nav-item nav-link disabled" href="visualizzaSegnalazioni.jsp">Visualizza segnalazioni</a>
                <a class="nav-item nav-link disabled" href="inoltraSegnalazione.jsp">Inoltra segnalazione</a>
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
             <input type="text" name="nickname" class="form-control" aria-label="Default" aria-describedby="inputGroup-sizing-default" required>
             <input type="password" name="password" class="form-control" aria-label="Default" aria-describedby="inputGroup-sizing-default" required>

         </div>
         <button userType="sumbit" name="login" class="btn btn-primary btn-lg">Login</button></center>
     </form>
    </center>
</div>


    <script type='text/javascript' src='${pageContext.request.contextPath}/Resource/jquery-1.8.3.min.js'></script>
    <link rel='stylesheet' href='${pageContext.request.contextPath}/Resource/bootstrap.min.css'>
    <link rel='stylesheet' href='${pageContext.request.contextPath}/Resource/bootstrap-datepicker3.min.css'>
    <script type='text/javascript' src='${pageContext.request.contextPath}/Resource/bootstrap-datepicker.min.js'></script>

</body>
</html>