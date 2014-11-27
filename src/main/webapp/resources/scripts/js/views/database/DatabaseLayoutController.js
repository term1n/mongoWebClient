/**
 * Created by manaev on 26.11.14.
 */
MongoWebClient.module("DatabaseLayout", function (DatabaseLayout, MongoWebClient, Backbone, Marionette, $, _) {
    DatabaseLayout.Controller= {
        showDatabaseLayout: function(){
            MongoWebClient.databaseLayout = new MongoWebClient.DatabaseLayout.Layout({collection:MongoWebClient.databaseConnectionsCollection});
            MongoWebClient.mainRegion.show(MongoWebClient.databaseLayout);
        },
        addConnection:function(opt){
            if(MongoWebClient.databaseConnectionsCollection){
                MongoWebClient.databaseConnectionsCollection.addConnection(opt);
            }else{
                MongoWebClient.databaseConnectionsCollection = new DatabaseLayout.ConnectionCollection();
                MongoWebClient.databaseConnectionsCollection.addConnection(opt);
            }
        }
    };
});