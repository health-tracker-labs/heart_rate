<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Login</title>
    <link href="<c:url value="/css/main.css" />" rel="stylesheet">
</head>
<body>
<div class="logFormDiv">
<h2>LOGIN</h2>
<form action="login" method="post" class="centeredLoginForm">
    <c:if test="${param.error != null}">
        <p style='color:red'>
            Invalid username and password.
        </p>
    </c:if>
    <c:if test="${param.logout != null}">
        <p style='color:blue'>
            You have been logged out.
        </p>
    </c:if>
    <p>
        <label for="username">Username</label>
        <input type="text" id="username" name="username"/>
    </p>
    <p>
        <label for="password">Password</label>
        <input type="password" id="password" name="password"/>
    </p>
    <input type="hidden"
           name="${_csrf.parameterName}"
           value="${_csrf.token}"/>
    <button type="submit">Log in</button>
</form>
</div>
</body>

</html>