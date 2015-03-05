/**
 * Created by manaev on 17.10.14.
 */
MongoWebClient.module("NavigationPanel", function (NavigationPanel, MongoWebClient, Backbone, Marionette, $, _) {

    Handlebars.registerHelper('ifCond', function (v1, operator, v2, options) {
        switch (operator) {
            case '==':
                return (v1.trim() == v2.trim()) ? options.fn(this) : options.inverse(this);
            case '===':
                return (v1 === v2) ? options.fn(this) : options.inverse(this);
            case '<':
                return (v1 < v2) ? options.fn(this) : options.inverse(this);
            case '<=':
                return (v1 <= v2) ? options.fn(this) : options.inverse(this);
            case '>':
                return (v1 > v2) ? options.fn(this) : options.inverse(this);
            case '>=':
                return (v1 >= v2) ? options.fn(this) : options.inverse(this);
            case '&&':
                return (v1 && v2) ? options.fn(this) : options.inverse(this);
            case '||':
                return (v1 || v2) ? options.fn(this) : options.inverse(this);
            default:
                return options.inverse(this);
        }
    });

    NavigationPanel.Model = Backbone.Model.extend({});
    NavigationPanel.Controller = {
        showStartView: function (appName) {
            MongoWebClient.navigationPanelView = new NavigationPanel.Show({model: new NavigationPanel.Model({appName:appName, username:MongoWebClient.username, authorities:MongoWebClient.authorities})});

            MongoWebClient.navigationRegion.show(MongoWebClient.navigationPanelView);

            MongoWebClient.mainRegion.show(new NavigationPanel.ManualShow());

            MongoWebClient.navigationPanelView.on("event:mwc_showManual",function(){
                MongoWebClient.navigationPanelView.removeActive();
                MongoWebClient.mainRegion.$el.hide();
                MongoWebClient.manualRegion.show(new NavigationPanel.ManualShow());
            });
            MongoWebClient.navigationPanelView.on("event:viewOpenedConnections",function(){
                MongoWebClient.navigationPanelView.removeActive();
                MongoWebClient.navigationPanelView.addVCActive();
                MongoWebClient.mainRegion.$el.show();
                MongoWebClient.manualRegion.empty();
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
