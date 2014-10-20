/**
 * Created by manaev on 20.10.14.
 */
MongoWebClient.module("DatabaseConnection", function (DatabaseConnection, ContactManager, Backbone, Marionette, $, _) {
    DatabaseConnection.Show = Marionette.ItemView.extend({
        template:null,
        tagName:"div",
        className:"container-fluid",
        initialize:function(){
            this.template = Handlebars.compile($("#dmc-modal-dialog").html());
        },
        showDialog: function(){
            this.$el.find('.modal').modal('show');
        },
        closeDialog: function(){
            this.$el.find('.modal').modal('close');
        }
    });
});
