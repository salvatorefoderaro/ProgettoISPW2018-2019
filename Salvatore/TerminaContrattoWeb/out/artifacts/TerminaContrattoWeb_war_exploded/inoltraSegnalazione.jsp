<%@page import="java.sql.SQLException"%>
<%@page import="java.time.temporal.ChronoUnit"%>
<%@page import="java.time.LocalDate"%>
<%@page import="it.uniroma2.ispw.fersa.Bean.contractBean"%>
<%@page import="java.util.LinkedList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import= "it.uniroma2.ispw.fersa.Controller.Controller, it.uniroma2.ispw.fersa.Bean.paymentClaimBean" %>
<%@ page import="it.uniroma2.ispw.fersa.Exceptions.emptyResult" %>
<%@ page import="it.uniroma2.ispw.fersa.Exceptions.transactionError" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="it.uniroma2.ispw.fersa.Entity.Enum.TypeOfMessage" %>
<%@ page import="it.uniroma2.ispw.fersa.Entity.Enum.TitleOfWindows" %>
<%@ page import="it.uniroma2.ispw.fersa.Exceptions.alreadyClaimed" %>

<jsp:useBean id="sessionBean" scope="session" class="it.uniroma2.ispw.fersa.Bean.userSessionBean"/>

<%
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


    <%
        LocalDate today = LocalDate.now();
        LocalDate nextWeek = today.plus(1, ChronoUnit.WEEKS);   
    %>

    <script type='text/javascript'>
        $(function(){
        $('.input-group.date').datepicker({
            format: "yyyy-mm-dd",
            calendarWeeks: true,
            startDate: "<%= (nextWeek) %>",
            autoclose: true
        });
        });
    </script>
    <title><%= TitleOfWindows.CREATEPAYMENTCLAIM.getString() %></title>
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

        if (localDate.isAfter(LocalDate.now().plusWeeks(2)) ){
            session.setAttribute("infoMessage", TypeOfMessage.WRONGDATEINTERVAL.getString());
            String destination ="inoltraSegnalazione.jsp";
            response.sendRedirect(response.encodeRedirectURL(destination));
            return;
        }

        try {
            controllerProva.insertNewPaymentClaim(bean);
        } catch (SQLException e) {
            e.printStackTrace();
            session.setAttribute("infoMessage", TypeOfMessage.TRANSATIONERROR.getString());
            String destination ="index.jsp";
            response.sendRedirect(response.encodeRedirectURL(destination));
            return;
        } catch (it.uniroma2.ispw.fersa.Exceptions.transactionError transactionError) {
            session.setAttribute("infoMessage", TypeOfMessage.TRANSATIONERROR.getString());
            String destination ="inoltraSegnalazione.jsp";
            response.sendRedirect(response.encodeRedirectURL(destination));
            return;
        } catch (it.uniroma2.ispw.fersa.Exceptions.dbConfigMissing missingConfig) {
            session.setAttribute("warningMessage", TypeOfMessage.DBCONFIGERROR.getString());
            String destination ="index.jsp";
            response.sendRedirect(response.encodeRedirectURL(destination));
            return;
        } catch (it.uniroma2.ispw.fersa.Exceptions.alreadyClaimed alreadyClaimed) {
            session.setAttribute("warningMessage", "Per il contratto è già presente una segnalazione di pagamento in sospeso!");
            String destination ="index.jsp";
            response.sendRedirect(response.encodeRedirectURL(destination));
            return;
        }

        session.setAttribute("successMessage", TypeOfMessage.SUCCESSOPERATION.getString());
        String destination ="pannelloUtente.jsp";
        response.sendRedirect(response.encodeRedirectURL(destination));
        return;
    }
      %>

  <body>

  <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
      <a class="navbar-brand" href="#">FERSA</a>
      <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
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
                  session.setAttribute("infoMessage", TypeOfMessage.DBERROR.getString());
                  String destination ="index.jsp";
                  response.sendRedirect(response.encodeRedirectURL(destination));
                  return;
              } catch (emptyResult e) {
                  session.setAttribute("infoMessage", "Nessun contratto da segnalare al momento!");
                  String destination ="pannelloUtente.jsp";
                  response.sendRedirect(response.encodeRedirectURL(destination));
                  return;
              } catch (it.uniroma2.ispw.fersa.Exceptions.dbConfigMissing missingConfig) {
                  missingConfig.printStackTrace();
                  session.setAttribute("warningMessage", TypeOfMessage.DBCONFIGERROR.getString());
                  String destination ="index.jsp";
                  response.sendRedirect(response.encodeRedirectURL(destination));
                  return;
              } %>

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
        } 

    		for (contractBean temp : listaResult) {
		%>
    <form action="inoltraSegnalazione.jsp" name="myform" method="POST"><div class="row justify-content-md-center ">
    <div class="col-md">
        <b>ID Contratto:</b> <%= temp.getContractId() %>
    </div>
    <div class="col-md">
        <b>Locatario:</b> <%= temp.getTenantNickname() %>
    </div>
      
    <div class="col-md">
        <b>Termine contratto:</b> <%= temp.getEndDate() %>
    </div>
      
    <div class="col-md">
        <div class="input-group date">
        <input name="dataScadenza" placeholder="Seleziona una data..." value='<%= (nextWeek) %>' type="text" class="form-control"><span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
</div>
    </div>
          <div class="col-md">
            <input name = "date" type="submit" class="btn btn-info" value="Reinoltra segnalazione">
    </div>
      
  </div>
        <input type="hidden" id="custId" name="contractId" value="<%= temp.getContractId() %>">
        <input type="hidden" id="custId" name="tenantUsername" value="<%= temp.getTenantNickname() %>">

    </form>
    <br>
                    <%
                }
            %>
    </center></div>

  <script type='text/javascript' src='${pageContext.request.contextPath}/Resource/jquery-1.8.3.min.js'></script>
  <link rel='stylesheet' href='${pageContext.request.contextPath}/Resource/bootstrap.min.css'>
  <link rel='stylesheet' href='${pageContext.request.contextPath}/Resource/bootstrap-datepicker3.min.css'>
  <script type='text/javascript' src='${pageContext.request.contextPath}/Resource/bootstrap-datepicker.min.js'></script>

</body>
</html>