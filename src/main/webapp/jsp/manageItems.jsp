<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<html>
<head>
  <title>Pahana Edu - Manage Items</title>
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
  <link rel="stylesheet" href="css/style.css">
</head>
<body>
<h1>Welcome, ${sessionScope.username}</h1>
<div class="container">
  <nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="#">Pahana Edu</a>
    <div class="navbar-nav">
      <a class="nav-link" href="${pageContext.request.contextPath}/jsp/dashboard.jsp">Dashboard</a>
      <a class="nav-link" href="customer">Add Customer</a>
      <a class="nav-link" href="item">Manage Items</a>
      <a class="nav-link" href="bill">Generate Bill</a>
      <a class="nav-link" href="help">Help</a>
      <a class="nav-link" href="logout">Logout</a>
    </div>
  </nav>
  <h2>Manage Items</h2>
  <% if (request.getAttribute("message") != null) { %>
  <div class="alert alert-success"><%= request.getAttribute("message") %></div>
  <% } %>
  <h3>Add New Item</h3>
  <form action="item" method="post">
    <input type="hidden" name="action" value="add">
    <div class="form-group">
      <label for="itemId">Item ID</label>
      <input type="text" class="form-control" id="itemId" name="itemId" required>
    </div>
    <div class="form-group">
      <label for="name">Name</label>
      <input type="text" class="form-control" id="name" name="name" required>
    </div>
    <div class="form-group">
      <label for="price">Price</label>
      <input type="number" step="0.01" class="form-control" id="price" name="price" required>
    </div>
    <div class="form-group">
      <label for="stock">Stock</label>
      <input type="number" class="form-control" id="stock" name="stock" required>
    </div>
    <button type="submit" class="btn btn-primary">Add Item</button>
    <button type="button" class="btn btn-secondary ml-2" onclick="resetForm()">Clear</button>
  </form>
  <h3>Item List</h3>
  <table class="table table-bordered">
    <thead>
    <tr>
      <th>Item ID</th>
      <th>Name</th>
      <th>Price</th>
      <th>Stock</th>
      <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="item" items="${items}">
      <tr>
        <td>${item.itemId}</td>
        <td>${item.name}</td>
        <td>${item.price}</td>
        <td>${item.stock}</td>
        <td>
          <form action="item" method="post" style="display:inline;">
            <input type="hidden" name="action" value="update">
            <input type="hidden" name="itemId" value="${item.itemId}">
            <input type="text" name="name" value="${item.name}" required>
            <input type="number" step="0.01" name="price" value="${item.price}" required>
            <input type="number" name="stock" value="${item.stock}" required>
            <button type="submit" class="btn btn-sm btn-primary">Update</button>
          </form>
          <form action="item" method="post" style="display:inline;">
            <input type="hidden" name="action" value="delete">
            <input type="hidden" name="itemId" value="${item.itemId}">
            <button type="submit" class="btn btn-sm btn-danger">Delete</button>
          </form>
        </td>
      </tr>
    </c:forEach>
    </tbody>
  </table>
  <a href="/jsp/dashboard.jsp" class="btn btn-secondary mt-3">Back</a>
</div>
<script>
  function resetForm() {
    document.getElementById("itemId").value = "";
    document.getElementById("name").value = "";
    document.getElementById("price").value = "";
    document.getElementById("stock").value = "";
    document.querySelector("input[name='action']").value = "add";
  }
</script>
</body>
</html>