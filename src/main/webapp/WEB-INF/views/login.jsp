<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/views/header.jsp" %>
<html>
<head>
    <title>MongoWebClient</title>
</head>
<body>
<nav id="navigation-panel" class="navbar navbar-inverse" role="navigation">
    <div class="container-fluid">
        <div class='navbar-header'>
            <span class='navbar-brand' id='mWcAppName'>MongoWebClient</span>
        </div>
        <div class='collapse navbar-collapse'>
            <ul class='nav navbar-nav'>
                <li>
                    <a href="<c:url value="/registration" />"> <i class="fa fa-user"></i> Create an account </a>
                </li>
            </ul>
        </div>
    </div>
</nav>

<div class="container">
    <div class="row">
        <div class="col-sm-6 col-md-4 col-md-offset-4">
            <div class="account-wall">
                <img class="profile-img" src="<c:url value="/resources/css/kuakamon.png" />"
                     alt="">

                <div class="container-fluid">
                    <c:if test="${not empty error}">
                        <div class="alert alert-danger" role="alert" style="margin-bottom:0px;">
                            <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                            <span class="sr-only">Error:</span>
                                ${error}
                        </div>
                    </c:if>
                    <c:if test="${not empty msg}">
                        <div class="alert alert-info" style="margin-bottom:0px;" role="alert">
                            <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                            <span class="sr-only">Error:</span>
                                ${msg}
                        </div>
                    </c:if>
                </div>
                <form name='loginForm' role="form" class="form-signin"
                      action="<c:url value='j_spring_security_check' />"
                      method='POST'>
                    <div class="form-group has-feedback has-feedback-left" style="margin-bottom: 10px;">
                        <label class="control-label">Username</label>
                        <input type="text" name='username' class="form-control" placeholder="login" required
                               autofocus/>
                        <i class="form-control-feedback glyphicon glyphicon-user"></i>
                    </div>
                    <div class="form-group has-feedback has-feedback-left" style="margin-bottom: 20px;">
                        <label class="control-label">Password</label>
                        <input type="password" name='password' class="form-control" placeholder="password" required>
                        <i class="form-control-feedback glyphicon glyphicon-lock "></i>
                    </div>
                    <button class="btn btn-lg btn-primary btn-block" type="submit" name="submit">
                        Sign in
                    </button>
                    <input type="hidden" name="${_csrf.parameterName}"
                           value="${_csrf.token}"/>
                </form>
            </div>
            <%--<a href="#" class="text-center new-account">Create an account </a>--%>
        </div>
    </div>
</div>

</body>
</html>