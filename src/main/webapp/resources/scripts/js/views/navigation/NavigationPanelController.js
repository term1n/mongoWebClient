/**
 * Created by manaev on 17.10.14.
 */
MongoWebClient.module("NavigationPanel", function (NavigationPanel, ContactManager, Backbone, Marionette, $, _) {
    NavigationPanel.Model = Backbone.Model.extend({});
    NavigationPanel.Controller = {
        showStartView: function (appName) {
            MongoWebClient.navigationPanelView = new NavigationPanel.Show({model: new NavigationPanel.Model({appName:appName})});

            MongoWebClient.navigationRegion.show(MongoWebClient.navigationPanelView);

            MongoWebClient.manual = new MongoWebClient.MainRegion.ManualShow();

            MongoWebClient.mainRegion.show(MongoWebClient.manual);

            MongoWebClient.navigationPanelView.on("event:mwc_showManual",function(){
                MongoWebClient.navigationPanelView.removeActive();
                MongoWebClient.mainRegion.show(MongoWebClient.manual);
            });

        }
    };
});
