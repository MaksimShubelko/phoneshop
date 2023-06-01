<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <tags:templates/>
    <title>Quick order</title>
</head>
<body>
<div class="container mt-3">
    <a href="${pageContext.request.contextPath}/productList">
        Back to products
    </a>
    <div class="row">
        <c:if test="${addedProducts != null}">
            <c:forEach var="addedItem" items="${addedProducts}" varStatus="itemsLoop">
                ${addedItem}
                <c:if test="${not itemsLoop.last}">, </c:if>
            </c:forEach>
            <c:if test="${addedProducts.size() != 0}">
                <p>added successfully</p>
            </c:if>
        </c:if>
    </div>
    <c:if test="${errorMsg != null}">
        ${errorMsg}
    </c:if>
    <c:if test="${successMsg != null}">
        ${successMsg}
    </c:if>
    <form:form method="post" action="${pageContext.request.contextPath}/quickOrder" modelAttribute="orderItems">
        <div class="row p-4">
            <p class="col-4">Model name</p>
            <p class="col-4">Quantity</p>
        </div>
        <c:forEach begin="0" end="7" varStatus="status" step="1">
            <div class="row p-3">
                <c:set var="index" value="${status.index}"/>
                <div class="col-3">
                    <form:input path="items[${index}].phoneModel"/>
                    <form:errors path="items[${index}].phoneModel"/>
                </div>
                <div class="col-3">
                    <form:input path="items[${index}].quantity"/>
                    <form:errors path="items[${index}].quantity"/>
                </div>
            </div>
        </c:forEach>
        <button type="submit">Add to cart</button>
    </form:form>
</div>
</body>
</html>
