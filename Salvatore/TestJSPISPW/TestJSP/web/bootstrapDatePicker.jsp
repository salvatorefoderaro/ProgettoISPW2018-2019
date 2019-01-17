<%@page import="java.time.temporal.ChronoUnit"%>
<%@page import="java.time.LocalDate"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%
    
        LocalDate today = LocalDate.now();
        System.out.println("Current date: " + today);

        //add 2 week to the current date
        LocalDate nextWeek = today.plus(1, ChronoUnit.WEEKS);
        out.println("Next week: " + nextWeek);
    
    if (request.getParameter("1") != null) {
        out.print(request.getParameter("date"));
}
%>

<!DOCTYPE html>
<html>
<head>
    <title>Bootstrap datepicket demo</title>
    <link href="https://netdna.bootstrapcdn.com/bootstrap/3.0.3/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://netdna.bootstrapcdn.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>
    <script type='text/javascript' src='http://code.jquery.com/jquery-1.8.3.min.js'></script>

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.5.0/css/bootstrap-datepicker3.min.css">
    <script type='text/javascript' src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.5.0/js/bootstrap-datepicker.min.js"></script>

<script type='text/javascript'>
$(function(){
$('.input-group.date').datepicker({
	format: "yyyy-mm-dd",
    calendarWeeks: true,
    startDate: "<%= (nextWeek) %>",
    autoclose: true
});  
});
</script>

</head>
<body>
<div class="container">
<h1>Bootstrap datepicker</h1>
    <form action="bootstrapDatePicker.jsp" name="myform" method="POST">
<div class="input-group date">
        <input name="date" type="text" class="form-control"><span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
       
       

</div>
        <br><input name = "1" type="submit" class="btn btn-info" value="Reinoltra segnalazione">

        </form>
</div>
</body>
</html>