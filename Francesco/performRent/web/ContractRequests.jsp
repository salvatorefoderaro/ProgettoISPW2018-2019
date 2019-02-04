<%@ page import="it.uniroma2.ispw.fersa.control.RequestResponseControl" %>
<%@ page import="it.uniroma2.ispw.fersa.rentingManagement.bean.RequestLabelBean" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigFileException" %>
<%@ page import="it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigException" %>
<%@ page import="java.time.format.DateTimeFormatter" %><%--
  Created by IntelliJ IDEA.
  User: francesco
  Date: 04/02/19
  Time: 17.27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="sessionBean" scope="session" class="it.uniroma2.ispw.fersa.rentingManagement.bean.SessionBean"></jsp:useBean>
<%
    if (!sessionBean.isLogged()) {
        session.setAttribute("warningMessage", "Non sei loggato");
        response.sendRedirect(response.encodeRedirectURL("index.jsp"));
        return;
    } else {
        sessionBean.setControl(new RequestResponseControl(sessionBean.getUsername()));
    }
%>
<html>
<head>
    <title>FERSA - Richieste contratti</title>
</head>
<center>
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
                <input name="requestId" type="text" value="<%=requestLabelBean.getContractRequestId()%>" hidden>
                <button type="submit" class="btn btn-primary">Visualizza</button>
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
<body>


<script type='text/javascript' src='resource/jquery-1.8.3.min.js'></script>
<link rel='stylesheet' href='resource/bootstrap.min.css'>
<link rel='stylesheet' href='resource/bootstrap-datepicker3.min.css'>
<script type='text/javascript' src='resource/bootstrap-datepicker.min.js'></script>
</body>
</html>
