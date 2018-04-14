<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>--%>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="webjars/bootstrap/3.3.6/css/bootstrap.min.css"/>
</head>
<body>
<div class="col-md-6 col-md-offset-4">
    <form action="login" method="post" class="form-horizontal">
        <div class="form-group">
            <h3 style="color:black">Enter username and password</h3>
        </div>
        <div class="form-group">
            <label class="control-label col-sm-2" for="usernameID">Username:</label>
            <div class="col-sm-5">
                <input type="text" class="form-control" name="username" id="usernameID"
                       placeholder="Enter Username" ng-model="username"/>
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-sm-2" for="passwordID">Password:</label>
            <div class="col-sm-5">
                <input type="password" class="form-control" name="password" id="passwordID"
                       placeholder="Enter Password" ng-model="password"/>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button type="submit" class="btn btn-default">Log in</button>
            </div>
        </div>
    </form>
</div>
<%--<div class="container">
&lt;%&ndash;<h2>LOGIN</h2>&ndash;%&gt;
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
        <input type="text" id="username" name="username" />
    </p>
    <p>
        <label for="password">Password</label>
        <input type="password" id="password" name="password" />
    </p>
    <input type="hidden"
           name="${_csrf.parameterName}"
           value="${_csrf.token}"/>
    <button type="submit">Log in</button>
</form>
</div>--%>
</body>
</html>