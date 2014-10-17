/**
 * Created by manaev on 17.10.14.
 */
MongoWebClient.module("NavigationPanel", function (NavigationPanel, ContactManager, Backbone, Marionette, $, _) {
    NavigationPanel.Show = Marionette.ItemView.extend({
        template:null,
        tagName:"div",
        className:"container-fluid",
        events: {
            "click #create-connection": "createMongoConnection"
        },
        initialize:function(){
            this.template = Handlebars.compile(MongoWebClient.Templates.navigationPanel);
        },
        createMongoConnection: function(evt){
            $(evt.currentTarget).toggleClass("active");
        }
    });
});