<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<html>
<head>
    <title>Pahana Edu - Manage Customers</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
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
    <h2>Manage Customers</h2>
    <% if (request.getAttribute("message") != null) { %>
    <div class="alert alert-${message.contains('successfully') ? 'success' : 'danger'}"><%= request.getAttribute("message") %></div>
    <% } %>
    <!-- Customer Form -->
    <form action="${pageContext.request.contextPath}/customer" method="post" class="mb-4" onsubmit="return validateForm()">
        <input type="hidden" name="action" value="${not empty customer ? 'edit' : 'add'}">
        <div class="form-group">
            <label for="accountNumber">Account Number:</label>
            <input type="text" class="form-control" id="accountNumber" name="accountNumber" value="${customer != null ? customer.accountNumber : ''}" ${not empty customer ? 'readonly' : ''} required>
        </div>
        <div class="form-group">
            <label for="name">Name:</label>
            <input type="text" class="form-control" id="name" name="name" value="${customer != null ? customer.name : ''}" required>
        </div>
        <div class="form-group">
            <label for="address">Address:</label>
            <input type="text" class="form-control" id="address" name="address" value="${customer != null ? customer.address : ''}" required>
        </div>
        <div class="form-group">
            <label for="telephone">Telephone:</label>
            <input type="text" class="form-control" id="telephone" name="telephone" value="${customer != null ? customer.telephone : ''}" required>
        </div>
        <button type="submit" class="btn btn-primary">${not empty customer ? 'Update' : 'Add'} Customer</button>
        <button type="button" class="btn btn-secondary ml-2" onclick="resetForm()">Clear</button>
    </form>

    <table class="table table-bordered">
        <thead>
        <tr>
            <th>Account Number</th>
            <th>Name</th>
            <th>Address</th>
            <th>Telephone</th>
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
                <td>
                    <a href="${pageContext.request.contextPath}/customer?action=edit&accountNumber=${customer.accountNumber}" class="btn btn-sm btn-primary">Edit</a>
                    <form action="${pageContext.request.contextPath}/customer" method="post" style="display:inline;">
                        <input type="hidden" name="action" value="delete">
                        <input type="hidden" name="accountNumber" value="${customer.accountNumber}">
                        <button type="submit" class="btn btn-sm btn-danger" onclick="return confirm('Are you sure?')">Delete</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <a href="${pageContext.request.contextPath}/jsp/dashboard.jsp" class="btn btn-secondary mt-3">Back</a>
</div>
<script>
    window.onload = function() {
        const messageDiv = document.querySelector('.message');
        if (messageDiv) {
            setTimeout(() => {
                messageDiv.style.display = 'none';
            }, 3000);
        }
    function resetForm() {
        document.getElementById("accountNumber").value = "";
        document.getElementById("name").value = "";
        document.getElementById("address").value = "";
        document.getElementById("telephone").value = "";
        document.querySelector("input[name='action']").value = "add";
    }

    function validateForm() {
        var accountNumber = document.getElementById("accountNumber").value.trim();
        if (accountNumber === "" && document.querySelector("input[name='action']").value === "add") {
            alert("Account Number is required for adding a new customer.");
            return false;
        }
        return true;
    }
</script>
</body>
</html>