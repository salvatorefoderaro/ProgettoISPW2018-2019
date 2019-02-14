<%@ page import="it.uniroma2.ispw.fersa.rentingManagement.bean.ContractTextBean" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigException" %>
<%@ page import="it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigFileException" %>
<%@ page import="it.uniroma2.ispw.fersa.userProfileAndServices.NicknameNotFoundException" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="it.uniroma2.ispw.fersa.rentingManagement.exception.ConflictException" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="sessionBean" scope="session" class="it.uniroma2.ispw.fersa.rentingManagement.bean.SessionBean"></jsp:useBean>
<jsp:useBean id="sessionRequest" scope="session" class="it.uniroma2.ispw.fersa.rentingManagement.bean.SessionRequestBean"></jsp:useBean>


<%
    ContractTextBean contractTextBean = null;
    if (!sessionBean.isLogged()) {
        session.setAttribute("warningMessage", "Non sei loggato");
        response.sendRedirect(response.encodeRedirectURL("index.jsp"));
        return;
    } else if (sessionRequest.getRenterRequestHandlerSession() == null || !sessionRequest.getRenterRequestHandlerSession().isRequestSelected()) {
%>
<jsp:forward page="ContractRequests.jsp"></jsp:forward>
<%
    } else if(request.getParameter("signature") != null) {


        try {
            sessionRequest.getRenterRequestHandlerSession().signContract();

        } catch (SQLException | ClassNotFoundException | ConfigException | ConfigFileException | ConflictException e) {
            session.setAttribute("warningMessage", e.toString());
            response.sendRedirect(response.encodeRedirectURL("RenterPage.jsp"));
            return;
        }

        session.setAttribute("warningMessage", "Contratto firmato correttamente!");
        response.sendRedirect(response.encodeRedirectURL("RenterPage.jsp"));
        return;
    } else {

        try {
            sessionRequest.getRenterRequestHandlerSession().createContract();
            contractTextBean = sessionRequest.getRenterRequestHandlerSession().getContract();
        } catch (SQLException | ClassNotFoundException | ConfigException | ConfigFileException | NicknameNotFoundException e) {
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
<center>
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
        <div class="row align-content-center">
            <div class="col-sm">
                <form action="RenterPage.jsp" method="get" style="display: inline">
                    <button type="submit" class="btn btn-primary">Indietro</button>
                </form>
                <form action="AcceptRequest.jsp" name="sign" method="get" style="display: inline">
                    <button type="submit" id="signature" name="signature" class="btn btn-primary">Firma</button>
                </form>
            </div>
        </div>
    </div>
</center>
</body>
</html>
