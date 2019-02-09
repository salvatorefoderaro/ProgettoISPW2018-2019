<%@ page import="it.uniroma2.ispw.fersa.rentingManagement.entity.ContractRequestId" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigFileException" %>
<%@ page import="it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigException" %>
<%@ page import="it.uniroma2.ispw.fersa.rentingManagement.exception.ContractPeriodException" %>
<%@ page import="it.uniroma2.ispw.fersa.control.RentalHandlerRenterSession" %>
<%@ page import="it.uniroma2.ispw.fersa.rentingManagement.bean.RequestLabelBean" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="it.uniroma2.ispw.fersa.rentingManagement.bean.ContractLabelBean" %>
<%@ page import="it.uniroma2.ispw.fersa.rentingManagement.entity.ContractId" %><%--
  Created by IntelliJ IDEA.
  User: francesco
  Date: 09/02/19
  Time: 11.01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<jsp:useBean id="sessionBean" scope="session" class="it.uniroma2.ispw.fersa.rentingManagement.bean.SessionBean"></jsp:useBean>
    <%
    if (!sessionBean.isLogged()) {
        session.setAttribute("warningMessage", "Non sei loggato");
        response.sendRedirect(response.encodeRedirectURL("index.jsp"));
        return;
    } else if (request.getParameter("seeContractDetails") != null) { //TODO da modificare
        try {
            sessionBean.getControl().selectContract(new ContractId(Integer.parseInt(request.getParameter("contractId"))));
        %>
    <jsp:forward page="ContractInfo.jsp"></jsp:forward>
    <%
        } catch (ClassNotFoundException | SQLException | ConfigFileException | ConfigException | ContractPeriodException e) {
            session.setAttribute("warningMessage", e.toString());
            response.sendRedirect(response.encodeRedirectURL("Contracts.jsp"));
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
            <th align="center" scope="col">ID contratto</th>
            <th align="center" scope="col">Nickname locatario</th>
            <th align="center" scope="col">Data creazione</th>
            <th align="center" scope="col">Data stipula</th>
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
                for (ContractLabelBean contractLabelBean : sessionBean.getControl().getAllContracts()) {
        %>
        <tr align="center">
            <th align="center" scope="row"><%= contractLabelBean.getContractId()%></th>
            <td align="center" ><%= contractLabelBean.getNickname()%></td>
            <td align="center" ><%= contractLabelBean.getCreationDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))%></td>

            <%
                if (contractLabelBean.getStipulationDate() == null) {
            %>
            <td align="center"></td>
            <%
                } else {
            %>
            <td align="center" ><%= contractLabelBean.getStipulationDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))%></td>
            <%
                }
            %>
            <td align="center" ><%= contractLabelBean.getStartDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))%></td>
            <td align="center"><%= contractLabelBean.getEndDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))%></td>
            <td align="center"><%= contractLabelBean.getTotalPrice() + " €"%></td>
            <td align="center"><%= contractLabelBean.getState().getRenterState()%></td>
            <td align="center"><form action="Contracts.jsp" name="seeRequestInfo" method="post">
                <input name="contractId" id="contractId" type="text" value="<%=contractLabelBean.getContractId()%>" hidden>
                <button type="submit" name="seeContractDetails" id="seeConytractDetails" class="btn btn-primary">Visualizza</button>
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
