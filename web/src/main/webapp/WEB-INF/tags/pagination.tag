<%@ tag pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="selectedPage" type="java.lang.Integer" required="true" %>
<%@ attribute name="pagesTotal" type="java.lang.Integer" required="true" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="offsetPageNum" value="7"/>
<html>
<body>
<nav aria-label="navigation">
    <ul class="pagination justify-content-center">
        <li class="page-item ${selectedPage eq 1 ? "disabled": ""}">
            <a class="page-link" href="<tags:pageLink page="${selectedPage-1}"/>" aria-label="Previous">Previous</a>
        </li>

        <c:forEach begin="${selectedPage - offsetPageNum > 0 ? selectedPage - offsetPageNum : 1}"
                   end="${selectedPage + offsetPageNum < pagesTotal ? selectedPage + offsetPageNum : pagesTotal}" var="page">
            <li class="page-item ${selectedPage eq page ? "active" :""}">
                <a class="page-link" href="<tags:pageLink page="${page}"/>">${page}</a>
            </li>
        </c:forEach>

        <li class="page-item ${selectedPage eq pagesTotal ? "disabled": ""}">
            <a class="page-link" href="<tags:pageLink page="${selectedPage+1}"/>" aria-label="Next">Next</a>
        </li>
    </ul>
</nav>
</body>
</html>