/**
 * Created by manaev on 12.12.14.
 */
MongoWebClient.module("ConfirmationDialog", function (ConfirmationDialog, MongoWebClient, Backbone, Marionette, $, _) {
    ConfirmationDialog.CDViewModel = Backbone.Model.extend({});
    ConfirmationDialog.CDView = Marionette.ItemView.extend({
        template:null,
        tagName:"div",
        className:"container-fluid",
        events:{
            "click .btn-yes" : "doConfirm"
        },
        initialize:function(opt) {
            this.model = new ConfirmationDialog.CDViewModel({ modalHeader:opt.options.modalHeader, modalBodyDanger:opt.options.modalBodyDanger,modalBodyText:opt.options.modalBodyText});
            this.fCallbackTarget = opt.options.fCallbackTarget;
            this.fCallbackName = opt.options.fCallbackName;
            this.template = Handlebars.compile($("#common-confirm-modal-dialog").html());
        },
        showDialog: function(){
            this.$el.find('.modal').modal('show');
        },
        doConfirm:function(){
            this.fCallbackTarget[this.fCallbackName]();
            this.$el.find('.modal').modal('hide');
        }
    });
    ConfirmationDialog.Controller = {
        showConfirmDialog: function(data){
            var s_db = new ConfirmationDialog.CDView({options:data});
            MongoWebClient.confirmDialogsRegion.show(s_db);
            s_db.showDialog();
        }
    };
});