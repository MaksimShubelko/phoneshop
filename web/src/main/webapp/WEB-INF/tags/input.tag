<%@ tag pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<%@ attribute name="field" required="true"%>
<%@ attribute name="lable" required="true"%>
<%@ attribute description="Supports input and textArea" name="inputType" required="true"%>
<div class="row m-1">
    <div class="col-1">
        <label for="${field}">
            ${lable}
        </label>
    </div>
    <div class="col-3">
        <c:if test="${inputType.equals('textArea')}">
            <spring:textarea id="${field}" path="${field}"/>
        </c:if>
        <c:if test="${inputType.equals('input')}">
            <spring:input id="${field}" path="${field}"/>
        </c:if>
        <spring:errors path="${field}" cssStyle="color: red"/>
    </div>
</div>
