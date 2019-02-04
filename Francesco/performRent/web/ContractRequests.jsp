<%--
  Created by IntelliJ IDEA.
  User: francesco
  Date: 03/02/19
  Time: 11.18
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.bean.SessionBean" %>
<%@ page import="java.util.List" %>
<%@ page import="it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.bean.RequestLabelBean" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>

<%
    if (request.getParameter("aButton") != null) {
        System.out.println("Ho premuto il bottone: " + request.getParameter("id"));
        request.getParameter("id");

    } %>


<!-- Si dichiara la variabile loginBean e istanzia un oggetto LoginBean -->
<jsp:useBean id="sessionBean" scope="session"
             class="it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.bean.SessionBean"/>

<%
    List<RequestLabelBean> requestLabelBeans = sessionBean.getControl().getAllContractRequest();
    request.setAttribute("results", requestLabelBeans);
    requestLabelBeans.forEach(requestLabelBean -> {
        System.out.println(requestLabelBean);
        System.out.println(requestLabelBean.getContractRequestId());
    });
%>
<html>
<head>





</head>
<body>

<div class="container">

    <% for (RequestLabelBean temp : requestLabelBeans) { %>
    <form action="ContractRequests.jsp" action="POST">
    <div class="row">
            <div class="col-sm">
                <%= temp.getContractRequestId() %>
            </div>

            <div class="col-sm">
                <%= temp.getTenantNickname() %>
            </div>

            <div class="col-sm">
                <%= temp.getCreationDate() %>
            </div>

            <input name="id" type="text" value="<%= temp.getContractRequestId() %>" hidden>

        <div class="col-sm">
            <button name="aButton" type="sumbit" class="btn btn-primary">Primary</button>
        </div>
    </div>
    </form>

    <% } %>

    </div>


<script type='text/javascript' src='resource/jquery-1.8.3.min.js'></script>
<link rel='stylesheet' href='resource/bootstrap.min.css'>
<link rel='stylesheet' href='resource/bootstrap-datepicker3.min.css'>
<script type='text/javascript' src='resource/bootstrap-datepicker.min.js'></script>

</body>
</html>
