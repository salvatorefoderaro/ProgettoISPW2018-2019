
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.sql.SQLException"%>
<%@ page import="java.util.LinkedList"%>
<%@ page import="java.util.List"%>
<%@ page import="Exceptions.transactionError" %>
<%@ page import="Exceptions.dbConnection" %>
<%@ page import="Exceptions.emptyResult" %>
<%@ page import="Control.controller" %>
<%@ page import="Bean.rentableBean" %>

<jsp:useBean id="sessionBean" scope="session" class="Bean.renterBean"/>

<%

    System.out.println(sessionBean.getController());
    if (sessionBean.getNickname() == null){

        response.sendRedirect("LoginPage.jsp?error=makeLogin");

    }

    controller parentController = sessionBean.getController();

if (request.getParameter("importContract") != null) {

%>

<jsp:forward page='importContract.jsp'>
    <jsp:param name='rentableID' value='<%= request.getParameter("rentableID") %>' />
    <jsp:param name='rentableName' value='<%= request.getParameter("rentableName2") %>' />
    <jsp:param name='rentableDescription' value='<%= request.getParameter("rentableDescription") %>' />
    <jsp:param name='rentableImage' value='<%= request.getParameter("rentableImage") %>' />
    <jsp:param name='rentableType' value='<%= request.getParameter("rentableType") %>' />
</jsp:forward>

<%
    } %>

<html>
<head>
    <script type='text/javascript' src='${pageContext.request.contextPath}/src/jquery-1.8.3.min.js'></script>
    <link rel='stylesheet' href='${pageContext.request.contextPath}/src/bootstrap.min.css'>
    <link rel='stylesheet' href='${pageContext.request.contextPath}/src/bootstrap-datepicker3.min.css'>
    <script type='text/javascript' src='${pageContext.request.contextPath}/src/bootstrap-datepicker.min.js'></script>
</head>
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

        if (session.getAttribute("transactionError") != null) { %>


    <div class="alert alert-warning">
        <strong>Errore!</strong> Errore durante la transazione.
    </div>


    <% session.setAttribute("emptyResult", null);
    }
        List<rentableBean> test = null;
        try {
            test = parentController.getRentableFromUser(sessionBean);
        } catch (SQLException e) {
            response.sendRedirect("LoginPage.jsp?error=databaseConnection");
        } catch (Exceptions.emptyResult emptyResult) {
            %>
            <jsp:forward page="LoginPage.jsp">
                <jsp:param name="error" value="emptyResultRentable" />
            </jsp:forward> <%
        }

        for (rentableBean temp : test) {
    %>
    <form action="importContract.jsp" name="myform" method="POST"><div class="row justify-content-md-center ">
        <div class="col-md">
            <b></b> <img style="max-width:50%" src="${pageContext.request.contextPath}/src/test1.jpeg">
        </div>

        <div class="col-md">
            <b>Numero reclamo:</b> Nome: <%= temp.getName() %> | Tipo <%= temp.getType() %>
        </div>

        <div class="col-md">

            <button type="submit" name="importContract" class="btn btn-outline-secondary">Importa contratto</button>

            <input type="hidden" id="custId" name="rentableID" value="<%= temp.getID() %>">
            <input type="hidden" id="custId" name="rentableName" value="<%= temp.getName() %>">
            <input type="hidden" id="custId" name="rentableDescription" value="<%= temp.getDescription() %>">
            <input type="hidden" id="custId" name="rentableImage" value="<%= temp.getImage() %>">
            <input type="hidden" id="custId" name="rentableType" value="<%= temp.getType() %>">




        </div>

    </div>

    </form>
    <% } %>
    <br>
</div>



</body>
</html></head>
<body>

</body>
</html>
