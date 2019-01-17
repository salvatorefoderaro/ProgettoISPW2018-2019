<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<jsp:useBean id="loginBean" scope="session" class="loginexample.LoginBean"/>

<% if (loginBean.getUsername() == ""){ %>
    
<jsp:forward page="LoginPage.jsp"/>



<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>OK</title>
</head>
<body>
    
    <%= loginBean.getUsername() %>
    <br>
        <%= loginBean.getPassword()%>
        <br>
            <%= loginBean.getNome() %>
            <br>
                <%= loginBean.getCognome() %>
                <br>
                
                <br>
                
                <% for (String strTemp : arrData){ %>

                <br>
                Wee, sono:  <%= strTemp %>
                <br>
<% } %>

Login effettuato
</body>
</html>