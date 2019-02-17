<%@ page import="java.sql.SQLException" %>
<%@ page import="it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigException" %>
<%@ page import="it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigFileException" %>
<%@ page import="it.uniroma2.ispw.fersa.rentingManagement.exception.AnsweredRequestException" %>
<%@ page import="it.uniroma2.ispw.fersa.rentingManagement.exception.CanceledRequestException" %>


<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:useBean id="sessionBean" scope="session" class="it.uniroma2.ispw.fersa.rentingManagement.bean.SessionBean"></jsp:useBean>
<jsp:useBean id="sessionRequest" scope="session" class="it.uniroma2.ispw.fersa.rentingManagement.bean.SessionRequestBean"></jsp:useBean>




<%
    if (!sessionBean.isLogged()) {
        session.setAttribute("warningMessage", "Non sei loggato");
        response.sendRedirect(response.encodeRedirectURL("index.jsp"));
        return;
    } else if (sessionRequest.getRenterRequestHandlerSession() == null || !sessionRequest.getRenterRequestHandlerSession().isRequestSelected()) {
%>
    <jsp:forward page="ContractRequests.jsp"></jsp:forward>
<%
    }else if(request.getParameter("decline") != null) {
        if (request.getParameter("declineMotivation").equals("")) {
%>
<center>
        <div class="alert alert-primary align-content-center" role="alert">
        Inserire una motivazione per il rifiuto!
        </div>
</center>
<%
        } else {
            try {
                sessionRequest.getRenterRequestHandlerSession().declineRequest(request.getParameter("declineMotivation"));
            } catch (SQLException | ClassNotFoundException | ConfigException | ConfigFileException | CanceledRequestException e) {
                session.setAttribute("warningMessage", e.toString());
                response.sendRedirect(response.encodeRedirectURL("ContractRequests.jsp"));
                return;
            }

            session.setAttribute("warningMessage", "Operazione completata con successo");
            response.sendRedirect(response.encodeRedirectURL("ContractRequests.jsp"));
        }
    }
%>

<html>
<head>
    <title>Rifiuto della richiesta</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <script src="libs/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
</head>
<body>
<center>
    <div class="container">
        <h2 class="font-weight-bold">Inserire la motivazione del rifiuto</h2>
        <form class="form-group" action="DeclineRequest.jsp" method="post">
            <textarea class="form-control" name="declineMotivation" id="declineMotivation" ></textarea>
            <button type="submit" id="decline" name="decline" class="btn btn-primary">Rifiuta richiesta</button>
        </form>
        <form class="form-group" action="ContractRequests.jsp" method="get">
            <button type="submit" id="undo" name="undo" class="btn btn-primary">Indietro</button>
        </form>
    </div>
</center>
</body>
</html>
