/**
 * Created by manaev on 17.10.14.
 */
var MongoWebClient = new Marionette.Application();

MongoWebClient.addRegions({
    connectionManagerRegion: "#database-connection-manager",
    navigationRegion: "#navigation-panel"

});
MongoWebClient.on("start", function(){
    console.log("started application");
    MongoWebClient.NavigationPanel.Controller.showNavigationPanel("Mongo Web Client");
});