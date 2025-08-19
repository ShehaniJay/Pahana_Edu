<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<html>
<head>
    <title>Pahana Edu - Generate Bill</title>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">


    <style>
        body {
            font-family: 'Roboto', sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f7fa;
            color: #333;
        }

        h1, h2, h3 {
            margin-top: 0;
        }
        .form-section, .bill-preview, .bill-history {
            width: 100%;
            position: static;
            background-color: white;
            padding: 13px;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        }
        .form-section {
            flex: 1;
            width: 600px;
        }
        .bill-preview, .bill-history {
            width: 600px;
            position: sticky;
            top: 20px;
            height: fit-content;
        }
        .bill-history {
            width: 600px;
            position: sticky;
            top: 20px;
            height: fit-content;
        }
        .form-group {
            margin-bottom: 15px;
        }
        label {
            display: block;
            margin-bottom: 5px;
            font-weight: 600;
        }
        select, input[type="number"] {
            width: 100%;
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
        }
        button {
            padding: 10px 15px;
            background-color: #3498db;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            transition: background-color 0.3s;
            margin-right: 5px;
        }
        button:hover {
            background-color: #2980b9;
        }
        .delete-btn {
            background-color: #e74c3c;
        }
        .delete-btn:hover {
            background-color: #c0392b;
        }
        .message {
            margin-top: 10px;
            color: #27ae60;
            font-weight: bold;
        }
        .error {
            color: #e74c3c;
            font-weight: bold;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 10px;
        }
        th, td {
            padding: 10px;
            border: 1px solid #ddd;
            text-align: left;
        }
        th {
            background-color: #ecf0f1;
        }
        .back-btn {
            display: inline-block;
            padding: 10px 20px;
            background-color: #7f8c8d;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            margin-top: 20px;
            transition: background-color 0.3s;
        }
        .back-btn:hover {
            background-color: #6c757d;
        }
        .printable-bill {
            display: none;
            border: 2px solid #000;
            padding: 20px;
            max-width: 600px;
            margin: 20px auto;
        }
        .printable-bill.visible {
            display: block;
        }
        @media (max-width: 480px) {
            .container {
                flex-direction: column;
                padding: 10px;
            }

            @media print {
                /*body * {*/
                /*    visibility: hidden;*/
                /*}*/
                .printable-bill, .printable-bill * {
                    visibility: visible;
                }

                .printable-bill {
                    position: absolute;
                    left: 0;
                    top: 0;
                    width: 100%;
                }
            }
        }
    </style>
    <script>
        window.onload = function() {
            if ('${showBillOnly}' === 'true' && '${not empty finalItems}') {
                const billSection = document.querySelector('.printable-bill');
                if (billSection) {
                    billSection.classList.add('visible');
                    setTimeout(() => {
                        window.print();
                        billSection.classList.remove('visible');
                        window.location.href = "${pageContext.request.contextPath}/bill?accountNumber=${param.accountNumber}";
                    }, 200);
                }
            } else if ('${showBillOnly}' === 'true' && '${not empty error}') {
                alert('Error generating bill: ${error}');
                window.location.href = "${pageContext.request.contextPath}/bill?accountNumber=${param.accountNumber}";
            }
        };
    </script>
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
<div class="container">
    <c:if test="${not showBillOnly}">
        <div class="form-section">

            <h2>Generate Bill</h2>
            <% if (request.getAttribute("message") != null) { %>
            <div class="message"><%= request.getAttribute("message") %></div>
            <% } %>
            <% if (request.getAttribute("error") != null) { %>
            <div class="error"><%= request.getAttribute("error") %></div>
            <% } %>

            <h3>Select Customer</h3>
            <form action="bill" method="get">
                <div class="form-group">
                    <label for="accountNumber">Account Number</label>
                    <select id="accountNumber" name="accountNumber" onchange="this.form.submit()" required>
                        <option value="">-- Select Customer --</option>
                        <c:forEach var="customer" items="${customers}">
                            <option value="${customer.accountNumber}" ${customer.accountNumber == param.accountNumber ? 'selected' : ''}>
                                    ${customer.accountNumber} - ${customer.name}
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </form>

            <c:if test="${not empty customer}">
                <h3>Customer Details</h3>
                <p>Account Number: ${customer.accountNumber}</p>
                <p>Name: ${customer.name}</p>
                <p>Address: ${customer.address}</p>
                <p>Telephone: ${customer.telephone}</p>

                <h3>Add Items</h3>
                <form action="bill" method="post">
                    <input type="hidden" name="accountNumber" value="${customer.accountNumber}">
                    <input type="hidden" name="action" value="add">
                    <div class="form-group">
                        <label for="itemId">Item</label>
                        <select id="itemId" name="itemId" required>
                            <option value="">-- Select Item --</option>
                            <c:forEach var="item" items="${items}">
                                <option value="${item.itemId}">${item.name} - Rs. ${item.price}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="quantity">Quantity</label>
                        <input type="number" id="quantity" name="quantity" min="1" required>
                    </div>
                    <button type="submit">Add</button>
                </form>

                <c:if test="${not empty currentBill}">
                    <h3>Confirm Final Bill</h3>
                    <form action="bill" method="post">
                        <input type="hidden" name="action" value="confirm">
                        <input type="hidden" name="accountNumber" value="${customer.accountNumber}">
                        <button type="submit">Generate Final Bill</button>
                    </form>
                </c:if>
            </c:if>
            <a href="${pageContext.request.contextPath}/jsp/dashboard.jsp" class="btn btn-secondary mt-3">Back</a>
        </div>

        <div class="bill-preview">
            <h3>Current Bill</h3>
            <c:if test="${not empty currentBill}">
                <table>
                    <thead>
                    <tr>
                        <th>Item Name</th>
                        <th>Quantity</th>
                        <th>Price</th>
                        <th>Total</th>
                        <th>Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="entry" items="${currentBill}">
                        <c:set var="item" value="${itemsMap[entry.key]}"/>
                        <tr>
                            <td>${item.name}</td>
                            <td>${entry.value}</td>
                            <td>Rs. ${item.price}</td>
                            <td>Rs. ${item.price * entry.value}</td>
                            <td>
                                <form action="bill" method="post" style="display:inline;">
                                    <input type="hidden" name="action" value="update">
                                    <input type="hidden" name="accountNumber" value="${customer.accountNumber}">
                                    <input type="hidden" name="itemId" value="${item.itemId}">
                                    <input type="number" name="quantity" min="1" required style="width: 60px;">
                                    <button type="submit">Update</button>
                                </form>
                                <form action="bill" method="post" style="display:inline;">
                                    <input type="hidden" name="action" value="delete">
                                    <input type="hidden" name="accountNumber" value="${customer.accountNumber}">
                                    <input type="hidden" name="itemId" value="${item.itemId}">
                                    <button type="submit" class="delete-btn" onclick="return confirm('Are you sure you want to delete this item?');">Delete</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    <tr>
                        <td colspan="4"><strong>Total Amount</strong></td>
                        <td><strong>Rs. ${totalAmount}</strong></td>
                    </tr>
                    </tbody>
                </table>
            </c:if>
            <c:if test="${empty currentBill}">
                <p>No items added yet.</p>
            </c:if>
        </div>

        <div class="bill-history">
            <h3>Bill History</h3>
            <c:if test="${not empty bills}">
                <c:forEach var="bill" items="${bills}">
                    <div style="margin-bottom: 20px; border: 1px solid #ddd; padding: 10px; border-radius: 5px;">
                        <p><strong>Bill ID:</strong> ${bill.billId}</p>
                        <p><strong>Date:</strong> <fmt:formatDate value="${bill.date}" pattern="yyyy-MM-dd HH:mm:ss" /></p>
                        <table>
                            <thead>
                            <tr>
                                <th>Item Name</th>
                                <th>Quantity</th>
                                <th>Price</th>
                                <th>Amount</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forTokens var="item" items="${bill.items}" delims=";">
                                <c:set var="details" value="${fn:split(item, ':')}"/>
                                <c:set var="itemObj" value="${itemsMap[details[0]]}"/>
                                <tr>
                                    <td>${itemObj.name}</td>
                                    <td>${details[1]}</td>
                                    <td>Rs. ${itemObj.price}</td>
                                    <td>Rs. ${details[2]}</td>
                                </tr>
                            </c:forTokens>
                            </tbody>
                        </table>
                        <p><strong>Total Amount:</strong> $${bill.total}</p>
                    </div>
                </c:forEach>
            </c:if>
            <c:if test="${empty bills}">
                <p>No bill history available.</p>
            </c:if>
        </div>
    </c:if>

    <c:if test="${showBillOnly}">
        <c:choose>
            <c:when test="${not empty finalItems}">
                <div class="printable-bill visible">
                    <h3>Final Bill</h3>
                    <p><strong>Bill ID:</strong> ${finalBillId}</p>
                    <p><strong>Customer Name:</strong> ${finalCustomer.name}</p>
                    <p><strong>Account Number:</strong> ${finalCustomer.accountNumber}</p>
                    <p><strong>Address:</strong> ${finalCustomer.address}</p>
                    <p><strong>Telephone:</strong> ${finalCustomer.telephone}</p>
                    <p><strong>Date:</strong> <fmt:formatDate value="${finalBillDate}" pattern="yyyy-MM-dd HH:mm:ss" /></p>
                    <table>
                        <thead>
                        <tr>
                            <th>Item Name</th>
                            <th>Quantity</th>
                            <th>Price</th>
                            <th>Amount</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forTokens var="item" items="${finalItems}" delims=";">
                            <c:set var="details" value="${fn:split(item, ':')}"/>
                            <c:set var="itemObj" value="${itemsMap[details[0]]}"/>
                            <tr>
                                <td>${itemObj.name}</td>
                                <td>${details[1]}</td>
                                <td>Rs. ${itemObj.price}</td>
                                <td>Rs. ${details[2]}</td>
                            </tr>
                        </c:forTokens>
                        </tbody>
                    </table>
                    <p><strong>Total Amount:</strong> Rs. ${finalTotalAmount}</p>
                </div>
            </c:when>
            <c:otherwise>
                <div class="error" style="margin: 20px auto; max-width: 600px;">
                    <p>Error generating bill: ${error}</p>
                    <a href="${pageContext.request.contextPath}/bill?accountNumber=${param.accountNumber}" class="back-btn">Back</a>
                </div>
            </c:otherwise>
        </c:choose>
    </c:if>
</div>
</body>
</html>