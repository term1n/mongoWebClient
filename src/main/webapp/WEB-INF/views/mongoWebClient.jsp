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
<nav class="navbar navbar-default" role="navigation" id="navigation-panel" style="margin-bottom: 0;">
</nav>
<div id="main-region" class="main-region">
</div>
<%--div for connection manager--%>
<div id="database-connection-manager">
</div>

<script type="text/x-handlebars-template" id="dmc-modal-dialog">
    <div class="modal fade dmcModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header h-cursor-pointer">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title">Connection settings</h4>
                </div>
                <div class="modal-body min-height-dialog">
                    <div class="container-fluid connection-tabs" style="padding-bottom:20px;">
                        <ul class="nav nav-pills" data-tabs="tabs">
                            <li class="active"><a href="#connection-connect" data-toggle="tab">Connection</a></li>
                            <li><a href="#connection-auth-connect" data-toggle="tab">Authentication</a></li>
                        </ul>
                    </div>
                    <div class="tab-content">
                        <div class="tab-pane container-fluid active" id="connection-connect">
                            <div class="form-horizontal t-a-l">
                                <div class="form-group">
                                    <div class="col-sm-2">
                                        <label class="control-label">Name</label>
                                    </div>
                                    <div class="col-sm-6">
                                        <input type="text" class="form-control" id="m_c_name"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-sm-12">
                                        <p>Choose any connection name that will you to identify this
                                            connection</p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-sm-2">
                                        <label class="control-label">Address</label>
                                    </div>
                                    <div class="col-sm-6">
                                        <input type="text" class="form-control" id="m_c_host"/>
                                    </div>
                                    <div class="col-sm-3">
                                        <input type="text" class="form-control" id="m_c_port"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-sm-12">
                                        <p>Specify host and port of MongoDB server. Host must be IP.</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="tab-pane container-fluid" id="connection-auth-connect">
                            <div class="form-group">
                                <div class="col-sm-12">
                                    <p>Will appear in next version</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <div class="form-group">
                        <div class="col-lg-4 t-a-l">
                            <button type="button" class="btn btn-primary" id="test_connection">Test</button>
                        </div>
                        <div class="col-lg-6 t-a-r">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                        </div>
                        <div class="col-lg-2">
                            <button type="button" class="btn btn-primary" id="button_connect">Connect</button>
                        </div>
                    </div>
                </div>
            </div>
            <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
    </div>
</script>

<script type="text/javascript">
    MongoWebClient.start();
</script>

</body>
</html>
