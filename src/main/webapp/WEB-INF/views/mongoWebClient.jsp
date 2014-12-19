<%--
  Created by IntelliJ IDEA.
  User: manaev
  Date: 13.10.14
  Time: 14:17
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page session="true" %>
<%@ include file="/WEB-INF/views/header.jsp" %>
<c:url value="/j_spring_security_logout" var="logoutUrl"/>
<html>
<head>
    <title>Mongo Web Client</title>
</head>
<body>
<%--simple security cred--%>
<div style="display: none">
    <form action="${logoutUrl}" method="post" id="logoutForm">
        <input type="hidden"
               name="${_csrf.parameterName}"
               value="${_csrf.token}"/>
    </form>
</div>
<%--main regions of mongo web client interface--%>

<%--navigation panel--%>

<nav class="navbar navbar-inverse" role="navigation" id="navigation-panel">
</nav>

<div id="main-region" class="main-region container-fluid">
</div>

<%--for manual--%>
<div id="manual-region" class="main-region container-fluid">
</div>

<%--div for connection manager--%>
<div id="database-connection-manager">
</div>
<%--div for common dialogs--%>
<div id="common-dialogs-div">
</div>

<%--div for common dialogs--%>
<div id="confirm-dialog-div">
</div>

<%--div for editing dialog--%>
<div id="edit-entry-dialog-div">
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

<ul class="dropdown-menu" id="entry-menu-placeholder" role="menu" style="display:none">
    <li><a tabindex="-1" href="#" class="editEntry">Edit entry</a></li>
    <li><a tabindex="-1" href="#" class="viewEntry">View entry</a></li>
    <li class="divider"></li>
    <li><a tabindex="-1" href="#" class="deleteEntry">Delete entry</a></li>
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
                            <button type="button" class="btn btn-info" id="test_connection">Test</button>
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

<script type="text/x-handlebars-template" id="edit-entry-modal-dialog">
    <div class="modal fade" tabindex="-1" role="dialog" aria-labelledby="largeModal" aria-hidden="true">
        <div class="modal-dialog modal-lg mwc-modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <button type="button" class="maximize" aria-hidden="true">&curren;</button>
                    <h4 class="modal-title">Edit entry id: <span style="font-weight:bold">{{header}}</span></h4>
                </div>
                <div class="modal-body t-a-l">
                    <pre class="mwc-wrap mwc-edit-sm-h" contenteditable="true">
                            {{json data}}
                    </pre>
                </div>
                <div class="modal-footer">
                    <div class="form-group">
                        <div class="col-lg-4 t-a-l">
                            <button type="button" class="btn btn-info btn-lg validate">Validate
                            </button>
                        </div>
                        <div class="col-lg-6 t-a-r">
                            <button type="button" class="btn btn-default btn-lg" data-dismiss="modal">Cancel</button>
                        </div>
                        <div class="col-lg-2">
                            <button type="button" class="btn btn-danger save btn-lg">Save</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
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

<script type="text/x-handlebars-template" id="common-confirm-modal-dialog">
    <div class="modal fade" tabindex="-1" role="dialog" aria-labelledby="smallModal" aria-hidden="true">
        <div class="modal-dialog modal-sm">
            <div class="modal-content panel-warning">
                <div class="modal-header panel-heading modal-header-confirm">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title">{{modalHeader}}</h4>
                </div>
                <div class="modal-body">
                    <div class="container-fluid">
                        {{modalBodyText}}
                        <span class="alert-danger">{{modalBodyDanger}} </span>
                        ?
                    </div>
                </div>
                <div class="modal-footer">
                    <div class="col-lg-6 t-a-l">
                        <button type="button" class="btn btn-default" data-dismiss="modal">No</button>
                    </div>
                    <div class="col-lg-6 t-a-r">
                        <button type="button" class="btn btn-warning btn-yes">Yes</button>
                    </div>
                </div>
                <div class="modal-footer">
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

<script type="text/x-handlebars-template" id="database-collection-attributes-view-template">
    <div class="panel-body">
        <div class="container-fluid" style="padding-left:0px;padding-bottom:15px;">
            <span class="fa fa-desktop padding-lr-5"></span> {{host}}:{{port}} <span
                class="fa fa-database padding-lr-5"> </span> {{dbName}} <span
                class="fa fa-folder-o padding-lr-5"></span>
            {{collName}}
            <span class="fa fa-info-circle padding-lr-5"> Total: </span><span class="totalHolder">{{colSize}}</span>
        </div>
        <div class="container-fluid" style="padding-left:0px;">
            <div class="col-lg-2 col-sm-2 col-xs-2" style="padding-left:0px;">
                <button type="button" class="btn btn-default mwc-refresh ">
                    <span class="fa fa-refresh"> Refresh</span>
                </button>
            </div>
            <div class="col-lg-3 col-sm-3 col-xs-3" style="float:right;">
                <div class="col-lg-6 col-sm-6 col-xs-6" style="padding-right:2px;">
                    <div class="input-group">
                             <span class="input-group-addon h-cursor-pointer mwc-prevPage">
                                 <span class=" fa fa-chevron-left" aria-hidden="true"></span>
                             </span>
                        <input type="text" class="form-control mwc-skip" value="{{skip}}"/>
                    </div>
                </div>
                <div class="col-lg-6 col-sm-6 col-xs-6" style="padding-left:2px;">
                    <div class="input-group">
                        <input type="text" class="form-control mwc-limit" value="{{limit}}"/>
                             <span class="input-group-addon h-cursor-pointer mwc-nextPage">
                                 <span class=" fa fa-chevron-right"></span>
                             </span>
                    </div>
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

