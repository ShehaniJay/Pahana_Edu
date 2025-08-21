<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<html>
<head>
  <title>Pahana Edu - Dashboard</title>
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
  <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;600&display=swap" rel="stylesheet">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
  <style>
    .note {
      margin: 50px 0;
      padding: 15px;
      border: 1px solid #ffeeba;
      border-radius: 5px;
      color: #856404;
      font-size: 1.1rem;
      font-weight: 400;
    }

  </style>
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


  <div class="note">
    <strong>Note:</strong> Please ensure all customer details are updated before generating bills. New items have been added to the inventory—check the Manage Items section for details!
  </div>
</div>
</body>
</html>