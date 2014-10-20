/**
 * Created by manaev on 17.10.14.
 */
MongoWebClient.module("NavigationPanel", function (NavigationPanel, ContactManager, Backbone, Marionette, $, _) {
    NavigationPanel.Show = Marionette.ItemView.extend({
        template:null,
        tagName:"div",
        className:"container-fluid",
        events: {
            "click #create-connection": "createMongoConnection",
            "click #mWcAppName": "showManual"

        },
        initialize:function(){
            this.template = Handlebars.compile(MongoWebClient.Templates.navigationPanel);
        },
        createMongoConnection: function(){
            this.addActive();
            NavigationPanel.trigger("nav:showDCM",this);
        },
        addActive:function(){
            $("#create-connection").addClass("active");
        },
        removeActive:function(){
            $("#create-connection").removeClass("active");
        },
        showManual:function(){
            NavigationPanel.trigger("nav:showManual",this);
        }
    });
});