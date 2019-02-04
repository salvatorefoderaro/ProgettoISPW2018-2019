<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>

<jsp:useBean id="sessionBean" scope="session" class="it.uniroma2.ispw.fersa.rentingManagement.bean.SessionBean"></jsp:useBean>




<html>
<head>

</head>
<body>
    <center>
        <%
            if (!sessionBean.isLogged()) {
                session.setAttribute("warningMessage", "Non sei loggato");
                response.sendRedirect(response.encodeRedirectURL("index.jsp"));
            }  else {
        %>
        <%="Bentornato " + sessionBean.getUsername()%>
        <%
            }
        %>
        <form action="ContractRequests.jsp" name="contractRequests" method="get">
            <button type="submit" id="contractRequests" name="contractRequest" class="btn btn-primary btn-lg">Richieste contratti</button>
        </form>
        <form action="RenterPage.jsp" name="contracts" method="get">
            <button type="submit" id="contracts" name="contracts" class="btn btn-primary btn-lg">Contratti</button>
        </form>
    </center>
    <script type='text/javascript' src='resource/jquery-1.8.3.min.js'></script>
    <link rel='stylesheet' href='resource/bootstrap.min.css'>
    <link rel='stylesheet' href='resource/bootstrap-datepicker3.min.css'>
    <script type='text/javascript' src='resource/bootstrap-datepicker.min.js'></script>
</body>
</html>
