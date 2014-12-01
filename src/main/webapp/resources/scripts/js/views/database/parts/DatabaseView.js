/**
 * Created by manaev on 26.11.14.
 */
MongoWebClient.module("DatabaseLayout", function (DatabaseLayout, MongoWebClient, Backbone, Marionette, $, _) {
    DatabaseLayout.Element = Backbone.Marionette.ItemView.extend({
        template: null,
        tagName: "div",
        className: "panel panel-default",
        events: {
            "contextmenu .list-group-item": "fireMenuCollection",
            "contextmenu .panel-heading": "fireMenuDatabase"
        },
        fireMenuCollection: function (evt) {
            evt.preventDefault();
            var rData = {
                host:this.model.collection.requestData.host,
                port:this.model.collection.requestData.port,
                name:this.model.collection.requestData.name,
                collName:evt.currentTarget.innerHTML,
                dbName:this.model.get("dbName")
            };
            $("#context-menu-placeholder").show().css({ position: "absolute",left: MongoWebClient.locate(evt).left,top: MongoWebClient.locate(evt).top}).off('click')
                .mouseleave(function(){$(this).hide();})
                .on('click',rData,this.clickCollectionMenuItem);
        },
        clickCollectionMenuItem:function(evt){
            $(this).hide();
            if($(evt.target).hasClass("viewCollection")){
                MongoWebClient.trigger("event:viewCollection",evt.data);
            }
            if($(evt.target).hasClass("dropCollection")){
                console.log("dropCollection");
            }
        },
        clickDatabaseMenuItem:function(evt){
            $(this).hide();
            //                    TODO manage drop database and refresh databse
            $("#context-menu-placeholder").hide();
            if($(evt.target).hasClass("dropDatabase")){
                console.log("dropDatabase");
            }
            if($(evt.target).hasClass("refreshDatabase")){
                console.log("refreshDatabase");
            }
        },
        fireMenuDatabase: function (evt) {
            evt.preventDefault();
            var rData = {
                host:this.model.collection.requestData.host,
                port:this.model.collection.requestData.port,
                dbName:this.model.get("dbName")
            };
            $("#database-menu-placeholder").show().css({ position: "absolute",left: MongoWebClient.locate(evt).left,top: MongoWebClient.locate(evt).top})
                .off('click').mouseleave(function(){$(this).hide();})
                .on('click',rData,this.clickDatabaseMenuItem);
        },
        initialize: function () {
            this.template = Handlebars.compile($("#database-element-template").html());
        }
    });
    DatabaseLayout.Container = Backbone.Marionette.CompositeView.extend({
        childView: DatabaseLayout.Element,
        childViewContainer: null,
        template: null,
        initialize: function () {
            this.childViewContainer = "#child" + this.collection.connectionName;
            this.template = Handlebars.compile($("#database-view-template").html());
            this.template = this.template({connectionName: this.collection.connectionName});
        }
    });
});
