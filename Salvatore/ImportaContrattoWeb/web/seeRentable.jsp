
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.sql.SQLException"%>
<%@ page import="java.util.LinkedList"%>
<%@ page import="java.util.List"%>
<%@ page import="Exceptions.transactionError" %>
<%@ page import="Exceptions.dbConnection" %>
<%@ page import="Exceptions.emptyResult" %>
<%@ page import="Control.controller" %>
<%@ page import="Bean.rentableBean" %>
<%@ page import="Entity.TypeOfRentable" %>

<jsp:useBean id="sessionBean" scope="session" class="Bean.userBean"/>
<jsp:useBean id="toRent" scope="session" class="Bean.rentableBean" />


<%

    if (sessionBean.getNickname() == null){

        response.sendRedirect("index.jsp?error=makeLogin");
        return;

    }

    controller parentController = sessionBean.getController();

if (request.getParameter("importContract") != null) {

    toRent.setID(Integer.parseInt(request.getParameter("rentableID")));
    toRent.setName(request.getParameter("rentableName"));
    toRent.setDescription(request.getParameter("rentableDescription"));
    toRent.setImage(request.getParameter("rentableImage"));
    toRent.setType(TypeOfRentable.makeType(request.getParameter("rentableType")));
    toRent.setAptID(Integer.parseInt(request.getParameter("aptID")));
    toRent.setRoomID(Integer.parseInt(request.getParameter("roomID")));
    toRent.setBedID(Integer.parseInt(request.getParameter("bedID")));
%>

        <jsp:forward page="importContract.jsp"/>

<%    } %>

<html>
<head>
    <script type='text/javascript' src='${pageContext.request.contextPath}/Resource/jquery-1.8.3.min.js'></script>
    <link rel='stylesheet' href='${pageContext.request.contextPath}/Resource/bootstrap.min.css'>
    <link rel='stylesheet' href='${pageContext.request.contextPath}/Resource/bootstrap-datepicker3.min.css'>
    <script type='text/javascript' src='${pageContext.request.contextPath}/Resource/bootstrap-datepicker.min.js'></script>
</head>
<body>



<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <a class="navbar-brand" href="#">FERSA</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
        <div class="navbar-nav">
            <a class="nav-item nav-link " href="index.jsp"> Login</a>
            <a class="nav-item nav-link active" href="seeRentable.jsp">Pannello utente  <span class="sr-only">(current)</span></a>
            <a class="nav-item nav-link" href="importContract.jsp">Importa contratto </a> </div>
        </div>
    </div>
</nav>

<br>

<div class="container">
<center>
    <%

        if (request.getParameter("success") != null) { %>


    <div class="alert alert-success">
        <strong>Ok!</strong> Contratto importato correttamente nel sistema.
    </div>

    <% }

        if (session.getAttribute("transactionError") != null) { %>


    <div class="alert alert-warning">
        <strong>Errore!</strong> Errore durante la transazione.
    </div>


    <% session.setAttribute("emptyResult", null);
    }
        List<rentableBean> test = null;
        try {
            test = sessionBean.getController().getRentableFromUser(sessionBean);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("index.jsp?error=databaseConnection");
            return;
        } catch (Exceptions.emptyResult emptyResult) {
            %>
            <jsp:forward page="index.jsp">
                <jsp:param name="error" value="emptyResultRentable" />
            </jsp:forward> <%
        }

        for (rentableBean temp : test) {
    %>
    <form action="seeRentable.jsp" name="myform" method="POST"><div class="row justify-content-md-center ">
        <div class="col-md">
            <b></b> <img style="max-width:50%" src="data:image/jpeg;base64,<%=temp.getImage()%>">
        </div>

        <div class="col-md">
            <b>Numero reclamo:</b> Nome: <%= temp.getName() %> | Tipo <%= temp.getType().getType() %>
        </div>

        <div class="col-md">

            <button type="submit" name="importContract" class="btn btn-outline-secondary">Importa contratto</button>

            <input type="hidden" id="custId" name="rentableID" value="<%= temp.getID() %>">
            <input type="hidden" id="custId" name="aptID" value="<%= temp.getAptID() %>">
            <input type="hidden" id="custId" name="roomID" value="<%= temp.getRoomID() %>">
            <input type="hidden" id="custId" name="bedID" value="<%= temp.getBedID() %>">
            <input type="hidden" id="custId" name="rentableName" value="<%= temp.getName() %>">
            <input type="hidden" id="custId" name="rentableDescription" value="<%= temp.getDescription() %>">
            <input type="hidden" id="custId" name="rentableImage" value="<%= temp.getImage() %>">
            <input type="hidden" id="custId" name="rentableType" value="<%= temp.getType().getType() %>">

        </div>

    </div>

    </form>
    <% } %>
</center>
</div>



</body>
</html></head>
<body>

</body>
</html>
