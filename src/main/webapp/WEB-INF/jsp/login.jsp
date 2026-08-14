<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
        <c:if test="${param.error != null}">
            <div style="margin: auto; width: 65%">
                <p style='color:red'>
                    Invalid username or password.
                </p>
            </div>
        </c:if>
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
</body>
</html>