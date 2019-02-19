<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.sql.SQLException"%>
<%@ page import="it.uniroma2.ispw.fersa.Bean.contractBean" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="it.uniroma2.ispw.fersa.Bean.userBean" %>
<%@ page import="it.uniroma2.ispw.fersa.Entity.Enum.typeOfMessage" %>
<%@ page import="it.uniroma2.ispw.fersa.Entity.Enum.typeOfContract" %>
<%@ page import="it.uniroma2.ispw.fersa.Entity.Enum.titleOfWindows" %>
<%@ page import="java.io.*" %>

<jsp:useBean id="sessionBean" scope="session" class="it.uniroma2.ispw.fersa.Bean.userBean"/>
<jsp:useBean id="toRent" scope="session" class="it.uniroma2.ispw.fersa.Bean.rentableBean" />

<%

    if ((sessionBean.getNickname() == null) || (toRent.getName() == null)){
        session.setAttribute("warningMessage", "Effettua l'accesso prima di continuare!");
        String destination ="index.jsp";
        response.sendRedirect(response.encodeRedirectURL(destination));
        return;
    }

    typeOfContract selectedContractType = typeOfContract.typeFromString(request.getParameter("contractType"));

    if (request.getParameter("trueImportContract") != null){

    toRent.setTenantnNickname(request.getParameter("tenantNickname"));
    toRent.setEndDateRequest(request.getParameter("endDateRequest"));
    toRent.setStartDateRequest(request.getParameter("startDateRequest"));

    LocalDate localStartDate = LocalDate.parse ( request.getParameter("startDateRequest") , DateTimeFormatter.ofPattern ("yyyy-MM-dd" ) );
    LocalDate localEndDate = LocalDate.parse ( request.getParameter("endDateRequest") , DateTimeFormatter.ofPattern ("yyyy-MM-dd" ) );

    if (localEndDate.isBefore(localStartDate)){
        session.setAttribute("infoMessage", typeOfMessage.WRONGDATEINTERVAL.getString());
        String destination ="importContract.jsp";
        response.sendRedirect(response.encodeRedirectURL(destination));
        return;
    }
    if (localEndDate.isBefore(localStartDate.plusMonths(selectedContractType.minDuration))){
        session.setAttribute("infoMessage", "Per la tipologia di contratto scelta, l'intervallo minimo è di " + selectedContractType.minDuration + " mesi!");
        String destination ="importContract.jsp";
        response.sendRedirect(response.encodeRedirectURL(destination));
        return;
    }

    if (localEndDate.isAfter(localStartDate.plusMonths(selectedContractType.maxDuration))){
        session.setAttribute("infoMessage", "Per la tipologia di contratto scelta, l'intervallo massimo è di " + selectedContractType.maxDuration + " mesi!");
        String destination ="importContract.jsp";
        response.sendRedirect(response.encodeRedirectURL(destination));
        return;
    }

        userBean tenant = null;
    try {
        tenant = sessionBean.getController().checkTenantNickname(toRent);
    } catch (SQLException e) {
        e.printStackTrace();
        session.setAttribute("waningMessage", typeOfMessage.DBERROR.getString());
        String destination ="importContract.jsp";
        response.sendRedirect(response.encodeRedirectURL(destination));
        return;
    } catch (it.uniroma2.ispw.fersa.Exceptions.emptyResult emptyResult) {
        session.setAttribute("infoMessage", "Nessun utente associato al nickname indicato!");
        String destination ="importContract.jsp";
        response.sendRedirect(response.encodeRedirectURL(destination));
        return;
    } catch (it.uniroma2.ispw.fersa.Exceptions.dbConfigMissing dbConfigMissing) {
        session.setAttribute("infoMessage1", "Nessun utente associato al nickname indicato!");
        String destination ="importContract.jsp";
        response.sendRedirect(response.encodeRedirectURL(destination));
        return;
    }

        try {
            sessionBean.getController().setNewAvailabilityCalendar(toRent);
        } catch (it.uniroma2.ispw.fersa.Exceptions.transactionError transactionError) {
            session.setAttribute("infoMessage", typeOfMessage.TRANSATIONERROR.getString());
            String destination ="importContract.jsp";
            response.sendRedirect(response.encodeRedirectURL(destination));
            return;
        } catch (SQLException e) {
            e.printStackTrace();
            session.setAttribute("warningMessage", typeOfMessage.DBERROR.getString());
            String destination ="index.jsp";
            response.sendRedirect(response.encodeRedirectURL(destination));
            return;
        } catch (it.uniroma2.ispw.fersa.Exceptions.emptyResult emptyResult) {
            session.setAttribute("infoMessage", "La risorsa non è disponibile per le date indicate!");
            String destination ="importContract.jsp";
            response.sendRedirect(response.encodeRedirectURL(destination));
            return;
        } catch (it.uniroma2.ispw.fersa.Exceptions.dbConfigMissing dbConfigMissing) {
            session.setAttribute("warningMessage", typeOfMessage.DBCONFIGERROR.getString());
            String destination ="index.jsp";
            response.sendRedirect(response.encodeRedirectURL(destination));
            return;
        }

        toRent.setTenantnNickname(request.getParameter("tenantNickname"));
        toRent.setEndDateRequest(request.getParameter("endDateRequest"));
        toRent.setStartDateRequest(request.getParameter("startDateRequest"));

        contractBean contract = null;
    if (tenant != null) {
        contract = new contractBean(
                0,
                toRent.getID(),
                false,
                LocalDate.parse(request.getParameter("startDateRequest"), DateTimeFormatter.ofPattern ("yyyy-MM-dd" )),
                LocalDate.parse(request.getParameter("endDateRequest"), DateTimeFormatter.ofPattern ("yyyy-MM-dd" )),
                null,
                request.getParameter("tenantNickname"),
                sessionBean.getNickname(),
                request.getParameter("locatoreNome"),
                request.getParameter("locatarioNome"),
                request.getParameter("locatarioCF"),
                request.getParameter("locatoreCF"),
                request.getParameter("locatoreIndirizzo"),
                request.getParameter("locatarioIndirizzo"),
                request.getParameter("locatoreCognome"),
                request.getParameter("locatarioCognome"),
                0,
                Integer.parseInt(request.getParameter("contractPrezzo")),
                0,
                false,
                null,
                toRent.getType(),
                Integer.parseInt(request.getParameter("contractDeposito")),
                selectedContractType
        );
        }

        try {
            sessionBean.getController().createContract(contract);
        } catch (SQLException e) {
            e.printStackTrace();
            session.setAttribute("warningMessage", typeOfMessage.DBERROR.getString());
            String destination ="index.jsp";
            response.sendRedirect(response.encodeRedirectURL(destination));
            return;
        }
        catch (it.uniroma2.ispw.fersa.Exceptions.transactionError transactionError) {
            session.setAttribute("infoMessage", typeOfMessage.TRANSATIONERROR.getString());
            String destination ="importContract.jsp";
            response.sendRedirect(response.encodeRedirectURL(destination));
            return;
        } catch (it.uniroma2.ispw.fersa.Exceptions.dbConfigMissing dbConfigMissing) {
            session.setAttribute("warningMessage", typeOfMessage.DBCONFIGERROR.getString());
            String destination ="index.jsp";
            response.sendRedirect(response.encodeRedirectURL(destination));
            return;
        }

        session.setAttribute("successMessage", typeOfMessage.SUCCESSOPERATION.getString());
        String destination ="seeRentable.jsp";
        response.sendRedirect(response.encodeRedirectURL(destination));
        return;
    } %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<html>
