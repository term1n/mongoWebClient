/**
 * Created by manaev on 17.10.14.
 */
MongoWebClient.module("NavigationPanel", function (NavigationPanel, MongoWebClient, Backbone, Marionette, $, _) {
    NavigationPanel.Show = Marionette.ItemView.extend({
        template:null,
        tagName:"div",
        className:"container-fluid",
        triggers: {
            "click #mWcAppName": "event:mwc_showManual"
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
            this.addActive();
            this.trigger("event:showDCMDialog",this);
        },
        addActive:function(){
            $("#create-connection").addClass("active");
        },
        removeActive:function(){
            $("#create-connection").removeClass("active");
        }
    });
});