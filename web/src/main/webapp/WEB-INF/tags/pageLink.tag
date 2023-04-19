<%@ tag pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="page" required="true" %>

<c:url value="/productList">
    <c:if test="${not empty param.sortingField}">
        <c:param name="sortingField" value="${param.sortingField}"/>
    </c:if>

    <c:if test="${not empty param.sortingType}">
        <c:param name="sortingType" value="${param.sortingType}"/>
    </c:if>

    <c:param name="page" value="${page}"/>
    <c:if test="${not empty param.term}">
        <c:param name="term" value="${param.term}"/>
    </c:if>
</c:url>