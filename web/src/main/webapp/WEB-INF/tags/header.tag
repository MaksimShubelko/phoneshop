<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div class="offset-9 pt-2">
    <sec:authorize access="isAuthenticated() and hasRole('ADMINISTRATOR')">
        <sec:authentication var="username" property="principal.username"/>
        <div class="row">
            <div class="col-3 text-nowrap">
                Hello, ${username}
            </div>
            <div class="col-1">
                <a href="${pageContext.request.contextPath}/logout">
                    <button type="button" class="button-primary">Logout</button>
                </a>
            </div>
        </div>
    </sec:authorize>
    <sec:authorize access="isAnonymous()">
        <div class="offset-3">
            <a href="${pageContext.request.contextPath}/login">
                <button type="button" class="button-primary">Login</button>
            </a>
        </div>
    </sec:authorize>
</div>
