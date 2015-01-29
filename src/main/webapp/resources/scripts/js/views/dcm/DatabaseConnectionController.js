/**
 * Created by manaev on 20.10.14.
 */
MongoWebClient.module("DatabaseConnection", function (DatabaseConnection, MongoWebClient, Backbone, Marionette, $, _) {
    DatabaseConnection.Model = Backbone.Model.extend({
        defaults:{
            name:"myMongo",
            host:"192.168.43.113",
            port:27017
        }
    });
    DatabaseConnection.Controller = {
        initView: function () {
            MongoWebClient.databaseConnection = new DatabaseConnection.Show(new DatabaseConnection.Model({name:"name",host:"192.168.43.113",port:27017}));
//            MongoWebClient.databaseConnection = new MongoWebClient.DatabaseConnection.Show(new DatabaseConnection.Model());
            MongoWebClient.connectionManagerRegion.show(MongoWebClient.databaseConnection);
            MongoWebClient.databaseConnection.$el.find(".dmcModal").draggable({
                handle: ".modal-header"
            });
            MongoWebClient.databaseConnection.listenTo(MongoWebClient.navigationPanelView,"event:showDCMDialog",function(){DatabaseConnection.Controller.showDialog()});
            MongoWebClient.databaseConnection.on("event:test-connection",function(){DatabaseConnection.Controller.test_connection();});
            MongoWebClient.databaseConnection.on("event:create_db_connection",function(){DatabaseConnection.Controller.create_connection();});
        },
        showDialog:function(){
            MongoWebClient.databaseConnection.showDialog();
        },
        closeDialog:function(){
            MongoWebClient.databaseConnection.closeDialog();
        },
        getModel: function(){
            return MongoWebClient.databaseConnection.model;
        },
        test_connection: function(){
            var dataToSend = this.getModel().attributes;
            $.ajax({
                dataType: "json",
                type: "GET",
                data: dataToSend,
                url:"/mongoWebClient/control/testMongoConnection",
                success: function (data) {
                    if (data.status === 'SUCCESS' && data.model) {
                        MongoWebClient.trigger("event:showSuccessDialog",{modalHeader:"Success",modalBody:data.model})
                    } else{
                        MongoWebClient.trigger("event:showErrorDialog",{modalHeader:"Error",modalBody:"Can't establish connection"});
                    }
                },
                error:function(){
                    MongoWebClient.trigger("event:showErrorDialog",{modalHeader:"Error",modalBody:"Can't establish connection"});
                }
            });
        },
        create_connection: function(){
            MongoWebClient.trigger("event:showDatabaseLayout",this.getModel().attributes);
        }
    };
});
