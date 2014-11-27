/**
 * Created by manaev on 24.11.14.
 */
MongoWebClient.module("CommonDialogBox", function (CommonDialogBox, MongoWebClient, Backbone, Marionette, $, _) {
    CommonDialogBox.Success = Marionette.ItemView.extend({
        template:null,
        tagName:"div",
        className:"container-fluid",
        initialize:function(opt) {
            this.model = opt;
            this.template = Handlebars.compile($("#common-success-modal-dialog").html());
        },
        showDialog: function(){
            this.$el.find('.modal').modal('show');
        }
    });
    CommonDialogBox.Error = Marionette.ItemView.extend({
        template:null,
        tagName:"div",
        className:"container-fluid",
        initialize:function(opt) {
            this.model = opt;
            this.template = Handlebars.compile($("#common-error-modal-dialog").html());
        },
        showDialog: function(){
            this.$el.find('.modal').modal('show');
        }
    });
});