<head>
    <title><%=titleOfWindows.IMPORTCONTRACT.getString() %></title>

    <script type='text/javascript' src='${pageContext.request.contextPath}/Resource/jquery-1.8.3.min.js'></script>
    <link rel='stylesheet' href='${pageContext.request.contextPath}/Resource/bootstrap.min.css'>
    <link rel='stylesheet' href='${pageContext.request.contextPath}/Resource/bootstrap-datepicker3.min.css'>
    <script type='text/javascript' src='${pageContext.request.contextPath}/Resource/bootstrap-datepicker.min.js'></script>

    <script type='text/javascript'>
        $(function(){
            $('.input-group.date').datepicker({
                format: "yyyy-mm-dd",
                calendarWeeks: true,
                autoclose: true
            });
        });
    </script>

</head>
<body>


<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <a class="navbar-brand" href="#">FERSA</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
        <div class="navbar-nav">
            <a class="nav-item nav-link" href="seeRentable.jsp">Pannello utente</a>
            <a class="nav-item nav-link" href="index.jsp"> Login</a>
            <a class="nav-item nav-link active" href="#">Importa contratto <span class="sr-only">(current)</span></a>
        </div>
    </div>
</nav>

<br>

<div class="container .text-center" style="margin: 0px auto;">
    <center>

        <%
            if (session.getAttribute("successMessage") != null) { %>

        <div class="alert alert-success">
            <strong>Ok!</strong> <%= session.getAttribute("successMessage") %>
        </div>


        <% session.setAttribute("successMessage", null);
        }

            if (session.getAttribute("infoMessage") != null) {  %>


        <div class="alert alert-info">
            <strong>Attenzione!</strong> <%= session.getAttribute("infoMessage") %>
        </div>


        <% session.setAttribute("infoMessage", null);
        }

            if (session.getAttribute("warningMessage") != null) {  %>


        <div class="alert alert-warning">
            <strong>Errore!</strong> <%= session.getAttribute("warningMessage") %>
        </div>


        <% session.setAttribute("warningMessage", null);
        } %>


