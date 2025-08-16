<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Pahana Edu - Add Customer</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
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
    <h2>Add New Customer</h2>
    <form action="customer" method="post">
        <input type="hidden" name="action" value="add">
        <div class="form-group">
            <label for="accountNumber">Account Number</label>
            <input type="text" class="form-control" id="accountNumber" name="accountNumber" required>
        </div>
        <div class="form-group">
            <label for="name">Name</label>
            <input type="text" class="form-control" id="name" name="name" required>
        </div>
        <div class="form-group">
            <label for="address">Address</label>
            <input type="text" class="form-control" id="address" name="address" required>
        </div>
        <div class="form-group">
            <label for="telephone">Telephone</label>
            <input type="text" class="form-control" id="telephone" name="telephone" required>
        </div>
        <div class="form-group">
            <label for="unitsConsumed">Units Consumed</label>
            <input type="number" class="form-control" id="unitsConsumed" name="unitsConsumed" required>
        </div>
        <button type="submit" class="btn btn-primary">Add Customer</button>
    </form>
</div>
</body>
</html>