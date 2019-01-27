<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.sql.SQLException"%>
<%@ page import="Bean.rentableBean" %>
<%@ page import="Bean.tenantBean" %>
<%@ page import="Bean.contractBean" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.format.DateTimeFormatter" %>

<jsp:useBean id="sessionBean" scope="session" class="Bean.renterBean"/>

<%
    System.out.println(sessionBean.getController());

    if (sessionBean.getNickname() == null){

        response.sendRedirect("LoginPage.jsp?error=makeLogin");

    }

    if (request.getParameter("trueImportContract") != null && request.getParameter("trueImportContract") != "littleTrick"){

    rentableBean toRent = new rentableBean();
    toRent.setID(Integer.parseInt(request.getParameter("rentableID")));
    toRent.setName(request.getParameter("rentableName"));
    toRent.setDescription(request.getParameter("rentableDescription"));
    toRent.setImage(request.getParameter("rentableImage"));
    toRent.setType(request.getParameter("rentableType"));
    toRent.setTenantnNickname(request.getParameter("tenantNickname"));
    toRent.setEndDate(request.getParameter("endDate"));
    toRent.setStartDate(request.getParameter("startDate"));

    tenantBean tenant = null;
    try {
        tenant = sessionBean.getController().checkTenantNickname(toRent);
    } catch (SQLException e) {
        response.sendRedirect("LoginPage.jsp?error=databaseConnection");
    } catch (Exceptions.emptyResult emptyResult) {

        %>
        <jsp:forward page="importContract.jsp">
            <jsp:param name="error" value="tenantNotFound" />
            <jsp:param name="trueImportContract" value="littleTrick" />
        </jsp:forward>
        <%
    }

    try {
        sessionBean.getController().checkRentableDate(toRent);
    } catch (Exceptions.emptyResult emptyResult) {


    } catch (Exceptions.transactionError transactionError) {
        transactionError.printStackTrace();
    } catch (Exceptions.dbConnection dbConnection) {
        response.sendRedirect("LoginPage.jsp?error=databaseConnection");
    }

    contractBean contract = null;
    if (tenant != null) {
        System.out.println("Arrivo fin prima l'inserimento del contratto?");
        contract = new contractBean(0, false, LocalDate.parse(toRent.getStartDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")), LocalDate.parse(toRent.getEndDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")), null, tenant.getNickname(), sessionBean.getNickname(), tenant.getCF(), sessionBean.getCF(), Integer.parseInt(request.getParameter("rataPiuServizi")), Integer.parseInt(request.getParameter("prezzoRata")), 0, false, null);
    }
    try {
        sessionBean.getController().createContract(contract);
    } catch (Exceptions.dbConnection dbConnection) {
        response.sendRedirect("LoginPage.jsp?error=databaseConnection");
    } catch (Exceptions.transactionError transactionError) {%>

<jsp:forward page="seeRentable.jsp">
    <jsp:param name="error" value="transactionError" />
</jsp:forward>

<% }



} %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<html>
<head>
    <title>Title</title>

    <script type='text/javascript' src='${pageContext.request.contextPath}/src/jquery-1.8.3.min.js'></script>
    <link rel='stylesheet' href='${pageContext.request.contextPath}/src/bootstrap.min.css'>
    <link rel='stylesheet' href='${pageContext.request.contextPath}/src/bootstrap-datepicker3.min.css'>
    <script type='text/javascript' src='${pageContext.request.contextPath}/src/bootstrap-datepicker.min.js'></script>

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
            <a class="nav-item nav-link active" href="#">Pannello utente <span class="sr-only">(current)</span></a>
            <a class="nav-item nav-link" href="#">Visualizza segnalazioni</a>
            <a class="nav-item nav-link" href="#">Inoltra segnalazione</a>
        </div>
    </div>
</nav>

<br>



<div class="container">
    <%
    if (session.getAttribute("tenantNotFound") != null) { %>


    <div class="alert alert-warning">
        <strong>Errore!</strong> Nickname e/o password errati.
    </div>


    <% session.setAttribute("tenantNotFound", null);
        }
%>

<center>
<form action="importContract.jsp" method="POST">

    <div class="row">
        <div class="col-5">
            <img class="img-rounded img-responsive" style="max-width:80%" src="${pageContext.request.contextPath}/src/test1.jpeg">
        </div>
        <div class="col-7">
            Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Tempus quam pellentesque nec nam aliquam sem et tortor consequat. Fermentum iaculis eu non diam phasellus vestibulum lorem sed risus. Condimentum lacinia quis vel eros donec. Pellentesque elit ullamcorper dignissim cras tincidunt lobortis feugiat. Turpis in eu mi bibendum. Et sollicitudin ac orci phasellus. Aliquet nec ullamcorper sit amet risus nullam. Urna porttitor rhoncus dolor purus. Enim blandit volutpat maecenas volutpat blandit aliquam etiam erat. Sem et tortor consequat id porta nibh venenatis. Mi eget mauris pharetra et ultrices neque ornare. Euismod in pellentesque massa placerat duis ultricies lacus. Amet consectetur adipiscing elit pellentesque. Consequat id porta nibh venenatis. Ultrices eros in cursus turpis.
        </div>
    </div>

    <br>

    <div class="row">
        <div class="col">
            <div class="input-group date">
                <input name="dataIni<io" placeholder="Data inizio contratto.." type="text" class="form-control" required><span class="input-group-addon"><i class="glyphicon glyphicon-calendar" ></i></span>
            </div>    </div>
        <div class="col">
            <div class="input-group date">
                <input name="fdataFine" placeholder="Data fine contratto.." type="text" class="form-control" required><span class="input-group-addon"><i class="glyphicon glyphicon-calendar" ></i></span>
            </div>    </div>
        <div class="col">
            <div class="input-group mb-3">
                <div class="input-group-prepend">
                    <span class="input-group-text" id="inputGroup-sizing-default">Nome utente</span>
                </div>
                <input name="tenantNickname" type="text" class="form-control" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default" required>
            </div>    </div>
    </div>

    <div class="row">
        <div class="col">
            <div class="input-group mb-3">
                <div class="input-group-prepend">
                    <span class="input-group-text" id="inputGroup-sizing-default">Prezzo rata + servizi</span>
                </div>
                <input name ="rataPiuServizi"type="number" class="form-control" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default" required>
            </div>    </div>
        <div class="col">
            <div class="input-group mb-3">
                <div class="input-group-prepend">
                    <span class="input-group-text" id="inputGroup-sizing-default">Prezzo netto rata</span>
                </div>
                <input type="number" name="prezzoRata" class="form-control" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default" required>
            </div>    </div>
    </div>

    <input type="hidden" name="rentableID" value="<%= request.getParameter("rentableID") %>" />
    <input type="hidden" name="rentableName" value="<%= request.getParameter("rentableName") %>" />
    <input type="hidden" name="rentableDescription" value="<%= request.getParameter("rentableDescription") %>" />
    <input type="hidden" name="rentableImage" value="<%= request.getParameter("rentableImage") %>" />
    <input type="hidden" name="rentableType" value="<%= request.getParameter("rentableType") %>" />


    <div class="row">
        <div class="col">
            <button type="sumbit" name="trueImportContract" class="btn btn-primary btn-lg">Importa contratto</button>
        </div>
    </div>
</form></center></div>

</body>
</html>