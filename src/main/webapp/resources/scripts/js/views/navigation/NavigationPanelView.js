/**
 * Created by manaev on 17.10.14.
 */
MongoWebClient.module("NavigationPanel", function (NavigationPanel, MongoWebClient, Backbone, Marionette, $, _) {
    NavigationPanel.Show = Marionette.ItemView.extend({
        template:null,
        tagName:"div",
        className:"container-fluid",
        triggers: {
            "click #mWcAppName": "event:mwc_showManual",
            "click #view-connections":"event:viewOpenedConnections"
        },
        events:{
            "click #create-connection": "createMongoConnection",
            "click #logout-href":"logout"
        },
        logout:function(){
            $("#logoutForm").submit();
        },
        initialize:function(){
            this.template = Handlebars.compile($("#mwc-navbar").html());
        },
        createMongoConnection: function(){
            this.addCCActive();
            this.trigger("event:showDCMDialog",this);
        },
        addCCActive:function(){
            $("#create-connection").addClass("active");
        },
        addVCActive:function(){
            $("#view-connections").addClass("active");
        },
        removeActive:function(){
            $("#create-connection").removeClass("active");
            $("#view-connections").removeClass("active");
        },
        toggleVC: function(){
            $("#view-connections").toggle('hide');
        }
    });
});