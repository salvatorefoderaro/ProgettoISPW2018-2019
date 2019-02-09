<%@ page import="it.uniroma2.ispw.fersa.rentingManagement.bean.RequestLabelBean" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigFileException" %>
<%@ page import="it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigException" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="it.uniroma2.ispw.fersa.rentingManagement.entity.ContractRequestId" %>
<%@ page import="it.uniroma2.ispw.fersa.control.RentalHandlerRenterSession" %>
<%@ page import="it.uniroma2.ispw.fersa.rentingManagement.exception.ContractPeriodException" %>


<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="sessionBean" scope="session" class="it.uniroma2.ispw.fersa.rentingManagement.bean.SessionBean"></jsp:useBean>
<%
    if (!sessionBean.isLogged()) {
        session.setAttribute("warningMessage", "Non sei loggato");
        response.sendRedirect(response.encodeRedirectURL("index.jsp"));
        return;
    } else if (request.getParameter("seeRequestDetails") != null) {
        try {
            sessionBean.getControl().selectRequest(new ContractRequestId(Integer.parseInt(request.getParameter("requestId"))));
        %>
        <jsp:forward page="ContractRequestInfo.jsp"></jsp:forward>
        <%
        } catch (ClassNotFoundException | SQLException | ConfigFileException | ConfigException | ContractPeriodException e) {
            session.setAttribute("warningMessage", e.toString());
            response.sendRedirect(response.encodeRedirectURL("ContractRequests.jsp"));
            return;
        }
    } else {
        sessionBean.setControl(new RentalHandlerRenterSession(sessionBean.getUsername()));
    }
        %>
<html>
<head>
    <title>FERSA - Richieste contratti</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <script src="libs/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
</head>
<body>

<center>
    <%
    if (session.getAttribute("warningMessage") != null) { %>
    <div class="alert alert-primary" role="alert">

        <%= session.getAttribute("warningMessage")%>

    </div>
    <%
        }
    %>
    <table class="table">
        <thead>
        <tr align="center">
            <th align="center" scope="col">ID richiesta</th>
            <th align="center" scope="col">Nickname locatario</th>
            <th align="center" scope="col">Data creazione</th>
            <th align="center" scope="col">Data inizio affitto</th>
            <th align="center" scope="col">Data fine affitto</th>
            <th align="center" scope="col">Prezzo</th>
            <th align="center" scope="col">Stato della richiesta</th>
            <th align="center" scope="col"></th>
        </tr>
        </thead>
        <tbody>
        <%
            try {
                for (RequestLabelBean requestLabelBean : sessionBean.getControl().getAllContractRequest()) {
        %>
        <tr align="center">
            <th align="center" scope="row"><%= requestLabelBean.getContractRequestId()%></th>
            <td align="center" ><%= requestLabelBean.getTenantNickname()%></td>
            <td align="center" ><%= requestLabelBean.getCreationDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))%></td>
            <td align="center" ><%= requestLabelBean.getStartDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))%></td>
            <td align="center"><%= requestLabelBean.getEndDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))%></td>
            <td align="center"><%= requestLabelBean.getTotalPrice() + " €"%></td>
            <td align="center"><%= requestLabelBean.getState().getRenterState()%></td>
            <td align="center"><form action="ContractRequests.jsp" name="seeRequestInfo" method="post">
                <input name="requestId" id="requestId" type="text" value="<%=requestLabelBean.getContractRequestId()%>" hidden>
                <button type="submit" name="seeRequestDetails" id="seeRequestDetails" class="btn btn-primary">Visualizza</button>
            </form></td>
        </tr>
        <%
                }
            } catch (SQLException | ClassNotFoundException | ConfigFileException | ConfigException e) {
                session.setAttribute("warningMessage", e.toString());
                response.sendRedirect(response.encodeRedirectURL("index.jsp"));
                return;
            }
        %>
        </tbody>
    </table>
</center>
</body>
</html>
