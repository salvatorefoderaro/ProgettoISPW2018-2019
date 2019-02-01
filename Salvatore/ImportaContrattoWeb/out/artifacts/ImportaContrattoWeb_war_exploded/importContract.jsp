<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.sql.SQLException"%>
<%@ page import="Bean.contractBean" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="Bean.userBean" %>
<%@ page import="Exceptions.transactionError" %>

<jsp:useBean id="sessionBean" scope="session" class="Bean.userBean"/>
<jsp:useBean id="toRent" scope="session" class="Bean.rentableBean" />

<%

    if ((sessionBean.getNickname() == null) || (toRent.getName() == null)){

        session.setAttribute("warningMessage", "Effettua l'accesso prima di continuare!");
        String destination ="index.jsp";
        response.sendRedirect(response.encodeRedirectURL(destination));
        return;
    }

    if (request.getParameter("trueImportContract") != null){

    toRent.setTenantnNickname(request.getParameter("tenantNickname"));
    toRent.setEndDateRequest(request.getParameter("endDateRequest"));
    toRent.setStartDateRequest(request.getParameter("startDateRequest"));

    LocalDate localStartDate = LocalDate.parse ( request.getParameter("startDateRequest") , DateTimeFormatter.ofPattern ("yyyy-MM-dd" ) );
    LocalDate localEndDate = LocalDate.parse ( request.getParameter("startDateRequest") , DateTimeFormatter.ofPattern ("yyyy-MM-dd" ) );

    if (localEndDate.isBefore(localStartDate)){
        session.setAttribute("infoMessage", "La data indicata non è valida!");
        String destination ="importContract.jsp";
        response.sendRedirect(response.encodeRedirectURL(destination));
        return;
    }

    if (localEndDate.isAfter(localStartDate.plusDays(180))){
        session.setAttribute("infoMessage", "L'intervallo massimo è di 180 giorni!");
        String destination ="importContract.jsp";
        response.sendRedirect(response.encodeRedirectURL(destination));
        return;
    }

        userBean tenant = null;
    try {
        tenant = sessionBean.getController().checkTenantNickname(toRent);
    } catch (SQLException e) {
        e.printStackTrace();
        session.setAttribute("waningMessage", "Errore nella connessione con il database!");
        String destination ="importContract.jsp";
        response.sendRedirect(response.encodeRedirectURL(destination));
        return;
    } catch (Exceptions.emptyResult emptyResult) {
        session.setAttribute("infoMessage", "Nessun utente associato al nickname indicato!");
        String destination ="importContract.jsp";
        response.sendRedirect(response.encodeRedirectURL(destination));
        return;
    }

        try {
            sessionBean.getController().checkRentableDate(toRent);
        } catch (Exceptions.transactionError transactionError) {
            session.setAttribute("infoMessage", "Errore nell'esecuzione della richiesta! Riprova.");
            String destination ="importContract.jsp";
            response.sendRedirect(response.encodeRedirectURL(destination));
            return;
        } catch (SQLException e) {
            session.setAttribute("warningMessage", "Errore nella connessione con il database!");
            String destination ="index.jsp";
            response.sendRedirect(response.encodeRedirectURL(destination));
            return;
        } catch (Exceptions.emptyResult emptyResult) {
            session.setAttribute("infoMessage", "La risorsa non è disponibile per le date indicate!");
            String destination ="importContract.jsp";
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
                Integer.parseInt(request.getParameter("contractDeposito"))
        );
        }

        try {
            sessionBean.getController().createContract(contract);
        } catch (SQLException e) {
            session.setAttribute("warningMessage", "Errore nella connessione con il database!");
            String destination ="index.jsp";
            response.sendRedirect(response.encodeRedirectURL(destination));
            return;
        }
        catch (Exceptions.transactionError transactionError) {
            session.setAttribute("transactionError", "");
            String destination ="importContract.jsp";
            response.sendRedirect(response.encodeRedirectURL(destination));
            return;
        }


%>

<jsp:forward page="seeRentable.jsp">
    <jsp:param name="success" value="contractCreated" />
</jsp:forward>

<%
    return; }
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<html>
<head>
    <title>Title</title>

    <script type='text/javascript' src='${pageContext.request.contextPath}/Resource/jquery-1.8.3.min.js'></script>
    <link rel='stylesheet' href='${pageContext.request.contextPath}/Resource/bootstrap.min.css'>
    <link rel='stylesheet' href='${pageContext.request.contextPath}/Resource/bootstrap-datepicker3.min.css'>
    <script type='text/javascript' src='${pageContext.request.contextPath}/Resource/bootstrap-datepicker.min.js'></script>

    <script type='text/javascript'>
        $(function(){
            $('.input-group.date').datepicker({
                format: "yyyy-mm-dd",
                calendarWeeks: true,
                startDateRequest: "<%= LocalDate.now().plusDays(30).toString() %>",
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


    // Error handling

    <%
    if (session.getAttribute("successMessage") != null) { %>

    <div class="alert alert-warning">
        <strong>Ok!</strong> <%= session.getAttribute("successMessage") %>
    </div>


    <% session.setAttribute("successMessage", null);
        }

        if (session.getAttribute("infoMessage") != null) {  %>


    <div class="alert alert-warning">
        <strong>Attenzione!</strong> <%= session.getAttribute("infoMessage") %>
    </div>


    <% session.setAttribute("infoMessage", null);
    }

        if (session.getAttribute("warningMessage") != null) {  %>


    <div class="alert alert-warning">
        <strong>Errore!</strong> <%= session.getAttribute("warningMessage") %>
    </div>


    <% session.setAttribute("warningMessage", null);
    }
%>


<form action="importContract.jsp" method="POST">

    <div class="row">
        <div class="col-5 .text-center">
            <img class="img-rounded img-responsive" style="max-width:80%" src="data:image/jpeg;base64,<%=toRent.getImage()%>">
        </div>
        <div class="col-7 .text-center">
            Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Tempus quam pellentesque nec nam aliquam sem et tortor consequat. Fermentum iaculis eu non diam phasellus vestibulum lorem sed risus. Condimentum lacinia quis vel eros donec. Pellentesque elit ullamcorper dignissim cras tincidunt lobortis feugiat. Turpis in eu mi bibendum. Et sollicitudin ac orci phasellus. Aliquet nec ullamcorper sit amet risus nullam. Urna porttitor rhoncus dolor purus. Enim blandit volutpat maecenas volutpat blandit aliquam etiam erat. Sem et tortor consequat id porta nibh venenatis. Mi eget mauris pharetra et ultrices neque ornare. Euismod in pellentesque massa placerat duis ultricies lacus. Amet consectetur adipiscing elit pellentesque. Consequat id porta nibh venenatis. Ultrices eros in cursus turpis.
        </div>
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
        <div class="col .text-center">
        </div>
        <div class="col .text-center" >
            <input type="number" class="form-control" name="contractPrezzo" placeholder="Prezzo" required>
        </div>
        <div class="col .text-center" >
            <input type="number" class="form-control" name="contractDeposito" placeholder="Deposito" required>
        </div>
        <div class="col .text-center" >
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

</body>
</html>