<%--
  Created by IntelliJ IDEA.
  User: manaev
  Date: 13.10.14
  Time: 14:17
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="/WEB-INF/views/header.jsp" %>
<html>
<head>
    <title>Mongo Web Client</title>
</head>
<body>
<%--main regions of mongo web client interface--%>

<%--navigation panel--%>
<nav class="navbar navbar-inverse" role="navigation" id="navigation-panel">
</nav>

<%--div for connection manager--%>
<div id="database-connection-manager">
</div>

<script type="text/javascript">
    MongoWebClient.start();
</script>

</body>
</html>
