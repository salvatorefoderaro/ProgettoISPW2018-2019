<%@ page import="it.uniroma2.ispw.fersa.rentingManagement.bean.PropertyBean" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigFileException" %>
<%@ page import="it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigException" %>
<%@ page import="javax.imageio.ImageIO" %>
<%@ page import="java.awt.image.BufferedImage" %>
<%@ page import="java.io.ByteArrayOutputStream" %>
<%@ page import="it.uniroma2.ispw.fersa.rentingManagement.bean.ContractRequestInfoBean" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="it.uniroma2.ispw.fersa.rentingManagement.bean.ServiceBean" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="sessionBean" scope="session" class="it.uniroma2.ispw.fersa.rentingManagement.bean.SessionBean"></jsp:useBean>
<jsp:useBean id="sessionRequest" scope="session" class="it.uniroma2.ispw.fersa.rentingManagement.bean.SessionRequestBean"></jsp:useBean>


<%
    if (!sessionBean.isLogged()) {
        session.setAttribute("warningMessage", "Non sei loggato");
        response.sendRedirect(response.encodeRedirectURL("index.jsp"));
        return;
    } else if (sessionRequest.getRenterRequestHandlerSession() == null) {
%>
    <jsp:forward page="ContractRequests.jsp"></jsp:forward>
<%
    }
    PropertyBean propertyBean;
    ContractRequestInfoBean contractRequestInfoBean;
    try {

        propertyBean = sessionRequest.getRenterRequestHandlerSession().getPropertyInfo();
        contractRequestInfoBean = sessionRequest.getRenterRequestHandlerSession().getRequestInfo();
    } catch (SQLException | ClassNotFoundException | ConfigFileException | ConfigException e) {
        session.setAttribute("warningMessage", e.toString());
        response.sendRedirect(response.encodeRedirectURL("RenterPage.jsp"));
        return;
    }
%>




<html>
<head>
    <title>Informazioni contratto</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <script src="libs/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
</head>
<body>
<center>
    <h2 class="font-weight-bold">Informazioni immobile</h2>
    <div class="container">
        <div class="row justify-content-center" style="border: black">
            <div class="col-md ">
                <%
                    BufferedImage bImage = propertyBean.getImage();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ImageIO.write( bImage, "jpg", baos );
                    baos.flush();
                    byte[] imageInByteArray = baos.toByteArray();
                    baos.close();
                    String b64 = javax.xml.bind.DatatypeConverter.printBase64Binary(imageInByteArray);
                %>
                <img src="data:image/jpg;base64, <%=b64%>" class="img-fluid"/>
            </div>
            <div class="col-md">
                <p class="text-left"><%=propertyBean.getTitle()%></p>
                <p class="text-left"><%= "Indirizzo: " + propertyBean.getAptAddress()%></p>
                <p class="text-left"><%="Tipologia: " + propertyBean.getType().toString()%></p>
                <p class="text-left"><%="Descrizione: " + propertyBean.getRentableDescription()%></p>
            </div>
        </div>
        <div class="row justify-content-center">
            <h2 class="font-weight-bold">Informazioni richiesta</h2>
        </div>
        <div class="row justify-content-center">
            <div class="col-md" >

                <p class="text-left"><%="Tipologia contratto: " + contractRequestInfoBean.getContractName()%></p>
                <p class="text-left"><%= "Data di inzio: " + contractRequestInfoBean.getStartDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))%></p>
                <p class="text-left"> <%= "Data di conclusione: " + contractRequestInfoBean.getEndDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))%></p>
                <p class="text-left"> <%="Prezzo: " + contractRequestInfoBean.getTotal() + " €"%></p>
                <p class="text-left"><%="Deposito cauzionale: " + contractRequestInfoBean.getDeposit() + " €"%></p>

            <%
                if (contractRequestInfoBean.getServices().size() == 0) {
                    %>
            <p class="text-left"><%="Servizi: nessun servizio"%></p>
                    <%
                } else {
            %>

            <p class="text-left"><%="Servizi: "%></p>

            <%
                    for (ServiceBean serviceBean : contractRequestInfoBean.getServices()) {
                        %>
            <ul class="text-left"><%="-" + serviceBean.getName() + ": " + serviceBean.getPrice() + " €"%></ul>
                        <%
                    }
                }
            %>
            </div>
        </div>
        <div class="row align-content-center">
        <%
            switch(contractRequestInfoBean.getState()) {
                case INSERTED:
        %>
            <div class="col-sm">
                <form action="ContractRequests.jsp" method="get" style="display: inline">
                    <button type="submit" class="btn btn-primary">Indietro</button>
                </form>
                <form action="AcceptRequest.jsp" method="get" style="display: inline">
                    <button type="submit" class="btn btn-primary">Accetta</button>
                </form>
                <form action="DeclineRequest.jsp" method="get" style="display: inline">
                    <button type="submit" class="btn btn-primary">Rifiuta</button>
                </form>
            </div>

        <%
                    break;
                case REFUSUED:
        %>
                    <div class="col-md align-content-center">
                        <p class="text-center font-weight-bold">Motivo del rifiuto</p>
                        <p class="text-justify"><%=contractRequestInfoBean.getDeclineMotivation()%></p>
                        <form action="ContractRequests.jsp" method="get">
                            <button type="submit" class="btn btn-primary">Indietro</button>
                        </form>
                    </div>
        <%
                    break;
                case CANCELED:
                case APPROVED:
        %>
            <div class="col-md align-content-center">
                <form action="ContractRequests.jsp" method="get">
                    <button type="submit" class="btn btn-primary">Indietro</button>
                </form>
            </div>
        <%
                break;
            }
        %>
        </div>
    </div>
</center>
</body>
</html>
