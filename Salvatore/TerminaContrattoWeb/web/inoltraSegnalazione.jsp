<%@page import="java.sql.SQLException"%>
<%@page import="java.time.temporal.ChronoUnit"%>
<%@page import="java.time.LocalDate"%>
<%@page import="Bean.contractBean"%>
<%@page import="java.util.LinkedList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import= "Controller.Controller, Bean.paymentClaimBean" %>
<%@ page import="Exceptions.emptyResult" %>
<%@ page import="Exceptions.dbConnection" %>
<%@ page import="Exceptions.transactionError" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.DateFormat" %>

<jsp:useBean id="sessionBean" scope="session" class="Bean.userSessionBean"/>

<%     if (sessionBean.getId() == 0){

    response.sendRedirect("index.jsp?error=makeLogin");

} %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css" integrity="sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.4.1/css/bootstrap-datepicker3.css"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.5.0/css/bootstrap-datepicker3.min.css">

    <script userType='text/javascript' src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.5.0/js/bootstrap-datepicker.min.js"></script>
    <link href="https://netdna.bootstrapcdn.com/bootstrap/3.0.3/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://netdna.bootstrapcdn.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>
    <script userType='text/javascript' src='http://code.jquery.com/jquery-1.8.3.min.js'></script>

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.5.0/css/bootstrap-datepicker3.min.css">
    <script userType='text/javascript' src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.5.0/js/bootstrap-datepicker.min.js"></script>
    
    <%
        LocalDate today = LocalDate.now();
        LocalDate nextWeek = today.plus(1, ChronoUnit.WEEKS);   
    %>

    <script userType='text/javascript'>
        $(function(){
        $('.input-group.date').datepicker({
            format: "yyyy-mm-dd",
            calendarWeeks: true,
            startDate: "<%= (nextWeek) %>",
            autoclose: true
        });
        });
    </script>
    <title>Hello, world!</title>
  </head>

  <%
    Controller controllerProva = sessionBean.getController();

    if (request.getParameter("date") != null) {
            paymentClaimBean bean = new paymentClaimBean();
            bean.setContractId(Integer.parseInt(request.getParameter("contractId")));
            bean.setTenantNickname(request.getParameter("tenantUsername"));
            bean.setClaimDeadline(request.getParameter("dataScadenza"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern ("yyyy-MM-dd" );
        LocalDate localDate = LocalDate.parse (request.getParameter("dataScadenza") , formatter );

        if ( localDate.isAfter(LocalDate.now().plusWeeks(2)) ){
            String destination ="inoltraSegnalazione.jsp";
            response.sendRedirect(response.encodeRedirectURL(destination));
            session.setAttribute("wrongDate", "");
            return;
        }

        try {
            controllerProva.insertNewPaymentClaim(bean);
        } catch (SQLException e) {
            response.sendRedirect("index.jsp?error=databaseConnection");
            return;
        } catch (Exceptions.transactionError transactionError) { %>

            <jsp:forward page="pannelloUtente.jsp">
                <jsp:param name="error" value="transaction" />
            </jsp:forward>

            <%
            return;}
            %>

        <jsp:forward page="pannelloUtente.jsp">
            <jsp:param name="success" value="claimCreated" />
        </jsp:forward>

    <%
        return; }
      %>

  <body>

  <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
      <a class="navbar-brand" href="#">FERSA</a>
      <button class="navbar-toggler" userType="button" data-toggle="collapse" data-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
          <div class="navbar-nav">
              <a class="nav-item nav-link " href="pannelloUtente.jsp">Pannello utente</a>
              <a class="nav-item nav-link active" href="visualizzaSegnalazioni.jsp">Visualizza segnalazioni  <span class="sr-only">(current)</span></a>
              <a class="nav-item nav-link " href="inoltraSegnalazione.jsp">Inoltra segnalazione</a>
              <a class="nav-item nav-link" href="index.jsp">Login</a>
          </div>
      </div>
  </nav>
    	  <br>
          <% List<contractBean> listaResult = null;
              try {
                  listaResult = sessionBean.getController().getContracts(sessionBean);
              } catch (SQLException e) {
          %><jsp:forward page="pannelloUtente.jsp">
      <jsp:param name="error" value="transaction" />
  </jsp:forward><% return;
              } catch (emptyResult e) {
          %><jsp:forward page="pannelloUtente.jsp">
    <jsp:param name="error" value="emptyActiveContract" />
</jsp:forward><%
              return;
              } %>

<div class="container">
    <center>
                <%
    if (session.getAttribute("wrongDate") != null) { %>


    <div class="alert alert-warning">
        <strong>Errore!</strong> Errore! Non è possibile inserire una data di scadenza oltre le due settimane!
    </div>


    <% session.setAttribute("wrongDate", null);
    }


    		for (contractBean temp : listaResult) {
		%>
    <form action="inoltraSegnalazione.jsp" name="myform" method="POST"><div class="row justify-content-md-center ">
    <div class="col-md">
        <b>ID Contratto:</b> <%= temp.getContractId() %>
    </div>
    <div class="col-md">
        <b>ID Locatario:</b> <%= temp.getTenantNickname() %>
    </div>
      
    <div class="col-md">
        <b>Stato contratto:</b> <%= temp.getContractState() %>
    </div>
      
    <div class="col-md">
        <div class="input-group date">
        <input name="dataScadenza" placeholder="Seleziona una data..." value='<%= (nextWeek) %>' userType="text" class="form-control"><span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
</div>
    </div>
          <div class="col-md">
            <input name = "date" userType="submit" class="btn btn-info" value="Reinoltra segnalazione">
    </div>
      
  </div>
        <input userType="hidden" id="custId" name="contractId" value="<%= temp.getContractId() %>">
        <input userType="hidden" id="custId" name="tenantUsername" value="<%= temp.getTenantNickname() %>">

    </form>
    <br>
                    <%
                }
            %>
    </center></div>

    <script src="https://code.jquery.com/jquery-3.3.1.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js" integrity="sha384-wHAiFfRlMFy6i5SRaxvfOCifBUQy1xHdJ/yoi7FRNXMRBu5WHdZYu1hA6ZOblgut" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js" integrity="sha384-B0UglyR+jN6CkvvICOB2joaf5I4l3gm9GU6Hc1og6Ls7i6U/mkkaduKaBhlAXv9k" crossorigin="anonymous"></script>
    <link href="//netdna.bootstrapcdn.com/bootstrap/3.0.3/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://netdna.bootstrapcdn.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>
</body>
</html>