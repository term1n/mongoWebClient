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
<div class="container-fluid">
    <nav class="navbar navbar-default" role="navigation" id="navigation-panel" style="margin-bottom: 0;">
    </nav>
</div>
<div id="main-region" class="main-region">
</div>
<%--div for connection manager--%>
<div id="database-connection-manager">
</div>
<%--div for common dialogs--%>
<div id="common-dialogs-div">
</div>

<ul class="dropdown-menu" id="database-menu-placeholder" role="menu" style="display:none">
    <li><a tabindex="-1" href="#" class="refreshDatabase">Refresh database</a></li>
    <li class="divider"></li>
    <li><a tabindex="-1" href="#" class="dropDatabase">Drop database</a></li>
</ul>
<ul class="dropdown-menu" id="context-menu-placeholder" role="menu" style="display:none">
    <li><a tabindex="-1" href="#" class="viewCollection">View collection</a></li>
    <li class="divider"></li>
    <li><a tabindex="-1" href="#" class="dropCollection">Drop collection</a></li>
</ul>

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
                                        <input type="text" class="form-control" id="m_c_name" value="{{name}}"/>
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
                                        <input type="text" class="form-control" id="m_c_host" value="{{host}}"/>
                                    </div>
                                    <div class="col-sm-3">
                                        <input type="text" class="form-control" id="m_c_port" value="{{port}}"/>
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

<script type="text/x-handlebars-template" id="common-success-modal-dialog">
    <div class="modal fade" tabindex="-1" role="dialog" aria-labelledby="smallModal" aria-hidden="true">
        <div class="modal-dialog modal-sm">
            <div class="modal-content">
                <div class="modal-header modal-header-success">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title">{{modalHeader}}</h4>
                </div>
                <div class="modal-body">
                    <h3>{{modalBody}}</h3>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                </div>
            </div>
        </div>
    </div>
</script>

<script type="text/x-handlebars-template" id="common-error-modal-dialog">
    <div class="modal fade" tabindex="-1" role="dialog" aria-labelledby="smallModal" aria-hidden="true">
        <div class="modal-dialog modal-sm">
            <div class="modal-content">
                <div class="modal-header modal-header-error">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title">{{modalHeader}}</h4>
                </div>
                <div class="modal-body">
                    <h3>{{modalBody}}</h3>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                </div>
            </div>
        </div>
    </div>
</script>

<script type="text/x-handlebars-template" id="database-layout-template">
    <div class="row">
        <div class="col-md-3 col-xs-3 col-sm-3 col-lg-3 .col-md-offset-0 .col-xs-offset-0 .col-sm-offset-0 .col-lg-offset-0"
             id="dlLeft">
        </div>
        <div class="col-md-9 col-xs-9 col-sm-9 col-lg-9" id="dlRight">
        </div>
    </div>
</script>

<script type="text/x-handlebars-template" id="database-connections-connection-div-template">
    <div id="{{id}}">
    </div>
</script>

<script type="text/x-handlebars-template" id="database-view-template">
    <div class="accordion">
        <div class="panel-group" id="{{connectionName}}" style="margin-bottom:0;">
            <div class="panel panel-default">
                <div class="panel-heading" onclick="$('#top{{connectionName}}').collapse('toggle');">
                    <h4 class="panel-title">
                        <a class="accordion-toggle" data-toggle="collapse" data-parent="#{{connectionName}}"
                           href="#top{{connectionName}}">
                            {{connectionName}}
                        </a>
                    </h4>
                </div>
                <div class="panel-collapse collapse" id="top{{connectionName}}">
                    <div class="panel-body">
                        <div id="child{{connectionName}}" class="panel-group" style="margin-bottom:0;">
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</script>

<script type="text/x-handlebars-template" id="database-element-template">
    <div class="panel-heading" onclick="$('#{{dataParent}}{{dbName}}').collapse('toggle');">
        <h4 class="panel-title">
            <a class="accordion-toggle" data-toggle="collapse" data-parent="#{{dataParent}}"
               href="#{{dataParent}}{{dbName}}">
                {{dbName}}
            </a>
        </h4>
    </div>
    <div class="panel-collapse collapse" id="{{dataParent}}{{dbName}}">
        <div class="panel-body" style="padding:0">
            <div class="list-group" style="margin-bottom:0;">
                {{#each collectionNames}}
                <a class="list-group-item" style="border:0;">{{this}}</a>
                {{/each}}
            </div>
        </div>
    </div>
</script>

<script type="text/javascript">
    MongoWebClient.start();
</script>

</body>
</html>
