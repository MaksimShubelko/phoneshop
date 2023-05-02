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
        <input type="submit" class="button button-primary" value="Back to products"/>
    </form>
    <c:if test="${cartItems.size() == 0}">
        Cart empty
    </c:if>

    <form:form method="post" action="${pageContext.request.contextPath}/cart/update" modelAttribute="cartItemsDto">
        <c:if test="${cartItems.size() != 0}">
            <table class="table table-responsive table-sm table-hover">
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
                        <th class="col-2">
                            Price
                        </th>
                        <th class="col-1">
                            Quantity
                        </th>
                        <th class="col-1">
                            Action
                        </th>
                    </tr>
                    </thead>
                </div>
                <tbody>
                <c:forEach var="item" items="${cartItems}">
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
                            <td class="col-2">${item.phone.price}</td>
                            <td class="col-1">
                                <input type="number" min="1" name='cartItems[${item.phone.id}]'
                                       value='${item.quantity}'/>

                                <c:if test="${updated == true}">
                                <c:set var="id">cartItems[${item.phone.id}]</c:set>
                                <p style="color: red">${errors[id]}</p>
                                <c:if test="${errors[id] == null}">
                                <p style="color: green">Successfully updated</p>
                                </c:if>
                                </c:if>
                            <td class="col-1">
                                <button type="submit" form="deleteItem"
                                        formaction="${pageContext.request.contextPath}/cart/delete/${item.phone.id}">
                                    Delete
                                </button>
                            </td>
                        </tr>
                    </div>
                </c:forEach>
                </tbody>
            </table>
            <button type="submit">Update</button>
            <a href="${pageContext.request.contextPath}/order">
                <button type="button">Order</button>
            </a>
        </c:if>
    </form:form>
</div>
</body>