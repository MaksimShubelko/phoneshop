<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <tags:templates/>
</head>
<body>
<title>Order overview</title>
<div class="container mt-1 my-2">
    <form method="post" id="deleteItem"></form>

    <%--Not authorized--%>
    <form method="get" action="${pageContext.request.contextPath}/productList">
        <input type="submit" class="button button-primary" value="Back to shopping"/>
    </form>

    <%--Role: admin--%>
    <div class="row">
        <div class="col-4">
            <h1>Order number: ${order.serialNo}</h1>
        </div>
        <div class="col-3 offset-5">
            <h1>Status: ${order.status}</h1>
        </div>
    </div>

    <%--Not authorized--%>
    <h1>Thank you for your order! Order number: ${order.serialNo}</h1>

    <%--Permit all--%>
    <c:if test="${cartItems.size() != 0}">
        <table class="table table-sm table-hover">
            <div class="row">
                <thead>
                <tr>
                    <th class="col-2">
                        Brand
                    </th>
                    <th class="col-2">
                        Model
                    </th>
                    <th class="col-2">
                        Color
                    </th>
                    <th class="col-2">
                        Display size
                    </th>
                    <th class="col-1">
                        Quantity
                    </th>
                    <th class="col-2">
                        Price
                    </th>
                </tr>
                </thead>
            </div>
            <tbody>
            <c:forEach var="item" items="${order.orderItems}">
                <div class="row">
                    <tr>
                        <td class="col-2">${item.phone.brand}</td>
                        <td class="col-2">${item.phone.model}</td>
                        <td class="col-2">
                            <c:forEach items="${item.phone.colors}" var="color" varStatus="status">
                                ${color.code}
                                <c:if test="${not status.last}">,
                                </c:if>
                            </c:forEach>
                        </td>
                        <td class="col-2">${item.phone.displaySizeInches}''</td>
                        <td class="col-1">${item.quantity}</td>
                        <td class="col-2">${item.phone.price}$</td>
                    </tr>
                </div>
            </c:forEach>
            <tr class="col-offset">
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td>
                    <table class="table table-success">
                        <tbody>
                        <tr>
                            <th class="table-success">Subtotal:</th>
                            <td>${order.subtotal}$</td>
                        </tr>
                        <tr>
                            <th class="table-success">Delivery Price:</th>
                            <td>${order.deliveryPrice}$</td>
                        </tr>
                        <tr class="">
                            <th class="table-success">Total price:</th>
                            <td>${order.totalPrice}$</td>
                        </tr>
                        </tbody>
                    </table>
                </td>
            </tr>
            </tbody>
        </table>
        <div class="row">
            First name: ${order.firstName}
        </div>
        <div class="row">
            Last name: ${order.lastName}
        </div>
        <div class="row">
            Delivery address: ${order.deliveryAddress}
        </div>
        <div class="row">
            Contact phone number: ${order.contactPhoneNo}
        </div>
        <div class="row">
            Additional information: ${order.additionalInf == null ? '-' : order.additionalInf}
        </div>
    </c:if>

    <%--Role: admin--%>
    <div class="row">
        <div class="col-2">
            <a href="${pageContext.request.contextPath}/admin/orders/">
                <button>Back</button>
            </a>
        </div>
        <form method="post"
              action="${pageContext.request.contextPath}/admin/orders/${order.serialNo}">
            <c:if test="${order.status == 'New'}">
                <div class="row">
                    <div class="col-2">
                        <input type="submit" name="status" value="DELIVERED"/>
                    </div>
                    <div class="col-2">
                        <input type="submit" name="status" value="REJECTED"/>
                    </div>
                </div>
            </c:if>
        </form>
    </div>

</div>
</body>
</head>
<body>
</body>
</html>