<script type="text/x-handlebars-template" id="database-console-template">
    <div class="panel-body st_console">
        <div class="container-fluid" style="padding-left:0px;">
            <span class="fa fa-play hoverable-scale mwc-console-execute" style="padding-right:10px;"></span>
            <span>{{dbName}}.{{collName}}.</span>&nbsp;
            <span contenteditable="true" class="mwc-console-operation">find</span>&nbsp;
            <span>.(</span>&nbsp;
            <span contenteditable="true" class="mwc-console-query">{}</span>&nbsp;
            <span>)</span>
        </div>
    </div>
</script>

<script type="text/x-handlebars-template" id="database-content-view-template">
    <div class="contentViewHeader list-group" style="margin-bottom:0;">
        <a class="list-group-item collection-element-id-view hoverable">ObjectId("{{_id.$oid}}")</a>
    </div>
    <div class="contentViewBody hidden">

    </div>
</script>

<script type="text/x-handlebars-template" id="database-content-view-holder-empty-template">
    <span style="font-weight:bold">
        Script executed successfully, but there is no results to show
    </span>
</script>

<script type="text/x-handlebars-template" id="database-content-layout-template">
    <div class="row" role="tabpanel">
        <ul class="nav nav-tabs" role="tablist" id="dbContent-tab-panel" style="border-bottom:0;">

        </ul>
        <div class="tab-content" id="dbContent-tab-content" style="margin-top:0px;">
        </div>
    </div>
</script>

<script type="text/x-handlebars-template" id="database-tab-control-template">
    <li role="presentation" class="active"><a href="#{{contentId}}" class="{{contentId}}" aria-controls="home"
                                              role="tab" data-toggle="tab">
        <button class="close" type="button"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
        </button>
        {{tabName}}</a>
    </li>
</script>

<script type="text/x-handlebars-template" id="database-content-template">
    <div class="container-fluid">
        <div class="attributes row">
        </div>
        <div class="console row">
        </div>
        <div class="content row">
        </div>
    </div>
</script>

<script type="text/x-handlebars-template" id="database-connections-connection-div-template">
    <div id="{{id}}" class="padding-r10">
    </div>
</script>

<script type="text/x-handlebars-template" id="database-view-template">
    <div id="{{connectionName}}">
        <div class="collapser h-cursor-pointer">
            <i class="fa fa-caret-down hide"></i>
            <i class="fa fa-caret-right"></i>
            <span><span class="fa fa-desktop padding-lr-5"></span>{{connectionName}}</span>
        </div>
        <div class="collapsible collapse">
            <div id="child{{connectionName}}"></div>
        </div>
    </div>
</script>

<script type="text/x-handlebars-template" id="database-element-template">
    <div class="collapser h-cursor-pointer">
        <i class="fa fa-caret-down hide"></i>
        <i class="fa fa-caret-right"></i>
        <span><span class="fa fa-database padding-lr-5"></span>
                {{dbName}}
        </span>
    </div>
    <div class="collapsible collapse">
        <div>
            {{#each collectionNames}}
            <div class="collectionName h-cursor-pointer" targetcoll="{{this}}">
            <span><span class="fa fa-folder-o padding-lr-5"></span>{{this}}</span>
            </div>
            {{/each}}
        </div>
    </div>
</script>

<script type="text/x-handlebars-template" id="mwc-navbar">
    <div class='navbar-header'>
        <span class='navbar-brand h-cursor-pointer' id='mWcAppName'>{{appName}}</span>
    </div>
    <div class='collapse navbar-collapse'>
        <ul class='nav navbar-nav'>
            <li id='create-connection'>
                <a class='h-cursor-pointer'>Create connection</a>
            </li>
            <li id='view-connections' style='display: none'>
                <a class='h-cursor-pointer'>View opened connections</a>
            </li>
        </ul>
        <ul class='nav navbar-nav navbar-right'>
            <li>
                <p class="navbar-text"><i class="fa fa-user"></i> {{username}} </p>
            </li>
            <li>
                <p id="logout-href" class='h-cursor-pointer navbar-text nav-p-hover'><i class="fa fa-sign-out"/></i>
                    Logout</p>
            </li>
        </ul>
    </div>
</script>

<script type="text/javascript">
    MongoWebClient.username = "${pageContext.request.userPrincipal.name}";
    MongoWebClient.start();
</script>

</body>
</html>
