/**
 * Created by manaev on 26.11.14.
 */
MongoWebClient.module("DatabaseLayout", function (DatabaseLayout, MongoWebClient, Backbone, Marionette, $, _) {
    DatabaseLayout.Layout = Backbone.Marionette.LayoutView.extend({
        regions: {
            leftMenu: "#dlLeft",
            rightMenu: "#dlRight"
        },
        tagName:"div",
        className:"container-fluid",
        initialize:function(){
            this.template = Handlebars.compile($("#database-layout-template").html());
        },
        onShow: function() {
            this.leftMenu.show(new DatabaseLayout.ConnectionsView({collection:this.collection}));
        },
        showCollectionEntry: function(collection){
            this.rightMenu.show(new DatabaseLayout.ContentHolder({collection : collection}));
            MongoWebClient.dbContentLayout = this.rightMenu.currentView;
        }
    });
    DatabaseLayout.ConnectionsView = Backbone.Marionette.LayoutView.extend({
        template: null,
        tagName:"div",
        className:"panel panel-default",
        initialize: function () {
            var tempTemplate = "";
            this.childTemplate = Handlebars.compile($("#database-connections-connection-div-template").html());
            _.each(this.collection.models,function(connection){
                var tempId = "id"+connection.connectionName;
                tempTemplate = tempTemplate + this.childTemplate({id:tempId});
            },this);
            this.template = Handlebars.compile(tempTemplate);
        },
        onShow: function() {
            _.each(this.collection.models,function(connection){
                var tempId = "#id"+connection.connectionName;
                this.addRegion(connection.connectionName,tempId);
                this[connection.connectionName].show(new DatabaseLayout.Container({collection:connection}));
            },this);
        }
    });
});