/**
 * Created by manaev on 20.10.14.
 */
MongoWebClient.module("DatabaseConnection", function (DatabaseConnection, ContactManager, Backbone, Marionette, $, _) {
    DatabaseConnection.Model = Backbone.Model.extend({});
    DatabaseConnection.Controller = {
        initView: function () {
            MongoWebClient.databaseConnection = new MongoWebClient.DatabaseConnection.Show();
            MongoWebClient.connectionManagerRegion.show(MongoWebClient.databaseConnection);
            MongoWebClient.databaseConnection.$el.find(".dmcModal").draggable({
                handle: ".modal-header"
            });
        },
        showDialog:function(){
            MongoWebClient.databaseConnection.showDialog();
        },
        closeDialog:function(){
            MongoWebClient.databaseConnection.closeDialog();
        }
    };
    this.on("dmc:showDialog",function(){
        DatabaseConnection.Controller.showDialog();
    })
});
