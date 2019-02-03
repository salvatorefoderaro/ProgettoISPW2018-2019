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
    <title>ContractRequests</title>
</head>
<body>

<table class="table table-borderless">
    <thead>
    <tr>
        <th scope="col">ID</th>
        <th scope="col">Tenant nickname</th>
        <th scope="col">Creation Date</th>
        <th scope="col">Start Date</th>
        <th scope="col">End Date</th>
        <th scope="col">Price</th>
        <th scope="col">State</th>
    </tr>
    </thead>

    <tbody>
        <% for (RequestLabelBean temp : requestLabelBeans) { %>
        <tr>
            

            <td><%= temp.getContractRequestId() %></td>
            <td><%= temp.getTenantNickname() %></td>
            <td><%= temp.getCreationDate() %></td>

        </tr>
    <% } %>

    </tbody>
</table>
</body>
</html>
