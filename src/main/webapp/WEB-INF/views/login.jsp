<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/WEB-INF/views/header.jsp" %>
<html>
<head>
    <title></title>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-sm-6 col-md-4 col-md-offset-4">
            <h1 class="text-center login-title">Sign in to continue to Bootsnipp</h1>
            <div class="account-wall">
                <img class="profile-img" src="http://forum.minecraft-galaxy.ru/img/avatars/213688"
                     alt="">

                <div class="container-fluid">

                    <c:if test="${not empty error}">
                        <div class="alert alert-danger" role="alert">
                            <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                            <span class="sr-only">Error:</span>
                                ${error}
                        </div>
                    </c:if>
                    <c:if test="${not empty msg}">
                        <div class="alert alert-info" role="alert">
                            <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                            <span class="sr-only">Error:</span>
                                ${msg}
                        </div>
                    </c:if>
                </div>

                <form name='loginForm' class="form-signin" action="<c:url value='j_spring_security_check' />" method='POST'>
                    <input type="text" name='username' class="form-control" placeholder="login" required autofocus>
                    <input type="password" name='password' class="form-control" placeholder="password" required>
                    <button class="btn btn-lg btn-primary btn-block" type="submit" name="submit">
                        Sign in</button>
                    <input type="hidden" name="${_csrf.parameterName}"
                           value="${_csrf.token}" />
                </form>
            </div>
            <a href="#" class="text-center new-account">Create an account </a>
        </div>
    </div>
</div>

</body>
</html>