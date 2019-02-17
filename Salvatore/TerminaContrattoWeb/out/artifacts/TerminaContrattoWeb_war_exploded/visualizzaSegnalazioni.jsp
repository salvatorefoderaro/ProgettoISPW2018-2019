<%@page import="java.sql.SQLException"%>
<%@page import="java.util.LinkedList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import= "it.uniroma2.ispw.fersa.Controller.Controller, it.uniroma2.ispw.fersa.Bean.userSessionBean" %>
<%@ page import="it.uniroma2.ispw.fersa.Bean.paymentClaimBean" %>
<%@ page import="it.uniroma2.ispw.fersa.Exceptions.transactionError" %>
<%@ page import="it.uniroma2.ispw.fersa.Exceptions.emptyResult" %>
<%@ page import="it.uniroma2.ispw.fersa.Entity.Enum.TypeOfUser" %>
<%@ page import="it.uniroma2.ispw.fersa.Entity.Enum.TypeOfMessage" %>
<%@ page import="it.uniroma2.ispw.fersa.Entity.Enum.TitleOfWindows" %>

<jsp:useBean id="sessionBean" scope="session" class="it.uniroma2.ispw.fersa.Bean.userSessionBean"/>

<%
    if (sessionBean.getId() == 0){
    session.setAttribute("infoMessage", TypeOfMessage.NOTLOGGED.getString());
    String destination ="index.jsp";
    response.sendRedirect(response.encodeRedirectURL(destination));
    return;
} %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"<html lang="en">
  <head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">




      <title><%= TitleOfWindows.SEEPAYMENTCLAIM.getString() %></title>
             
  </head>
  <% Controller parentController = sessionBean.getController();
        paymentClaimBean bean = new paymentClaimBean();
        
        if (request.getParameter("0") != null) {

            bean.setClaimId(Integer.parseInt(request.getParameter("id")));
            bean.setContractId(Integer.parseInt(request.getParameter("contractID")));

            try {
                parentController.setPaymentClaimPayed(bean);
            } catch (SQLException e) {
                e.printStackTrace();
                session.setAttribute("infoMessage", TypeOfMessage.DBERROR.getString());
                String destination = "index.jsp";
                response.sendRedirect(response.encodeRedirectURL(destination));
                return;
            } catch (it.uniroma2.ispw.fersa.Exceptions.transactionError transactionError) {
                session.setAttribute("infoMessage", TypeOfMessage.TRANSATIONERROR.getString());
                String destination = "index.jsp";
                response.sendRedirect(response.encodeRedirectURL(destination));
                return;
            } catch (it.uniroma2.ispw.fersa.Exceptions.dbConfigMissing missingConfig) {
                missingConfig.printStackTrace();
                session.setAttribute("warningMessage", TypeOfMessage.DBCONFIGERROR.getString());
                String destination ="index.jsp";
                response.sendRedirect(response.encodeRedirectURL(destination));
                return;
            }
            String destination = "visualizzaSegnalazioni.jsp";
            response.sendRedirect(response.encodeRedirectURL(destination));
            session.setAttribute("infoMessage", TypeOfMessage.SUCCESSOPERATION.getString());
            return;
        }
        
    if (request.getParameter("1") != null) {
       
        bean.setClaimId(Integer.parseInt(request.getParameter("id")));
        bean.setClaimNumber(Integer.parseInt(request.getParameter("numeroReclamo")));
            try {
                parentController.incrementPaymentClaim(bean);
            } catch (SQLException e) {
                e.printStackTrace();
                session.setAttribute("infoMessage", TypeOfMessage.DBERROR.getString());
                String destination ="index.jsp";
                response.sendRedirect(response.encodeRedirectURL(destination));
                return;
            }
            catch (it.uniroma2.ispw.fersa.Exceptions.transactionError transactionError) {
                session.setAttribute("infoMessage", TypeOfMessage.TRANSATIONERROR.getString());
                String destination ="index.jsp";
                response.sendRedirect(response.encodeRedirectURL(destination));
                return;
            } catch (it.uniroma2.ispw.fersa.Exceptions.dbConfigMissing missingConfig) {
                missingConfig.printStackTrace();
                session.setAttribute("warningMessage", TypeOfMessage.DBCONFIGERROR.getString());
                String destination ="index.jsp";
                response.sendRedirect(response.encodeRedirectURL(destination));
                return;
            }
        String destination ="visualizzaSegnalazioni.jsp";
        response.sendRedirect(response.encodeRedirectURL(destination));
        session.setAttribute("infoMessage", TypeOfMessage.SUCCESSOPERATION.getString());
        return;
    }

    if (request.getParameter("2") != null) {
       
        bean.setClaimId(Integer.parseInt(request.getParameter("id")));

            try {
                parentController.setContractAchieved(bean);
            } catch (SQLException e) {
                e.printStackTrace();
                session.setAttribute("infoMessage", TypeOfMessage.DBERROR.getString());
                String destination ="index.jsp";
                response.sendRedirect(response.encodeRedirectURL(destination));
                return;
            }
            catch (it.uniroma2.ispw.fersa.Exceptions.transactionError transactionError) {
                session.setAttribute("infoMessage", TypeOfMessage.TRANSATIONERROR.getString());
                String destination ="index.jsp";
                response.sendRedirect(response.encodeRedirectURL(destination));
                return;
            } catch (it.uniroma2.ispw.fersa.Exceptions.dbConfigMissing missingConfig) {
                missingConfig.printStackTrace();
                session.setAttribute("warningMessage", TypeOfMessage.DBCONFIGERROR.getString());
                String destination ="index.jsp";
                response.sendRedirect(response.encodeRedirectURL(destination));
                return;
            }
        String destination ="visualizzaSegnalazioni.jsp";
        response.sendRedirect(response.encodeRedirectURL(destination));
        session.setAttribute("infoMessage", TypeOfMessage.SUCCESSOPERATION.getString());
        return;
    }
    
   if (request.getParameter("3") != null){
       
       bean.setClaimId(Integer.parseInt(request.getParameter("id")));
          try {
               parentController.setPaymentClaimNotified(bean);
          } catch (SQLException e) {
              e.printStackTrace();
              session.setAttribute("infoMessage", TypeOfMessage.DBERROR.getString());
              String destination ="index.jsp";
              response.sendRedirect(response.encodeRedirectURL(destination));
              return;
          }
          catch (it.uniroma2.ispw.fersa.Exceptions.transactionError transactionError) {
              session.setAttribute("infoMessage", TypeOfMessage.TRANSATIONERROR.getString());
              String destination ="index.jsp";
              response.sendRedirect(response.encodeRedirectURL(destination));
              return;
          } catch (it.uniroma2.ispw.fersa.Exceptions.dbConfigMissing missingConfig) {
              missingConfig.printStackTrace();
              session.setAttribute("warningMessage", TypeOfMessage.DBCONFIGERROR.getString());
              String destination ="index.jsp";
              response.sendRedirect(response.encodeRedirectURL(destination));
              return;
          }
       String destination ="visualizzaSegnalazioni.jsp";
       response.sendRedirect(response.encodeRedirectURL(destination));
       session.setAttribute("infoMessage", TypeOfMessage.SUCCESSOPERATION.getString());
       return;
   }
        
       if (request.getParameter("4") != null) {
           bean.setClaimId(Integer.parseInt(request.getParameter("id")));
               try {
                   parentController.setPaymentClaimPayed(bean);
               } catch (SQLException e) {
                   e.printStackTrace();
                   session.setAttribute("infoMessage", TypeOfMessage.DBERROR.getString());
                   String destination ="index.jsp";
                   response.sendRedirect(response.encodeRedirectURL(destination));
                   return;
               }
               catch (it.uniroma2.ispw.fersa.Exceptions.transactionError transactionError) {
                   session.setAttribute("infoMessage", TypeOfMessage.TRANSATIONERROR.getString());
                   String destination ="index.jsp";
                   response.sendRedirect(response.encodeRedirectURL(destination));
                   return;
               } catch (it.uniroma2.ispw.fersa.Exceptions.dbConfigMissing missingConfig) {
                   missingConfig.printStackTrace();
                   session.setAttribute("warningMessage", TypeOfMessage.DBCONFIGERROR.getString());
                   String destination ="index.jsp";
                   response.sendRedirect(response.encodeRedirectURL(destination));
                   return;
               }
           String destination ="visualizzaSegnalazioni.jsp";
           response.sendRedirect(response.encodeRedirectURL(destination));
           session.setAttribute("infoMessage", TypeOfMessage.SUCCESSOPERATION.getString());
           return;
       }

    List<paymentClaimBean> listaResult = null;
    List<Integer> IDSegnalazioni = new LinkedList<>();

      try {
          listaResult = parentController.getPaymentClaims(sessionBean);
      } catch (it.uniroma2.ispw.fersa.Exceptions.emptyResult emptyResult) {
          session.setAttribute("infoMessage", "Nessuna segnalazione di pagamento al momento disponibile!");
          String destination ="index.jsp";
          response.sendRedirect(response.encodeRedirectURL(destination));
          return;
      } catch (SQLException e) {
          session.setAttribute("infoMessage", TypeOfMessage.DBERROR.getString());
          String destination ="index.jsp";
          response.sendRedirect(response.encodeRedirectURL(destination));
          return;
      } catch (it.uniroma2.ispw.fersa.Exceptions.dbConfigMissing missingConfig) {
          session.setAttribute("warningMessage", TypeOfMessage.DBCONFIGERROR.getString());
          String destination ="index.jsp";
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
                    if(sessionBean.getUserType() == TypeOfUser.RENTER){
%>
                    <button type="button" class="btn btn-outline-secondary" disabled>In attesa del locatario</button>
            <% } else { %> 
                    <button name ="0" type="submit" class="btn btn-outline-secondary">Conferma pagamento</button>
                             <input type="hidden" id="custId" name="id" value="<%= temp.getClaimId() %>">
                            <input type="hidden" name="contractID" value="<%= temp.getContractId() %>">
   
                    <% 
                        }
                    break;
                
                case 1:
                if(sessionBean.getUserType() == TypeOfUser.RENTER){
                %>
        <input name = "1" type="submit" class="btn btn-info" value="Reinoltra segnalazione">
        <input type="hidden" id="custId" name="id" value="<%= temp.getClaimId() %>">
         <input type="hidden" id="custId" name="numeroReclamo" value="<%= temp.getClaimNumber() %>">

                <%} else{ %>
        <input  type="submit" class="btn btn-info" value="In attesa del locatore" disabled>
                    <%}
                
                    break;
                    
                case 2:                        
                if(sessionBean.getUserType() == TypeOfUser.RENTER){
                    %> 
        <input name = "2" type="submit" class="btn btn-info" value="Archivia contratto">
        <input type="hidden" id="custId" name="id" value="<%= temp.getClaimId() %>">
            <% }else {%>
                    <input  class="btn btn-info" value="In attesa del locatore" disabled>

            <% }
                    break;
                    
                case 3:

                   if(sessionBean.getUserType() == TypeOfUser.RENTER){



                    %>
        <button type="submit" name="3" class="btn btn-outline-secondary">Archivia notifica</button>
                                     <input type="hidden" id="custId" name="id" value="<%= temp.getClaimId() %>">

                    <% }else{ %>
                            <button type="button" class="btn btn-outline-secondary" disabled>Archivia contratto</button>

        <%}   break;
            
        case 4:

        if(sessionBean.getUserType() == TypeOfUser.RENTER){ %>
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


<script type='text/javascript' src='${pageContext.request.contextPath}/Resource/jquery-1.8.3.min.js'></script>
<link rel='stylesheet' href='${pageContext.request.contextPath}/Resource/bootstrap.min.css'>
<link rel='stylesheet' href='${pageContext.request.contextPath}/Resource/bootstrap-datepicker3.min.css'>
<script type='text/javascript' src='${pageContext.request.contextPath}/Resource/bootstrap-datepicker.min.js'></script>

</body>
</html>