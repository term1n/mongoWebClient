/**
 * Created by manaev on 24.11.14.
 */
MongoWebClient.module("CommonDialogBox", function (CommonDialogBox, MongoWebClient, Backbone, Marionette, $, _) {
    CommonDialogBox.Model = Backbone.Model.extend({
        defaults:{
            modalHeader:"Title",
            modalBody:"Content"
        }
    });
    CommonDialogBox.Controller = {
        showSuccessDialog: function(data){
            var s_db = new CommonDialogBox.Success(new CommonDialogBox.Model(data));
            MongoWebClient.dialogsRegion.show(s_db);
            s_db.showDialog();
        },
        showErrorDialog: function(data){
            var e_db = new CommonDialogBox.Error(new CommonDialogBox.Model(data));
            MongoWebClient.dialogsRegion.show(e_db);
            e_db.showDialog();
        }
    };
});