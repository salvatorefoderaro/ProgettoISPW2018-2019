<%@ page import="it.uniroma2.ispw.fersa.rentingManagement.bean.ContractTextBean" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigException" %>
<%@ page import="it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigFileException" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="sessionBean" scope="session" class="it.uniroma2.ispw.fersa.rentingManagement.bean.SessionBean"></jsp:useBean>
<jsp:useBean id="sessionContract" scope="session" class="it.uniroma2.ispw.fersa.rentingManagement.bean.SessionContractBean"></jsp:useBean>


<%
    ContractTextBean contractTextBean = null;
    if (!sessionBean.isLogged()) {
        session.setAttribute("warningMessage", "Non sei loggato");
        response.sendRedirect(response.encodeRedirectURL("index.jsp"));
        return;
    } else if (sessionContract.getRenterContractHandlerSession() == null || !sessionContract.getRenterContractHandlerSession().isContractSelected()){
%>
    <jsp:forward page="Contracts.jsp"></jsp:forward>
<%
    } else {

        try {
            contractTextBean = sessionContract.getRenterContractHandlerSession().getContract();
        } catch (SQLException | ClassNotFoundException | ConfigException | ConfigFileException e) {
            session.setAttribute("warningMessage", e.toString());
            response.sendRedirect(response.encodeRedirectURL("index.jsp"));
            return;
        }
    }
%>


<html>
<head>
    <title>Firma del contratto</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <script src="libs/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
</head>
<body>
<div class="container">
    <h2 class="font-weight-bold text-center"><%=contractTextBean.getContractName()%></h2>
    <p class="text-justify"><%=contractTextBean.getIntro()%></p>
    <p class="font-weight-bold text-center">Durata</p>
    <p class="text-justify"><%=contractTextBean.getDuration()%></p>
    <%
        if (contractTextBean.getTransitory() != null) {
    %>
    <p class="font-weight-bold text-center">Natura transitoria</p>
    <p class="text-justify"><%=contractTextBean.getTransitory()%></p>
    <%
        }
    %>
    <p class="font-weight-bold text-center">Canone</p>
    <p class="text-justify"><%=contractTextBean.getPayment()%></p>
    <p class="font-weight-bold text-center">Deposito cauzionale</p>
    <p class="text-justify"><%=contractTextBean.getDeposit()%></p>
    <p class="font-weight-bold text-center">Pagamento, risoluzione e prelazione</p>
    <p class="text-justify"><%=contractTextBean.getResolution()%></p>
    <p class="text-center font-weight-bold">Uso</p>
    <p class="text-justify"><%=contractTextBean.getUse()%></p>
    <p class="font-weight-bold text-center">Varie</p>
    <p class="text-justify"><%=contractTextBean.getVarious()%></p>
    <center>
        <form class="align-content-center" action="ContractInfo.jsp" name="sign" method="get">
            <button type="submit" id="undo" name="undo" class="btn btn-primary btn-lg align-content-center">Indietro</button>
        </form>
    </center>
</div>
</body>
</html>
