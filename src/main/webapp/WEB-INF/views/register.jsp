<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ include file="/WEB-INF/views/header.jsp" %>
<html>
<head>
    <title>MongoWebClient</title>
</head>
<body>
<nav id="navigation-panel" class="navbar navbar-inverse" role="navigation">
    <div class="container-fluid">
        <div class='navbar-header'>
            <span class='navbar-brand'>MongoWebClient</span>
        </div>
        <div class='collapse navbar-collapse'>
            <ul class='nav navbar-nav'>
                <li id='create-connection'>
                    <a href="<c:url value="/login" />"> <i class="fa fa-user"></i> Login</a>
                </li>
            </ul>
        </div>
    </div>
</nav>

<div class="container">
    <div class="row">
        <div class="col-sm-6 col-md-4 col-md-offset-4">
            <div class="account-wall">
                <div class="container-fluid">
                    <c:forEach var="err" items="${error}">
                        <div class="alert alert-danger" role="alert" style="margin-bottom:0px;">
                            <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                            <span class="sr-only">Error:</span>
                                ${err.field}  ${err.defaultMessage}
                        </div>
                    </c:forEach>
                </div>
                <form:form role="form" class="form-signin"
                           action="${pageContext.request.contextPath}/registerUser"
                           modelAttribute="user" method="POST" enctype="utf8">
                    <div class="form-group has-feedback has-feedback-left" style="margin-bottom: 10px;">
                        <label class="control-label">Username</label>
                        <form:input type="text" path='username' class="form-control" placeholder="login"/>
                        <i class="form-control-feedback glyphicon glyphicon-user"></i>
                    </div>
                    <div class="form-group has-feedback has-feedback-left" style="margin-bottom: 10px;">
                        <label class="control-label">Password</label>
                        <form:input type="password" path='password' class="form-control" placeholder="password"/>
                        <i class="form-control-feedback glyphicon glyphicon-lock "></i>
                    </div>
                    <div class="form-group has-feedback has-feedback-left" style="margin-bottom: 20px;">
                        <label class="control-label">eMail</label>
                        <form:input type="eMail" path='eMail' class="form-control" placeholder="eMail"/>
                        <i class="form-control-feedback glyphicon glyphicon-lock "></i>
                    </div>
                    <button class="btn btn-lg btn-primary btn-block" type="submit" name="submit">
                        Submit
                    </button>
                </form:form>
            </div>
        </div>
    </div>
</div>

</body>
</html>