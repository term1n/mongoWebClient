/**
 * Created by manaev on 17.10.14.
 */
var MongoWebClient = new Marionette.Application();

MongoWebClient.addRegions({
    connectionManagerRegion: "#database-connection-manager",
    mainRegion: "#main-region",
    navigationRegion: "#navigation-panel"

});
MongoWebClient.on("start", function(){
    MongoWebClient.NavigationPanel.Controller.showStartView("Mongo Web Client");
    MongoWebClient.DatabaseConnection.Controller.initView();
});