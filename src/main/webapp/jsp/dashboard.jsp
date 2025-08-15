<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<html>
<head>
  <title>Pahana Edu - Dashboard</title>
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
  <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;600&display=swap" rel="stylesheet">
  <link rel="stylesheet" href="css/style.css">
</head>
<body>
<h1>Welcome, ${sessionScope.username}</h1>
<div class="container">
  <nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="#">Pahana Edu</a>
    <div class="navbar-nav">
      <a class="nav-link" href="${pageContext.request.contextPath}/jsp/dashboard.jsp">Dashboard</a>
      <a class="nav-link" href="${pageContext.request.contextPath}/customer">Add Customer</a>
      <a class="nav-link" href="${pageContext.request.contextPath}/item">Manage Items</a>
      <a class="nav-link" href="${pageContext.request.contextPath}/bill">Generate Bill</a>
      <a class="nav-link" href="${pageContext.request.contextPath}/help">Help</a>
      <a class="nav-link" href="${pageContext.request.contextPath}/logout">Logout</a>
    </div>
  </nav>
  <h2>Customer List</h2>
  <% if (request.getAttribute("message") != null) { %>
  <div class="alert alert-success"><%= request.getAttribute("message") %></div>
  <% } %>
  <table class="table table-bordered">
    <thead>
    <tr>
      <th>Account Number</th>
      <th>Name</th>
      <th>Address</th>
      <th>Telephone</th>
      <th>Units Consumed</th>
      <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="customer" items="${customers}">
      <tr>
        <td>${customer.accountNumber}</td>
        <td>${customer.name}</td>
        <td>${customer.address}</td>
        <td>${customer.telephone}</td>
        <td>${customer.unitsConsumed}</td>
        <td>
          <a href="customer?action=edit&accountNumber=${customer.accountNumber}" class="btn btn-sm btn-primary">Edit</a>
          <a href="bill?accountNumber=${customer.accountNumber}" class="btn btn-sm btn-info">Bill</a>
        </td>
      </tr>
    </c:forEach>
    </tbody>
  </table>
</div>
</body>
</html>