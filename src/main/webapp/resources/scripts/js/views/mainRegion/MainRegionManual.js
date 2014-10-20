/**
 * Created by manaev on 20.10.14.
 */
MongoWebClient.module("MainRegion", function (MainRegion, ContactManager, Backbone, Marionette, $, _) {
    MainRegion.ManualShow = Marionette.ItemView.extend({
        template:null,
        tagName:"div",
        className:"container",
        initialize:function(){
            this.template = Handlebars.compile(MongoWebClient.Templates.mongoWebClientManual);
        }
    });
});