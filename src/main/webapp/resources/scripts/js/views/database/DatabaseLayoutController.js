/**
 * Created by manaev on 26.11.14.
 */
MongoWebClient.module("DatabaseLayout", function (DatabaseLayout, MongoWebClient, Backbone, Marionette, $, _) {
    DatabaseLayout.Controller = {
        showDatabaseLayout: function () {
            MongoWebClient.databaseLayout = new DatabaseLayout.Layout({collection: MongoWebClient.databaseConnectionsCollection});
            MongoWebClient.mainRegion.show(MongoWebClient.databaseLayout);
            MongoWebClient.navigationPanelView.removeActive();
            MongoWebClient.navigationPanelView.addVCActive();
            MongoWebClient.mainRegion.$el.show();
        },
        addConnection: function (opt) {
            if (MongoWebClient.databaseConnectionsCollection) {
                if (!this.sameName(opt)) {
                    return MongoWebClient.databaseConnectionsCollection.addConnection(opt);
                }else{
                    MongoWebClient.trigger("event:showErrorDialog",{modalHeader:"Error",modalBody:"Naming policy violation"});
                    return false;
                }
            } else {
                MongoWebClient.navigationPanelView.toggleVC();
                MongoWebClient.databaseConnectionsCollection = new DatabaseLayout.ConnectionCollection();
                return MongoWebClient.databaseConnectionsCollection.addConnection(opt);
            }
        },
        showCollectionEntry: function (collection) {
            MongoWebClient.databaseLayout.showCollectionEntry(collection);
        },
        sameName: function (opt) {
            var isIn = false;
            _.each(MongoWebClient.databaseConnectionsCollection.models, function (entry) {
                if (entry.connectionName == opt.name) {
                    isIn = true;
                }
            });
            return isIn;
        }

    };
});