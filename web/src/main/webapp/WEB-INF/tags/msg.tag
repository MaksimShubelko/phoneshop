<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div>
    <c:if test="${pageContext.request.parameterMap.get('msg') != null}">
        <c:if test="${pageContext.request.parameterMap.get('msg')[0] != null}">
            ${pageContext.request.parameterMap.get("msg")[0]}
        </c:if>
    </c:if>
</div>