<form action='importContract.jsp' method='POST'>

    <div class="row">
        <div class="col-5 .text-center">
            <img class="img-rounded img-responsive" style="max-width:80%" src="data:image/jpeg;base64,<%=toRent.getImage()%>">
        </div>
        <div class="col-7 .text-center">
<%= toRent.getDescription() %>        </div>
    </div>

    <br>

    <div class="row">
        <div class="col .text-center">
            <div class="input-group date">
                <input name="startDateRequest" placeholder="Data inizio contratto.." type="text" class="form-control" required><span class="input-group-addon"><i class="glyphicon glyphicon-calendar" ></i></span>
            </div>    </div>
        <div class="col .text-center">
            <div class="input-group date">
                <input name="endDateRequest" placeholder="Data fine contratto.." type="text" class="form-control" required><span class="input-group-addon"><i class="glyphicon glyphicon-calendar" ></i></span>
            </div>    </div>
        <div class="col .text-center">
                <input placeholder="Nickname locatario" name="tenantNickname" type="text" class="form-control" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default" required>
       </div>
    </div>

    <br>



    <div class="row">
        <div class="col .text-center">
            <input type="text" class="form-control" name="locatarioNome" placeholder="Nome locatario" required>
    </div>
        <div class="col .text-center" >
            <input type="text" class="form-control" name="locatarioCognome" placeholder="Cognome locatario" required>
         </div>
        <div class="col .text-center" >
            <input type="text" class="form-control" name="locatarioCF" placeholder="Codice fiscale locatario" required>
        </div>
        <div class="col .text-center" >
            <input type="text" class="form-control" name="locatarioIndirizzo" placeholder="Indirizzo locatario" required>
        </div>
    </div>
    <br>

    <div class="row">
        <div class="col .text-center">
            <input type="text" class="form-control" name="locatoreNome" placeholder="Nome locatore" required>
        </div>
        <div class="col .text-center" >
            <input type="text" class="form-control" name="locatoreCognome" placeholder="Cognome locatore" required>
        </div>
        <div class="col .text-center" >
            <input type="text" class="form-control" name="locatoreCF" placeholder="Codice fiscale locatore" required>
        </div>
        <div class="col .text-center" >
            <input type="text" class="form-control" name="locatoreIndirizzo" placeholder="Indirizzo locatore" required>
        </div>
    </div>
    <br>

    <div class="row">
        <div class="col .text-center" >
            <input type="number" class="form-control" name="contractPrezzo" placeholder="Prezzo" required>
        </div>
        <div class="col .text-center" >
            <input type="number" class="form-control" name="contractDeposito" placeholder="Deposito" required>
        </div>
        <div class="col .text-center" >
            <select name="contractType" required>
                <option value="Contratto ordinario a canone libero" selected disabled hidden>Seleziona una tipologia di contratto...</option>
                <option value="Contratto ordinario a canone libero">Contratto ordinario a canone libero</option>
                <option value="Contratto transitorio">Contratto transitorio</option>
                <option value="Contratto di locazione convenzionato o a canone concordato">Contratto di locazione convenzionato o a canone concordato</option>
                <option value="Contratto transitorio per studenti">Contratto transitorio per studenti</option>
            </select>
        </div>
    </div>

    <input type="hidden" name="rentableID" value="<%= request.getParameter("rentableID") %>" />
    <input type="hidden" name="rentableName" value="<%= request.getParameter("rentableName") %>" />
    <input type="hidden" name="rentableDescription" value="<%= request.getParameter("rentableDescription") %>" />
    <input type="hidden" name="rentableImage" value="<%= request.getParameter("rentableImage") %>" />
    <input type="hidden" name="rentableType" value="<%= request.getParameter("rentableType") %>" />

    <br>
    <div class="row">
        <div class="col .text-center" >
            <button type="sumbit" name="trueImportContract" class="btn btn-primary btn-lg">Importa contratto</button>
        </div>
    </div>
</form>
</center>
    <br>
</div>

<script type='text/javascript' src='${pageContext.request.contextPath}/Resource/jquery-1.8.3.min.js'></script>
<link rel='stylesheet' href='${pageContext.request.contextPath}/Resource/bootstrap.min.css'>
<link rel='stylesheet' href='${pageContext.request.contextPath}/Resource/bootstrap-datepicker3.min.css'>
<script type='text/javascript' src='${pageContext.request.contextPath}/Resource/bootstrap-datepicker.min.js'></script>


</body>
</html>