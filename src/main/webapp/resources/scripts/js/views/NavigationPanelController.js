/**
 * Created by manaev on 17.10.14.
 */
MongoWebClient.module("NavigationPanel", function (NavigationPanel, ContactManager, Backbone, Marionette, $, _) {
    NavigationPanel.Model = Backbone.Model.extend({});
    NavigationPanel.Controller = {
        showNavigationPanel: function (appName) {
            console.log("showing nav panel for" + appName);
            MongoWebClient.navigationRegion.show(new NavigationPanel.Show({model: new NavigationPanel.Model({appName:appName})}));
        }
    }
});
