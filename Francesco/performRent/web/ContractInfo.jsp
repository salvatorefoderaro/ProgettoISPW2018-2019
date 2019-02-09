<%@ page import="it.uniroma2.ispw.fersa.rentingManagement.bean.PropertyBean" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigFileException" %>
<%@ page import="it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigException" %>
<%@ page import="java.awt.*" %>
<%@ page import="javax.imageio.ImageIO" %>
<%@ page import="java.awt.image.BufferedImage" %>
<%@ page import="java.io.ByteArrayOutputStream" %>
<%@ page import="it.uniroma2.ispw.fersa.rentingManagement.bean.ContractRequestInfoBean" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="it.uniroma2.ispw.fersa.rentingManagement.bean.ServiceBean" %>
<%@ page import="it.uniroma2.ispw.fersa.rentingManagement.bean.ContractInfoBean" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="sessionBean" scope="session" class="it.uniroma2.ispw.fersa.rentingManagement.bean.SessionBean"></jsp:useBean>

<%
    if (!sessionBean.isLogged()) {
        session.setAttribute("warningMessage", "Non sei loggato");
        response.sendRedirect(response.encodeRedirectURL("index.jsp"));
        return;
    }
    PropertyBean propertyBean;
    ContractInfoBean contractInfoBean;
    try {

        propertyBean = sessionBean.getControl().getPropertyInfoContract();
        contractInfoBean = sessionBean.getControl().getContractInfo();
    } catch (SQLException | ClassNotFoundException | ConfigFileException | ConfigException e) {
        session.setAttribute("warningMessage", e.toString());
        response.sendRedirect(response.encodeRedirectURL("index.jsp"));
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
            <h2 class="font-weight-bold">Informazioni contratto</h2>
        </div>
        <div class="row justify-content-center">
            <div class="col-md" >

                <p class="text-left"><%="Tipologia contratto: " + contractInfoBean.getContractName()%></p>
                <p class="text-left"><%= "Data di creazione: " + contractInfoBean.getCreationDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))%></p>
                <p class="text-left">Data di stipulazione:
                    <%
                    if (contractInfoBean.getStipulationDate() != null) {
                    %>
                    <%=contractInfoBean.getStipulationDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))%>

                    <%
                    }
                    %>
                </p>
                <p class="text-left"><%= "Data di inzio: " + contractInfoBean.getStartDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))%></p>
                <p class="text-left"> <%= "Data di conclusione: " + contractInfoBean.getEndDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))%></p>
                <p class="text-left"> <%="Prezzo: " + contractInfoBean.getTotal() + " €"%></p>
                <p class="text-left"><%="Deposito cauzionale: " + contractInfoBean.getDeposit() + " €"%></p>

                <%
                    if (contractInfoBean.getServices().size() == 0) {
                %>
                <p class="text-left"><%="Servizi: nessun servizio"%></p>
                <%
                } else {
                %>

                <p class="text-left"><%="Servizi: "%></p>

                <%
                    for (ServiceBean serviceBean : contractInfoBean.getServices()) {
                %>
                <ul class="text-left"><%="-" + serviceBean.getName() + ": " + serviceBean.getPrice() + " €"%></ul>
                <%
                        }
                    }
                %>
            </div>
        </div>
        <div class="row justify-content-center">
            <h2 class="font-weight-bold">Informazioni locatario</h2>
        </div>
        <div class="row justify-content-center">
            <div class="col-md" >

                <p class="text-left"><%="Nickname: " + contractInfoBean.getNickname()%></p>
                <p class="text-left"><%= "Nome: " + contractInfoBean.getName()%></p>
                <p class="text-left"><%= "Cognome: " + contractInfoBean.getSurname()%></p>
                <p class="text-left"><%= "Codice Fiscale: " + contractInfoBean.getCF()%></p>

            </div>
        </div>
        <div class="row align-content-center">
            <div class="col-sm">
                <form action="Contracts.jsp" method="get" style="display: inline">
                    <button type="submit" class="btn btn-primary">Indietro</button>
                </form>
                <form action="ContractText.jsp" method="get" style="display: inline">
                    <button type="submit" class="btn btn-primary">Visualizza Contratto</button>
                </form>
            </div>
        </div>
    </div>
</center>
</body>
</html>