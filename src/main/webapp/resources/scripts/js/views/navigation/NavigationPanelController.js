/**
 * Created by manaev on 17.10.14.
 */
MongoWebClient.module("NavigationPanel", function (NavigationPanel, MongoWebClient, Backbone, Marionette, $, _) {
    NavigationPanel.Model = Backbone.Model.extend({});
    NavigationPanel.Controller = {
        showStartView: function (appName) {
            MongoWebClient.navigationPanelView = new NavigationPanel.Show({model: new NavigationPanel.Model({appName:appName, username:MongoWebClient.username})});

            MongoWebClient.navigationRegion.show(MongoWebClient.navigationPanelView);

            MongoWebClient.mainRegion.show(new NavigationPanel.ManualShow());

            MongoWebClient.navigationPanelView.on("event:mwc_showManual",function(){
                MongoWebClient.navigationPanelView.removeActive();
                MongoWebClient.mainRegion.show(new NavigationPanel.ManualShow());
            });
        }
    };

    NavigationPanel.ManualShow = Marionette.ItemView.extend({
        template:null,
        tagName:"div",
        className:"container",
        initialize:function(){
            this.template = Handlebars.compile(MongoWebClient.Templates.mongoWebClientManual);
        }
    });
});
