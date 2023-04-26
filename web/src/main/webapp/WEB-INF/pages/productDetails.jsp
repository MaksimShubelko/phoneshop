<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<head>
    <tags:templates/>
</head>
<body>
<title>Product details</title>
<div class="container mt-1">
    <th class="my-2">
        <div>
            <a href="${pageContext.request.contextPath}/productList">
                <input type="button" class="button button-success" value="Back to products">
            </a>
            <a href="${pageContext.request.contextPath}/cart">
                <div class="offset-1">
                    <h4>Cart</h4>
                </div>
            </a>
            <div class="row m-auto pe-auto">
                <h6 class="col-sm-1">Items: </h6>
                <div class="col-sm-1" id="totalQuantity">${cart.totalQuantity}</div>
            </div>
            <div class="row m-0 p-0">
                <h6 class="col-sm-1">Price:</h6>
                <div class="col-sm-1" id="totalPrice">${cart.totalPrice}</div>
                <h6 class="col-sm-1">$</h6>
            </div>
        </div>
        <div class="table table-striped">
            <h1 class="table table-striped">Phone</h1>
            <div class="mt-3 container-fluid">
                <div class="row">
                    <div class="col-3">
                        <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${phone.imageUrl}">
                    </div>
                    <div class="col-3 offset-1">
                        <table class="table table-sm table-primary bordered">
                            <thead class="text">
                            Display
                            </thead>
                            <thead>
                            </thead>
                            <tbody>
                            <tr>
                                <td>
                                    Size
                                </td>
                                <td>
                                    ${phone.displaySizeInches}''
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    Resolution
                                </td>
                                <td>
                                    ${phone.displayResolution}
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    Technology
                                </td>
                                <td>
                                    ${phone.displayTechnology}
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    Pixel density
                                </td>
                                <td>
                                    ${phone.pixelDensity}
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="row">
                    <div class="col-3">
                        ${phone.description}
                    </div>
                    <div class="col-3 offset-1">
                        <table class="table table-sm table-primary bordered">
                            <thead class="text">
                            Dimensions & weight
                            </thead>
                            <thead>
                            </thead>
                            <tbody>
                            <tr>
                                <td>
                                    Length
                                </td>
                                <td>
                                    ${phone.lengthMm}mm
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    Width
                                </td>
                                <td>
                                    ${phone.widthMm}mm
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    Color
                                </td>
                                <td>
                                    <c:forEach items="${phone.colors}" var="color" varStatus="status">
                                        ${color.code}
                                        <c:if test="${not status.last}">
                                            ,
                                        </c:if>
                                    </c:forEach>

                                </td>
                            </tr>
                            <tr>
                                <td>
                                    Weight
                                </td>
                                <td>
                                    ${phone.weightGr}gr
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                    <div class="row">
                        <div class="col-3 border border-primary rounded">
                            <h3>
                                <p>Price: <span id="phone${phone.id}Price">${phone.price}</span></p>
                            </h3>
                            <label for="phone${phone.id}Quantity">Quantity: </label>
                            <input id="phone${phone.id}Quantity" class="form-check-inline" value="1">
                            <input type="hidden" id="phone${phone.id}Id" value="${phone.id}">
                            <p style="color: green" id="quantity${phone.id}Message"></p>
                            <p style="color: red" id="quantity${phone.id}Error"></p>
                            <input type="button" onclick="addToCartAjax(${phone.id})" value="To cart">
                        </div>
                        <div class="col-3 offset-1">
                            <table class="table table-sm table-primary bordered">
                                <thead class="text">
                                Camera
                                </thead>
                                <thead>
                                </thead>
                                <tbody>
                                <tr>
                                    <td>
                                        Front
                                    </td>
                                    <td>
                                        ${phone.frontCameraMegapixels} megapixels
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        Back
                                    </td>
                                    <td>
                                        ${phone.backCameraMegapixels} megapixels
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-3 offset-4">
                            <table class="table table-sm table-primary bordered">
                                <thead class="text">
                                Battery
                                </thead>
                                <thead>
                                </thead>
                                <tbody>
                                <tr>
                                    <td>
                                        Talk time
                                    </td>
                                    <td>
                                        ${phone.talkTimeHours} hours
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        Stand by time
                                    </td>
                                    <td>
                                        ${phone.standByTimeHours} hours
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        Battery capacity
                                    </td>
                                    <td>
                                        ${phone.batteryCapacityMah}mAh
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-3 offset-4">
                            <table class="table table-sm table-primary bordered">
                                <thead class="text">
                                Other
                                </thead>
                                <thead>
                                </thead>
                                <tbody>
                                <tr>
                                    <td>
                                        Device type
                                    </td>
                                    <td>
                                        ${phone.deviceType}
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        Bluetooth
                                    </td>
                                    <td>
                                        ${phone.bluetooth}
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </th>
</div>
</body>