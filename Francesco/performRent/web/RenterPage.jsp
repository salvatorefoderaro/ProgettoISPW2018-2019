<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>

<jsp:useBean id="sessionBean" scope="session" class="it.uniroma2.ispw.fersa.rentingManagement.bean.SessionBean"></jsp:useBean>




<html>
<head>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <script src="libs/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
</head>
<body>
    <center>
        <% if (session.getAttribute("warningMessage") != null) { %>
        <div class="alert alert-primary" role="alert">

            <%= session.getAttribute("warningMessage")%>

        </div>
        <%
                session.setAttribute("warningMessage" , null);
            }
            if (!sessionBean.isLogged()) {
                session.setAttribute("warningMessage", "Non sei loggato");
                response.sendRedirect(response.encodeRedirectURL("index.jsp"));
            }  else {
        %>
        <h2><%="Bentornato " + sessionBean.getUsername()%></h2>
        <%
            }
        %>
        <form action="ContractRequests.jsp" name="contractRequests" method="get">
            <button type="submit" id="contractRequests" name="contractRequest" class="btn btn-primary btn-lg">Richieste contratti</button>
        </form>
        <form action="Contracts.jsp" name="contracts" method="get">
            <button type="submit" id="contracts" name="contracts" class="btn btn-primary btn-lg">Contratti</button>
        </form>
    </center>
</body>
</html>
