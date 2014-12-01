<%--
  Created by IntelliJ IDEA.
  User: manaev
  Date: 13.10.14
  Time: 17:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <script src="<c:url value="/resources/scripts/libs/jquery2.1.1.js"/>"></script>
    <script src="<c:url value="/resources/scripts/libs/jquery-ui-1.10.3.custom.min.js"/>"></script>
    <script src="<c:url value="/resources/scripts/libs/underscore-min.js"/>"></script>
    <script src="<c:url value="/resources/scripts/libs/backbone-min.js"/>"></script>
    <script src="<c:url value="/resources/scripts/libs/backbone.marionette.js"/>"></script>
    <script src="<c:url value="/resources/scripts/libs/handlebars-v2.0.0.js"/>"></script>
    <script src="<c:url value="/resources/scripts/libs/bootstrap.3.2.0.js"/>"></script>
    <link href="<c:url value="/resources/css/bootstrap.3.2.0.css"/>" rel="stylesheet"/>
    <%--custom styles--%>
    <link href="<c:url value="/resources/css/mongoWebClient.css"/>" rel="stylesheet"/>

    <%--custom scripts--%>
    <script src="<c:url value="/resources/scripts/js/app.js"/>"></script>

    <script src="<c:url value="/resources/scripts/js/templates/mongoWebClientTemplates.js"/>"></script>
    <script src="<c:url value="/resources/scripts/js/views/navigation/NavigationPanelView.js"/>"></script>
    <script src="<c:url value="/resources/scripts/js/views/navigation/NavigationPanelController.js"/>"></script>

    <script src="<c:url value="/resources/scripts/js/views/dialogs/CommonDialogBoxController.js"/>"></script>
    <script src="<c:url value="/resources/scripts/js/views/dialogs/CommonDialogBoxView.js"/>"></script>


    <script src="<c:url value="/resources/scripts/js/views/dcm/DatabaseConnectionView.js"/>"></script>
    <script src="<c:url value="/resources/scripts/js/views/dcm/DatabaseConnectionController.js"/>"></script>

    <script src="<c:url value="/resources/scripts/js/views/database/models/DatabaseCollection.js"/>"></script>
    <script src="<c:url value="/resources/scripts/js/views/database/DatabaseLayout.js"/>"></script>
    <script src="<c:url value="/resources/scripts/js/views/database/DatabaseLayoutController.js"/>"></script>
    <script src="<c:url value="/resources/scripts/js/views/database/parts/DatabaseView.js"/>"></script>
    <script src="<c:url value="/resources/scripts/js/views/database/models/CollectionEntity.js"/>"></script>
    <script src="<c:url value="/resources/scripts/js/views/database/parts/DbContentLayoutController.js"/>"></script>
    <script src="<c:url value="/resources/scripts/js/views/database/parts/DbContentView.js"/>"></script>
    <script src="<c:url value="/resources/scripts/js/views/database/parts/DbContentLayout.js"/>"></script>


</head>
</html>
