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
    <form method="get" action="${pageContext.request.contextPath}/cart">
        <input type="submit" class="button button-primary" value="Back to cart"/>
    </form>
    <form:form method="post" action="${pageContext.request.contextPath}/order" modelAttribute="orderDto">
        <c:if test="${order.orderItems.size() != 0}">
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
                            <tr>
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
                <tags:input inputType="input" lable="First name" field="firstname"/>
            </div>
            <div class="row">
                <tags:input inputType="input" lable="Last name" field="lastname"/>
            </div>
            <div class="row">
                <tags:input inputType="input" lable="Delivery address" field="deliveryAddress"/>
            </div>
            <div class="row">
                <tags:input inputType="input" lable="Phone No" field="contactPhoneNo"/>
            </div>
            <div class="row">
                <tags:input inputType="textArea" lable="Additional information" field="${orderDto.additionalInf}"/>
            </div>
            <button type="submit">Order</button>
        </c:if>
    </form:form>
</div>
</body>