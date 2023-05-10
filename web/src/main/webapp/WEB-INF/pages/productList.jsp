<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<head>
    <tags:templates/>
</head>
<body>
<title>Product list</title>
<div class="container mt-3">
    <hr class="my-2">
    <div class="container-fluid">
        <a href="${pageContext.request.contextPath}/cart">
        <div class="offset-1">
            <h4>Cart</h4>
        </div>
        </a>
        <%--Admin--%>
        <div class="offset-11">
            <a href="${pageContext.request.contextPath}/admin/orders">
                <button>Orders management</button>
            </a>
        </div>
        <div class="row m-auto pe-auto">
            <h6 class="col-sm-1">Items: </h6>
            <div class="col-sm-1" id="totalQuantity">${cart.totalQuantity} </div>
        </div>

        <div class="row m-0 p-0">
            <h6 class="col-sm-1">Price:</h6>
            <div class="col-sm-1" id="totalPrice">${cart.totalPrice}</div>
            <h6 class="col-sm-1">$</h6>
        </div>
    </div>

    <div class="clearfix">
        <h2 class="float-start">Phones</h2>
        <form action="${pageContext.request.contextPath}/productList" class="float-end">
            <div class="input-group mb-3">
                <input name="term" value="${param.term}" type="text" class="form-control form-control-sm"
                       placeholder="Search Here">
                <button class="input-group-text btn-success">Search</button>
            </div>
        </form>
    </div>

    <div class="container">
        <table class="table table-responsive table-sm table-hover">
            <thead class="align-text-top text-center">
            <tr>
                <th class="col-2">Image</th>
                <th class="text-nowrap col-2">
                    Brand
                    <tags:sorting sortingField="brand" sortingType="asc"/>
                    <tags:sorting sortingField="brand" sortingType="desc"/>
                </th>
                <th class="text-nowrap col-2">
                    Model
                    <tags:sorting sortingField="model" sortingType="asc"/>
                    <tags:sorting sortingField="model" sortingType="desc"/>
                </th>
                <th class="col-3">Color</th>
                <th class="col-1">
                    Display size
                </th>
                <th class="col-2">
                    Price
                </th>
                <th class="col-1">Quantity</th>
                <th>Action</th>
            </tr>
            </thead>
            <c:forEach var="phone" items="${phones}">
                <tr>
                    <td class="col-2">
                        <a href="${pageContext.request.contextPath}/productDetails/${phone.id}">
                            <img class="img-small"
                                 src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${phone.imageUrl}">
                        </a>
                    </td>
                    <td class="col-2">${phone.brand}</td>
                    <td class="col-2">${phone.model}</td>
                    </td>
                    <td class="col-3">
                        <c:forEach var="color" items="${phone.colors}" varStatus="colorLoop">
                            ${color.code}
                            <c:if test="${not colorLoop.last}">, </c:if>
                        </c:forEach>
                    </td>
                    <td class="col-1">${phone.displaySizeInches}"</td>
                    <td class="col-3">$ <span id="phone${phone.id}Price">${phone.price}</span></td>
                    <td class="col-1">
                        <input id="phone${phone.id}Quantity" class="form-check-inline" value="1">
                        <input type="hidden" id="phone${phone.id}Id" value="${phone.id}">
                        <p style="color: green" id="quantity${phone.id}Message"></p>
                        <p style="color: red" id="quantity${phone.id}Error"></p>
                    </td>
                    <td class="col-2">
                        <input type="button" onclick="addToCartAjax(${phone.id})" value="To cart">
                    </td>
                </tr>
                <c:set var="dto"></c:set>
            </c:forEach>
        </table>
    </div>
    <tags:pagination selectedPage="${selectedPage}" pagesTotal="${pagesTotal}"/>
</div>
</body>