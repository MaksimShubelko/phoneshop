<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<html>
<head>
    <title>Login page</title>
</head>
<body>
<form method="post" action="${pageContext.request.contextPath}/login">
    <div>
        <p class="text-danger">
            ${error}
        </p>
        <table>
            <tbody>
            <tr>
                <td>
                    <label for="username">Username:</label>
                </td>
                <td>
                    <input type="text" id="username" name="username">
                </td>
            </tr>
            <tr>
                <td>
                    <label for="username">Password:</label>
                </td>
                <td>
                    <input type="password" id="password" name="password">
                </td>
            </tr>
            <tr>
                <td>
                    <input type="submit" value="Sign in">
                    <a href="${pageContext.request.contextPath}/productList">back to products</a>
                </td>
            </tr>
            </tbody>
        </table>

    </div>
</form>
</body>
</html>
