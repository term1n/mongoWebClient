<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="/WEB-INF/views/adHeader.jsp" %>
<html>
<head>
    <title>MongoWebClient</title>
</head>
<body>
<%--simple security cred--%>
<div style="display: none">
    <form action="<c:url value='j_spring_security_logout' />" method="post" id="logoutForm">
        <input type="hidden"
               name="${_csrf.parameterName}"
               value="${_csrf.token}"/>
    </form>
</div>

<nav id="navigation-panel" class="navbar navbar-inverse" role="navigation">
    <div class="container-fluid">
        <div class='navbar-header'>
                <span class='navbar-brand'>MongoWebClient admin area</span>
        </div>
        <div class='collapse navbar-collapse'>
            <ul class='nav navbar-nav'>
                <li class="dropdown">
                    <a href='#' class="h-cursor-pointer dropdown-toggle" data-toggle="dropdown" role="button"
                       aria-expanded="false">User management<span class="caret"></span> </a>
                    <ul class="dropdown-menu" role="menu">
                        <li><a href="#" id="manageNativeUsers" onclick="MWCAdmin.showNUserManagement()">Manage native
                            users</a></li>
                        <li><a href="#" id="Importusers">Import users</a></li>
                    </ul>
                </li>
            </ul>
            <ul class='nav navbar-nav navbar-right'>
                <li>
                    <p class="navbar-text"><i class="fa fa-user"></i> ${user.username} </p>
                </li>
                <li>
                    <p id="logout-href" class='h-cursor-pointer navbar-text nav-p-hover'
                       onclick="$('#logoutForm').submit()"><i class="fa fa-sign-out"/></i>
                        Logout</p>
                </li>
            </ul>
        </div>
    </div>
</nav>

<div class="container main-region">
    <div class="row" id="nuserMan">
    </div>
</div>
<div id="common-dialogs-div">
</div>
<script type="text/x-handlebars-template" id="common-success-modal-dialog">
    <div class="modal fade" tabindex="-1" role="dialog" aria-labelledby="smallModal" aria-hidden="true" style="z-index:10000">
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
    <div class="modal fade" tabindex="-1" role="dialog" aria-labelledby="smallModal" aria-hidden="true" style="z-index:10000">
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
    <div class="modal fade" tabindex="-1" role="dialog" aria-labelledby="smallModal" aria-hidden="true" style="z-index:10000">
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
<script type="text/x-handlebars-template" id="nusersman-collection-item-tpl">
    <div class="col-lg-12">
        <div class="col-lg-3">
            <input type="text" value="{{username}}"/>
        </div>
        <div class="col-lg-3">
            <input type="text" value="{{eMail}}"/>
        </div>
        <div class="col-lg-3">
            {{#each authorities}}
                <div class="row" style="padding-top:5px;">
                    <div class="col-lg-6">
                        <span>{{role}} </span>
                    </div>
                    <div class="col-lg-6">
                        <button type="button" class="btn btn-default dropdown-toggle removeRole" roletodel={{role}}><span class="fa fa-minus"/></button>
                    </div>
                </div>
            {{/each}}
        </div>
        <div class="col-lg-3">
            <div class="col-lg-6">
                <div class="input-group">
                    <div class="input-group-btn">
                        <button type="button" class="btn btn-default dropdown-toggle remove-user-button"> <span class="fa fa-remove"></span> Remove</button>
                    </div>
                </div>
            </div>
            <div class="col-lg-6">
                <div class="input-group-btn">
                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
                        <span class="fa fa-plus"></span> Add<span class="caret"></span>
                    </button>
                    <ul class="dropdown-menu first-dropdown" role="menu">
                        <li><a href="#">ROLE_USER</a></li>
                        <li><a href="#">ROLE_PRUSER</a></li>
                        <li><a href="#">ROLE_ADMIN</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</script>

<script type="text/x-handlebars-template" id="nusersman-collection-tpl">
    <div class="panel-heading mwc-paginating">
        <div>
            <h3>MongoWebClient users</h3>
        </div>
        <div>
            <button type="button" class="btn btn-default dropdown-toggle addUser"><span class="fa fa-plus-square"></span> AddUser</button>
        </div>
    </div>
    <div class="panel-body">
        <div class="row">
            <div class="col-lg-12">
                <div class="col-lg-3">
                    <h4>Username</h4>
                </div>
                <div class="col-lg-3">
                    <h4>E-Mail</h4>
                </div>
                <div class="col-lg-3">
                    <h4>Authorities</h4>
                </div>
            </div>
        </div>
        <div id="nusersman-item-container">

        </div>
    </div>
    <div class="panel-footer">
        <div class="panel">
            <div class="panel-body">
                <p> Change granted authorities, remove and add users if nesessary</p>
                <p> ROLE_ADMIN - all actions, access to admin area</p>
                <p> ROLE_PRUSER - reading, deleting items, updating items</p>
                <p> ROLE_USER - only reading</p>
            </div>
        </div>
    </div>
</script>

<script type="text/javascript">
    function ajaxindicatorstart(text) {
        if (jQuery('body').find('#resultLoading').attr('id') != 'resultLoading') {
            jQuery('body').append('<div id="resultLoading" style="display:none"><div><img src=\"<c:url value='/resources/css/ajax-loader.gif'/>\"<div></div></div><div class="bg"></div></div>');
        }

        jQuery('#resultLoading').css({
            'width': '100%',
            'height': '100%',
            'position': 'fixed',
            'opacity': '0.5',
            'z-index': '10000000',
            'top': '0',
            'left': '0',
            'right': '0',
            'bottom': '0',
            'margin': 'auto',
            'background': 'none repeat scroll 0 0 black'
        });

        jQuery('#resultLoading .bg').css({
            'background': '#000000',
            'opacity': '0.7',
            'width': '100%',
            'height': '100%',
            'position': 'absolute',
            'top': '0'
        });

        jQuery('#resultLoading>div:first').css({
            'width': '250px',
            'height': '75px',
            'text-align': 'center',
            'position': 'fixed',
            'top': '0',
            'left': '0',
            'right': '0',
            'bottom': '0',
            'margin': 'auto',
            'font-size': '16px',
            'z-index': '10',
            'color': '#ffffff'

        });

        jQuery('#resultLoading .bg').height('100%');
        jQuery('#resultLoading').fadeIn();
        jQuery('body').css('cursor', 'wait');
    }
    function ajaxindicatorstop() {
        jQuery('#resultLoading .bg').height('100%');
        jQuery('#resultLoading').fadeOut();
        jQuery('body').css('cursor', 'default');
    }
    jQuery(document).ajaxStart(function () {
        ajaxindicatorstart();
    }).ajaxStop(function () {
        ajaxindicatorstop();
    });
    MWCAdmin.start();
</script>

</body>
</html>