<%@page import="java.sql.SQLException"%>
<%@page import="java.util.LinkedList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import= "Controller.Controller, Bean.userSessionBean" %>
<%@ page import="Bean.paymentClaimBean" %>
<%@ page import="Exceptions.transactionError" %>
<%@ page import="Exceptions.dbConnection" %>
<%@ page import="Exceptions.emptyResult" %>
<%@ page import="Entity.TypeOfUser" %>

<jsp:useBean id="sessionBean" scope="session" class="Bean.userSessionBean"/>

<%
    if (sessionBean.getId() == 0){
    response.sendRedirect("index.jsp?error=makeLogin");
    return;
} %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"<html lang="en">
  <head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css" integrity="sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.4.1/css/bootstrap-datepicker3.css"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.5.0/css/bootstrap-datepicker3.min.css">
    
    
    <script type='text/javascript' src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.5.0/js/bootstrap-datepicker.min.js"></script>

        
    <title>Hello, world!</title>
             
  </head>
  <% Controller parentController = sessionBean.getController();
        paymentClaimBean bean = new paymentClaimBean();
        
        if (request.getParameter("0") != null){
        
            bean.setClaimId(Integer.parseInt(request.getParameter("id")));
            try {
                parentController.setSegnalazionePagata(bean);
            } catch (Exceptions.transactionError transactionError) { %>

<jsp:forward page="pannelloUtente.jsp">
    <jsp:param name="error" value="transaction" />
</jsp:forward>

<% return; } catch (Exceptions.dbConnection dbConnection) {
                response.sendRedirect("index.jsp?error=databaseConnection");
                return;
            }
    String destination ="visualizzaSegnalazioni.jsp";
    response.sendRedirect(response.encodeRedirectURL(destination));
    session.setAttribute("success", "");
    return;
        }
        
    if (request.getParameter("1") != null) {
       
        bean.setClaimId(Integer.parseInt(request.getParameter("id")));
        bean.setClaimNumber(Integer.parseInt(request.getParameter("numeroReclamo")));
        try {
            parentController.incrementaSegnalazione(bean);
        } catch (Exceptions.dbConnection dbConnection) {
            response.sendRedirect("index.jsp?error=databaseConnection");
            return;
        } catch (Exceptions.transactionError transactionError) { %>

<jsp:forward page="pannelloUtente.jsp">
    <jsp:param name="error" value="transaction" />
</jsp:forward>

<% return; } }

    if (request.getParameter("2") != null) {
       
        bean.setClaimId(Integer.parseInt(request.getParameter("id")));
        try {
            parentController.setContrattoArchiviato(bean);
        } catch (Exceptions.dbConnection dbConnection) {
            response.sendRedirect("index.jsp?error=databaseConnection");
            return;
        } catch (Exceptions.transactionError transactionError) { %>

<jsp:forward page="pannelloUtente.jsp">
    <jsp:param name="error" value="transaction" />
</jsp:forward>

<% return;}
    String destination ="visualizzaSegnalazioni.jsp";
    response.sendRedirect(response.encodeRedirectURL(destination));
    session.setAttribute("success", "");
    return;
    }
    
   if (request.getParameter("3") != null){
       
       bean.setClaimId(Integer.parseInt(request.getParameter("id")));
       try {
           parentController.setSegnalazioneNotificata(bean);
       } catch (Exceptions.dbConnection dbConnection) {
           response.sendRedirect("index.jsp?error=databaseConnection");
           return;
       } catch (Exceptions.transactionError transactionError) {
  %>

<jsp:forward page="pannelloUtente.jsp">
    <jsp:param name="error" value="transaction" />
</jsp:forward>

<% return;
}
    String destination ="visualizzaSegnalazioni.jsp";
    response.sendRedirect(response.encodeRedirectURL(destination));
    session.setAttribute("success", "");
    return;
   }
        
       if (request.getParameter("4") != null) {
           bean.setClaimId(Integer.parseInt(request.getParameter("id")));
           try {
               parentController.setSegnalazionePagata(bean);
           } catch (Exceptions.transactionError transactionError) { %>

                <jsp:forward page="pannelloUtente.jsp">
                <jsp:param name="error" value="transaction" />
                </jsp:forward>

<% return; } catch (Exceptions.dbConnection dbConnection) {
               response.sendRedirect("index.jsp?error=databaseConnection");
               return;
           }
    String destination ="visualizzaSegnalazioni.jsp";
    response.sendRedirect(response.encodeRedirectURL(destination));
    session.setAttribute("success", "");
    return;
       }

    List<paymentClaimBean> listaResult = null;
    List<Integer> IDSegnalazioni = new LinkedList<>();

      try {
          listaResult = parentController.getSegnalazioniPagamento(sessionBean);
      } catch (Exceptions.emptyResult emptyResult) { %>

            <jsp:forward page="pannelloUtente.jsp">
            <jsp:param name="error" value="emptyPaymentClaim" />
            </jsp:forward>

      <% return; } catch (SQLException e) {
          response.sendRedirect("index.jsp?error=databaseConnection");
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
        <a class="nav-item nav-link" href="pannelloUtente.jsp">Pannello utente</a>
        <a class="nav-item nav-link active" href="#">Visualizza segnalazioni  <span class="sr-only">(current)</span></a>
        <a class="nav-item nav-link" href="inoltraSegnalazione.jsp">Inoltra segnalazione</a>
        <a class="nav-item nav-link" href="index.jsp">Login</a>
    </div>
  </div>
</nav>
      
    	  <br>

<div class="container">
<center>
        <%
    if (session.getAttribute("success") != null) { %>


    <div class="alert alert-success">
        <strong>Ok!</strong> Operazione eseguita correttamente!
    </div>


    <% session.setAttribute("success", null);
        }

    		for (paymentClaimBean temp : listaResult) {
		%>
    <form action="visualizzaSegnalazioni.jsp" name="myform" method="POST"><div class="row justify-content-md-center ">
    <div class="col-md">
        <b>ID Contratto:</b> <%= temp.getContractId() %>
    </div>
    <div class="col-md">
        <b>Numero reclamo:</b> <%= temp.getClaimNumber() %>
    </div>
      
    <div class="col-md">
        <b>Scadenza reclamo:</b> <%= temp.getClaimDeadline() %>
    </div>
      
    <div class="col-md">
        
        <%
            switch(temp.getClaimState()){
                case 0: 
                    if(sessionBean.getType() == TypeOfUser.RENTER){
%>
                    <button type="button" class="btn btn-outline-secondary" disabled>In attesa del locatario</button>
            <% } else { %> 
                    <button name ="0" type="submit" class="btn btn-outline-secondary">Conferma pagamento</button>
                             <input type="hidden" id="custId" name="id" value="<%= temp.getClaimId() %>"> 
   
                    <% 
                        }
                    break;
                
                case 1:
                if(sessionBean.getType() == TypeOfUser.RENTER){
                %>
        <input name = "1" type="submit" class="btn btn-info" value="Reinoltra segnalazione">
        <input type="hidden" id="custId" name="id" value="<%= temp.getClaimId() %>"> 
         <input type="hidden" id="custId" name="numeroReclamo" value="<%= temp.getClaimNumber() %>"> 

                <%} else{ %>
        <input  type="submit" class="btn btn-info" value="In attesa del locatore" disabled>
                    <%}
                
                    break;
                    
                case 2:                        
                if(sessionBean.getType() == TypeOfUser.RENTER){
                    %> 
        <input name = "2" type="submit" class="btn btn-info" value="Archivia contratto">
        <input type="hidden" id="custId" name="id" value="<%= temp.getClaimId() %>"> 
            <% }else {%>
                    <input  class="btn btn-info" value="In attesa del locatore" disabled>

            <% }
                    break;
                    
                case 3:

                   if(sessionBean.getType() == TypeOfUser.RENTER){



                    %>
        <button type="submit" name="3" class="btn btn-outline-secondary">Archivia notifica</button>
                                     <input type="hidden" id="custId" name="id" value="<%= temp.getClaimId() %>"> 

                    <% }else{ %>
                            <button type="button" class="btn btn-outline-secondary" disabled>Archivia contratto</button>

        <%}   break;
            
        case 4:

        if(sessionBean.getType() == TypeOfUser.RENTER){ %>
                            <button type="submit" name="4" class="btn btn-outline-secondary">Archivia notifica</button>
                             <input type="hidden" id="custId" name="id" value="<%= temp.getClaimId() %>"> 

<% }else { %>
                            <button type="button" class="btn btn-outline-secondary" disabled>Conferma pagamento</button>
<% }
}
        %>
        
            
    </div>
      
  </div></form>
    <br>
                    <%
                }
            %>
</center></div>

      
      <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    
    
    <script src="https://code.jquery.com/jquery-3.3.1.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js" integrity="sha384-wHAiFfRlMFy6i5SRaxvfOCifBUQy1xHdJ/yoi7FRNXMRBu5WHdZYu1hA6ZOblgut" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js" integrity="sha384-B0UglyR+jN6CkvvICOB2joaf5I4l3gm9GU6Hc1og6Ls7i6U/mkkaduKaBhlAXv9k" crossorigin="anonymous"></script>
    <!-- Bootstrap Date-Picker Plugin -->
    

</body>
</html>