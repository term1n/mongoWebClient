/**
 * Created by manaev on 17.10.14.
 */
var MongoWebClient = new Marionette.Application();

MongoWebClient.addRegions({
    connectionManagerRegion: "#database-connection-manager",
    mainRegion: "#main-region",
    navigationRegion: "#navigation-panel",
    dialogsRegion: "#common-dialogs-div"

});
MongoWebClient.on("start", function () {
    MongoWebClient.NavigationPanel.Controller.showStartView("Mongo Web Client");
    MongoWebClient.DatabaseConnection.Controller.initView();
});

MongoWebClient.on("event:showSuccessDialog", function (data) {
    MongoWebClient.CommonDialogBox.Controller.showSuccessDialog(data);
});

MongoWebClient.on("event:showErrorDialog", function (data) {
    MongoWebClient.CommonDialogBox.Controller.showErrorDialog(data);
});

MongoWebClient.on("event:closeDMCDialog", function (data) {
    MongoWebClient.DatabaseConnection.Controller.closeDialog();
});

MongoWebClient.on("event:viewCollection", function (data) {
    MongoWebClient.DatabaseLayout.ContentControllerLayout.addView(data);
});

MongoWebClient.on("event:showDatabaseLayout", function (data) {
    if(MongoWebClient.DatabaseLayout.Controller.addConnection(data)){
        MongoWebClient.DatabaseLayout.Controller.showDatabaseLayout();
    }
});
MongoWebClient.locate=function(event){
    var eventDoc, doc, body;
    event = event || window.event; // IE-ism
    if (event.pageX == null && event.clientX != null) {
        eventDoc = (event.target && event.target.ownerDocument) || document;
        doc = eventDoc.documentElement;
        body = eventDoc.body;
        event.pageX = event.clientX +
            (doc && doc.scrollLeft || body && body.scrollLeft || 0) -
            (doc && doc.clientLeft || body && body.clientLeft || 0);
        event.pageY = event.clientY +
            (doc && doc.scrollTop  || body && body.scrollTop  || 0) -
            (doc && doc.clientTop  || body && body.clientTop  || 0 );
    }
    return {top:event.pageY-5,left:event.pageX-5}
};


