<%@ tag pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="sortingField" required="true" %>
<%@ attribute name="sortingType" required="true" %>

<a class="col-1" href="
    <c:url value="/productList">
        <c:param name="sortingField" value="${sortingField}"/>
        <c:param name="sortingType" value="${sortingType}"/>
        <c:if test="${not empty param.term}">
            <c:param name="term" value="${param.term}"/>
        </c:if>
    </c:url>">

    <h6 class="text-success">${sortingType eq "asc" ? "↑" : "↓"}</h6>
</a>