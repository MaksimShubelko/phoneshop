<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ attribute name="localeDateTime" required="true" %>
<fmt:parseDate value="${localeDateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both" />
<fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${parsedDateTime}"/>
