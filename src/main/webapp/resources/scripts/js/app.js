/**
 * Created by manaev on 17.10.14.
 */
var MongoWebClient = new Marionette.Application();

MongoWebClient.addRegions({
    connectionManagerRegion: "#database-connection-manager",
    mainRegion: "#main-region",
    navigationRegion: "#navigation-panel",
    dialogsRegion:"#common-dialogs-div"

});
MongoWebClient.on("start", function(){
    MongoWebClient.NavigationPanel.Controller.showStartView("Mongo Web Client");
    MongoWebClient.DatabaseConnection.Controller.initView();
});

MongoWebClient.on("event:showSuccessDialog",function(data){
    MongoWebClient.CommonDialogBox.Controller.showSuccessDialog(data);
});

MongoWebClient.on("event:showErrorDialog",function(data){
    MongoWebClient.CommonDialogBox.Controller.showErrorDialog(data);
});

MongoWebClient.on("event:closeDMCDialog",function(data){
    MongoWebClient.DatabaseConnection.Controller.closeDialog();
});

MongoWebClient.on("event:viewCollection",function(data){
    //TODO next stage - render collection entities
    var fff = new MongoWebClient.CollectionEntity.Collection(data);
    fff.fetch();
});

MongoWebClient.on("event:showDatabaseLayout",function(data){
    MongoWebClient.DatabaseLayout.Controller.addConnection(data);
    MongoWebClient.DatabaseLayout.Controller.showDatabaseLayout();
});


