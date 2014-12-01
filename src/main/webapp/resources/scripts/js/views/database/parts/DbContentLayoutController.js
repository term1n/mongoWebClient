/**
 * Created by manaev on 28.11.14.
 */
MongoWebClient.module("DatabaseLayout", function (DatabaseLayout, MongoWebClient, Backbone, Marionette, $, _) {
    DatabaseLayout.ContentControllerLayout = {
        addView: function (data) {
            if (MongoWebClient.dbContentLayout && MongoWebClient.dbContentLayout.children && MongoWebClient.dbContentLayout.children.length> 0) {
                MongoWebClient.dbContentLayout.addChild(new DatabaseLayout.LayoutViewEntity(data), DatabaseLayout.ContentLayout, 1);
            } else {
                DatabaseLayout.Controller.showCollectionEntry(new DatabaseLayout.LayoutViewCollection(new DatabaseLayout.LayoutViewEntity(data)));
            }
        }
    }
});