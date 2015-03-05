/**
 * Created by manaev on 17.10.14.
 */

var MWCAdmin = new Marionette.Application();
var MongoWebClient = new Marionette.Application();

MWCAdmin.addRegions({
    nUserManRegion: "#nuserMan"
});

MongoWebClient.addRegions({
    dialogsRegion: "#common-dialogs-div"
});

MWCAdmin.on("start", function () {
});

MWCAdmin.showNUserManagement = function () {
    var coll = new MWCAdmin.MWCNUserMan.MWCUsers().fetch();
    MWCAdmin.nUserManRegion.show(new MWCAdmin.MWCNUserMan.ShowUsers({collection: coll}));
};

MongoWebClient.on("event:showSuccessDialog", function (data) {
    MongoWebClient.CommonDialogBox.Controller.showSuccessDialog(data);
});

MongoWebClient.on("event:showErrorDialog", function (data) {
    MongoWebClient.CommonDialogBox.Controller.showErrorDialog(data);
});


