<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Pahana Edu - Help</title>
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
    <h2>Help</h2>
    <p>Welcome to the Pahana Edu Bookstore Management System. Below are the guidelines for using the system:</p>
    <ul>
        <li><strong>Login</strong>: Use your username and password to access the system.</li>
        <li><strong>Dashboard</strong>: View all customers and their details.</li>
        <li><strong>Add Customer</strong>: Register a new customer with their account number, name, address, telephone, and units consumed.</li>
        <li><strong>Manage Items</strong>: Add, update, or delete book items, including their ID, name, price, and stock.</li>
        <li><strong>Generate Bill</strong>: Create a bill for a customer by selecting an item and specifying the quantity.</li>
        <li><strong>Logout</strong>: Exit the system securely.</li>
    </ul>
</div>
<a href="/jsp/dashboard.jsp" class="btn btn-secondary mt-3">Back</a>
</body>
</html>