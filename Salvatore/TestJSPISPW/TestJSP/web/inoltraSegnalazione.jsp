<%@page import="java.time.temporal.ChronoUnit"%>
<%@page import="java.time.LocalDate"%>
<%@page import="Bean.ContrattoBean"%>
<%@page import="java.util.LinkedList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import= "Controller.Controller, Bean.SegnalazionePagamentoBean" %>

<jsp:useBean id="sessionBean" scope="session" class="Bean.BeanSession"/>

<% 
    sessionBean.setId(30);
    sessionBean.setType("Locatario");
    sessionBean.setUsername("Pasquale");
%>
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
    <link href="https://netdna.bootstrapcdn.com/bootstrap/3.0.3/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://netdna.bootstrapcdn.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>
    <script type='text/javascript' src='http://code.jquery.com/jquery-1.8.3.min.js'></script>

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.5.0/css/bootstrap-datepicker3.min.css">
    <script type='text/javascript' src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.5.0/js/bootstrap-datepicker.min.js"></script>
<%
        LocalDate today = LocalDate.now();
        System.out.println("Current date: " + today);

        //add 2 week to the current date
        LocalDate nextWeek = today.plus(1, ChronoUnit.WEEKS);   
%>
        
    <title>Hello, world!</title>
            
  </head>
  <%
    Controller controllerProva = Controller.getInstance(); 
    
    if (request.getParameter("date") != null) {
        
                SegnalazionePagamentoBean bean = new SegnalazionePagamentoBean();
                bean.setIDContratto(Integer.parseInt(request.getParameter("idContratto")));
                bean.setIDLocatario(Integer.parseInt(request.getParameter("idLocatario")));
                bean.setScadenzaReclamo(request.getParameter("dataScadenza"));
                
                controllerProva.testMakeBean(bean);
       
        out.print(request.getParameter("dataScadenza"));
        out.print(request.getParameter("idContratto"));
        out.print(request.getParameter("idLocatario"));
    }

            List<ContrattoBean> listaResult = controllerProva.getContratti(sessionBean.getId());

     %>
  <body>
      
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">  
    <a class="navbar-brand" href="#">FERSA</a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>
  <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
    <div class="navbar-nav">
      <a class="nav-item nav-link active" href="#">Pannello utente <span class="sr-only">(current)</span></a>
      <a class="nav-item nav-link" href="#">Visualizza segnalazioni</a>
      <a class="nav-item nav-link" href="#">Inoltra segnalazione</a>
    </div>
  </div>
</nav>
      
    	  <br>
<div class="container">
                <%
    		for (ContrattoBean temp : listaResult) {
		%>
    <form action="inoltraSegnalazione.jsp" name="myform" method="POST"><div class="row justify-content-md-center ">
    <div class="col-md">
        <b>ID Contratto:</b> <%= temp.getIDContratto() %>
    </div>
    <div class="col-md">
        <b>ID Locatario:</b> <%= temp.getIDLocatario() %>
    </div>
      
    <div class="col-md">
        <b>Stato contratto:</b> <%= temp.getStatoContratto() %>
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
        <input type="hidden" id="custId" name="idContratto" value="<%= temp.getIDContratto() %>"> 
        <input type="hidden" id="custId" name="idLocatario" value="<%= temp.getIDLocatario() %>"> 

    </form>
    <br>
                    <%
                }
            %>
</div>      


    

    <script src="https://code.jquery.com/jquery-3.3.1.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js" integrity="sha384-wHAiFfRlMFy6i5SRaxvfOCifBUQy1xHdJ/yoi7FRNXMRBu5WHdZYu1hA6ZOblgut" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js" integrity="sha384-B0UglyR+jN6CkvvICOB2joaf5I4l3gm9GU6Hc1og6Ls7i6U/mkkaduKaBhlAXv9k" crossorigin="anonymous"></script>
    <!-- Bootstrap Date-Picker Plugin -->
    <link href="//netdna.bootstrapcdn.com/bootstrap/3.0.3/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://netdna.bootstrapcdn.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>
    

    
</body>
</html>