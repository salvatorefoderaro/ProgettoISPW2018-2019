<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.sql.SQLException"%>
<%@ page import="Bean.contractBean" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="Bean.userBean" %>

<jsp:useBean id="sessionBean" scope="session" class="Bean.userBean"/>
<jsp:useBean id="toRent" scope="session" class="Bean.rentableBean" />

<%

    if (sessionBean.getNickname() == null){
        response.sendRedirect("index.jsp?error=makeLogin");
        return;
    }

    if (toRent.getName() == null){
        response.sendRedirect("index.jsp?error=makeLogin");
        return;
    }

    if (request.getParameter("trueImportContract") != null){

    toRent.setTenantnNickname(request.getParameter("tenantNickname"));
    toRent.setEndDate(request.getParameter("endDate"));
    toRent.setStartDate(request.getParameter("startDate"));

    LocalDate localStartDate = LocalDate.parse ( request.getParameter("startDate") , DateTimeFormatter.ofPattern ("yyyy-MM-dd" ) );
    LocalDate localEndDate = LocalDate.parse ( request.getParameter("startDate") , DateTimeFormatter.ofPattern ("yyyy-MM-dd" ) );

    if (localStartDate.isBefore(localEndDate)){
        session.setAttribute("wrongInterval", "");
        String destination ="importContract.jsp";
        response.sendRedirect(response.encodeRedirectURL(destination));
        return;
    }

    if (localEndDate.isAfter(localStartDate.plusDays(180))){
        session.setAttribute("wrongDaysIntervalMax", "");
        String destination ="importContract.jsp";
        response.sendRedirect(response.encodeRedirectURL(destination));
        return;
    }

        userBean tenant = null;
    try {
        tenant = sessionBean.getController().checkTenantNickname(toRent);
    } catch (SQLException e) {
        response.sendRedirect("index.jsp?error=databaseConnection");
        e.printStackTrace();
        return;
    } catch (Exceptions.emptyResult emptyResult) {

        session.setAttribute("tenantNotFound", "");
        String destination ="importContract.jsp";
        response.sendRedirect(response.encodeRedirectURL(destination));
        return;
    }

    try {
        sessionBean.getController().checkRentableDate(toRent);
    } catch (Exceptions.emptyResult emptyResult) {
        session.setAttribute("notAvaiable", "");
        String destination ="importContract.jsp";
        response.sendRedirect(response.encodeRedirectURL(destination));
        return;
    }

        toRent.setTenantnNickname(request.getParameter("tenantNickname"));
        toRent.setEndDate(request.getParameter("endDate"));
        toRent.setStartDate(request.getParameter("startDate"));

        contractBean contract = null;
    if (tenant != null) {
        contract = new contractBean(
                0,
                toRent.getID(),
                false,
                LocalDate.parse(request.getParameter("startDate"), DateTimeFormatter.ofPattern ("yyyy-MM-dd" )),
                LocalDate.parse(request.getParameter("endDate"), DateTimeFormatter.ofPattern ("yyyy-MM-dd" )),
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
                toRent.getType());
        } try {

        sessionBean.getController().createContract(contract);

    } catch (Exceptions.dbConnection e) {

    response.sendRedirect("index.jsp?error=databaseConnection");
    return;

    }catch (Exceptions.transactionError transactionError) {

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
                startDate: "<%= LocalDate.now().plusDays(30).toString() %>",
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
    if (session.getAttribute("transactionError") != null) { %>

    <div class="alert alert-warning">
        <strong>Errore!</strong> Errore nell'esecuzione della richiesta.
    </div>


    <% session.setAttribute("transactionError", null);
        }

        if (session.getAttribute("wrongInterval") != null) {  %>


    <div class="alert alert-warning">
        <strong>Errore!</strong> L'intervallo inserito non è corretto!
    </div>


    <% session.setAttribute("wrongInterval", null);
    }

        if (session.getAttribute("wrongDaysIntervalMax") != null) {  %>


    <div class="alert alert-warning">
        <strong>Errore!</strong> L'intervallo massimo è di 180 giorni!
    </div>


    <% session.setAttribute("wrongDaysIntervalMax", null);
    }

    if (session.getAttribute("notAvaiable") != null) {  %>


    <div class="alert alert-warning">
        <strong>Errore!</strong> Risorsa non disponibile per la data indicata.
    </div>


    <% session.setAttribute("notAvaiable", null);
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
                <input name="startDate" placeholder="Data inizio contratto.." type="text" class="form-control" required><span class="input-group-addon"><i class="glyphicon glyphicon-calendar" ></i></span>
            </div>    </div>
        <div class="col .text-center">
            <div class="input-group date">
                <input name="endDate" placeholder="Data fine contratto.." type="text" class="form-control" required><span class="input-group-addon"><i class="glyphicon glyphicon-calendar" ></i></span>
            </div>    </div>
        <div class="col .text-center">
                <input placeholder="Nickname locatario" name="tenantNickname" type="text" class="form-control" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default" required>
       </div>
    </div>

    <br>

    <div class="row">
        <div class="col .text-center">
            <input type="text" class="form-control" id="usr" placeholder="Nome locatario" required>
    </div>
        <div class="col .text-center" >
            <input type="text" class="form-control" id="usr" placeholder="Cognome locatario" required>
         </div>
        <div class="col .text-center" >
            <input type="text" class="form-control" id="usr" placeholder="Codice fiscale locatario" required>
        </div>
        <div class="col .text-center" >
            <input type="text" class="form-control" id="usr" placeholder="Indirizzo locatario" required>
        </div>
    </div>
    <br>

    <div class="row">
        <div class="col .text-center">
            <input type="text" class="form-control" id="usr" placeholder="Nome locatore" required>
        </div>
        <div class="col .text-center" >
            <input type="text" class="form-control" id="usr" placeholder="Cognome locatore" required>
        </div>
        <div class="col .text-center" >
            <input type="text" class="form-control" id="usr" placeholder="Codice fiscale locatore" required>
        </div>
        <div class="col .text-center" >
            <input type="text" class="form-control" id="usr" placeholder="Indirizzo locatore" required>
        </div>
    </div>
    <br>

    <div class="row">
        <div class="col .text-center">
        </div>
        <div class="col .text-center" >
            <input type="number" class="form-control" id="usr" placeholder="Prezzo" required>
        </div>
        <div class="col .text-center" >
            <input type="number" class="form-control" id="usr" placeholder="Deposito" required>
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
</div>

</body>
</html>