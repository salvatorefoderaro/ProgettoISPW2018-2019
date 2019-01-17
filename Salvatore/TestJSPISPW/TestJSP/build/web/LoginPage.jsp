<%@page import="java.util.LinkedList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import= "Controller.Controller, Bean.SegnalazionePagamentoBean" %>

<jsp:useBean id="sessionBean" scope="session" class="Bean.BeanSession"/>

<% 
    
    if (request.getParameter("Locatore") != null) {
       
        sessionBean.setType("Locatore");
        sessionBean.setId(Integer.parseInt(request.getParameter("idUtente")));
        sessionBean.setUsername(request.getParameter("nomeUtente"));
        %>
        <jsp:forward page="pannelloUtente.jsp"/>

        <%
            
}
    
    if (request.getParameter("Locatario") != null) {
       
        sessionBean.setType("Locatario");
        sessionBean.setId(Integer.parseInt(request.getParameter("idUtente")));
        sessionBean.setUsername(request.getParameter("nomeUtente"));
        %>
        
        <jsp:forward page="pannelloUtente.jsp"/>
<%
}
    
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

        
    <title>Hello, world!</title>
             
  </head>
  
    
    <body>
      
      

        <nav class="navbar navbar-expand-lg navbar-light bg-light">
  <a class="navbar-brand" href="#">Navbar</a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>
  <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
    <div class="navbar-nav">
      <a class="nav-item nav-link active" href="#">Home <span class="sr-only">(current)</span></a>
      <a class="nav-item nav-link" href="#">Features</a>
      <a class="nav-item nav-link" href="#">Pricing</a>
      <a class="nav-item nav-link disabled" href="#" tabindex="-1" aria-disabled="true">Disabled</a>
    </div>
  </div>
</nav>
      
    	  <br>
<div class="container">
     <form action="LoginPage.jsp" method="POST">
         <div class="input-group mb-3">
  <div class="input-group-prepend">
    <span class="input-group-text" id="inputGroup-sizing-default">ID Utente</span>
  </div>
  <input type="text" name="idUtente" class="form-control" aria-label="Default" aria-describedby="inputGroup-sizing-default">
</div>
                  <div class="input-group mb-3">
  <div class="input-group-prepend">
    <span class="input-group-text" id="inputGroup-sizing-default">Nome Utente</span>
  </div>
  <input type="text" name="nomeUtente" class="form-control" aria-label="Default" aria-describedby="inputGroup-sizing-default">
</div>
         <center><button type="sumbit" name="Locatore" class="btn btn-primary btn-lg">Locatore</button>
             <button type="sumbit" name="Locatario" class="btn btn-secondary btn-lg">Locatario</button></center>
     </form>
</div>      

      
      <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    
    
    <script src="https://code.jquery.com/jquery-3.3.1.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js" integrity="sha384-wHAiFfRlMFy6i5SRaxvfOCifBUQy1xHdJ/yoi7FRNXMRBu5WHdZYu1hA6ZOblgut" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js" integrity="sha384-B0UglyR+jN6CkvvICOB2joaf5I4l3gm9GU6Hc1og6Ls7i6U/mkkaduKaBhlAXv9k" crossorigin="anonymous"></script>
    <!-- Bootstrap Date-Picker Plugin -->
    

</body>
</html>