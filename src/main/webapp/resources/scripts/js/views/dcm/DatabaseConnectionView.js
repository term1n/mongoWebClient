/**
 * Created by manaev on 20.10.14.
 */
MongoWebClient.module("DatabaseConnection", function (DatabaseConnection, MongoWebClient, Backbone, Marionette, $, _) {
    DatabaseConnection.Show = Marionette.ItemView.extend({
        template:null,
        tagName:"div",
        className:"container-fluid",
        triggers:{
            "click #test_connection":"event:test-connection",
            "click #button_connect":"event:create_db_connection"
        },
        events:{
          "change #m_c_name": "change_m_c_name",
          "change #m_c_host": "change_m_c_host",
          "change #m_c_port": "change_m_c_port"
        },
        initialize:function(opt){
            this.model = opt;
            this.template = Handlebars.compile($("#dmc-modal-dialog").html());
        },
        showDialog: function(){
            this.$el.find('.modal').modal('show');
        },
        closeDialog: function(){
            this.$el.find('.close').click();
        },
        change_m_c_name: function(evt){
            this.model.set("name",evt.currentTarget.value);
        },
        change_m_c_host: function(evt){
            this.model.set("host",evt.currentTarget.value);
        },
        change_m_c_port: function(evt){
            this.model.set("port",evt.currentTarget.value);
        }
    });
});
