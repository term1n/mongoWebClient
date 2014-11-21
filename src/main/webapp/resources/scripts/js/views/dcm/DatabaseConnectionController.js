/**
 * Created by manaev on 20.10.14.
 */
MongoWebClient.module("DatabaseConnection", function (DatabaseConnection, ContactManager, Backbone, Marionette, $, _) {
    DatabaseConnection.Model = Backbone.Model.extend({
        defaults:{
            name:"myMongo",
            host:"192.168.42.102",
            port:27017
        }
    });
    DatabaseConnection.Controller = {
        initView: function () {
            MongoWebClient.databaseConnection = new MongoWebClient.DatabaseConnection.Show(new DatabaseConnection.Model);
            MongoWebClient.connectionManagerRegion.show(MongoWebClient.databaseConnection);
            MongoWebClient.databaseConnection.$el.find(".dmcModal").draggable({
                handle: ".modal-header"
            });
            MongoWebClient.databaseConnection.listenTo(MongoWebClient.navigationPanelView,"event:showDCMDialog",function(){DatabaseConnection.Controller.showDialog()});
            MongoWebClient.databaseConnection.on("event:test-connection",function(){console.log("test connection")});
            MongoWebClient.databaseConnection.on("event:create_db_connection",function(){console.log("create_database connection")});
        },
        showDialog:function(){
            MongoWebClient.databaseConnection.showDialog();
        },
        closeDialog:function(){
            MongoWebClient.databaseConnection.closeDialog();
        },
        getModel: function(){
            return MongoWebClient.databaseConnection.model;
        }
    };
});
