/**
 * Created by manaev on 17.10.14.
 */

var MWCAdmin = new Marionette.Application();
var MongoWebClient = new Marionette.Application();

MWCAdmin.addRegions({
    nUserManRegion: "#nuserMan",
    addUserRegion: "#add-user-dialog-div"
});

MongoWebClient.addRegions({
    dialogsRegion: "#common-dialogs-div"
});

MWCAdmin.on("start", function () {
});

MWCAdmin.showAddUserDialog = function(){
    var s_db = new MWCAdmin.MWCNUserMan.AddUserDialog(new MWCAdmin.MWCNUserMan.SimpleModel());
    MWCAdmin.addUserRegion.show(s_db);
    s_db.showDialog();
}

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


