<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<head>
    <tags:templates/>
    <tags:msg/>
</head>
<body>
<title>Cart</title>
<div class="container mt-1 my-2">
    <form method="post" id="deleteItem"></form>
    <form method="get" action="${pageContext.request.contextPath}/productList">
        <input type="submit" class="button button-primary" value="Back to product list"/>
    </form>

    <table class="table table-striped">
        <div class="row">
            <thead>
            <tr>
                <th class="col-2">
                    Order number
                </th>
                <th class="col-2">
                    Customer
                </th>
                <th class="col-2">
                    Phone
                </th>
                <th class="col-2">
                    Address
                </th>
                <th class="col-2">
                    Date
                </th>
                <th class="col-2">
                    Total price
                </th>
                <th class="col-1">
                    Status
                </th>
            </tr>
            </thead>
        </div>
        <tbody>
        <c:forEach var="order" items="${orders}">
            <tr class="col-offset">
                <td>
                    <a href="${pageContext.request.contextPath}/admin/orders/${order.id}">${order.id}</a>
                </td>
                <td>${order.firstName} ${order.lastName}</td>
                <td>${order.contactPhoneNo}</td>
                <td>${order.deliveryAddress}</td>
                <td><tags:dateTimeFormat localeDateTime="${order.creationDate}"/></td>
                <td>${order.totalPrice}$</td>
                <td>${order.status}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</div>
</body>