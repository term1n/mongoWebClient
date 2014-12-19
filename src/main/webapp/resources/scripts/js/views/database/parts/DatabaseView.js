/**
 * Created by manaev on 26.11.14.
 */
MongoWebClient.module("DatabaseLayout", function (DatabaseLayout, MongoWebClient, Backbone, Marionette, $, _) {
    DatabaseLayout.Element = Backbone.Marionette.ItemView.extend({
        template: null,
        tagName: "div",
        className: "",
        events: {
            "contextmenu .collectionName": "fireMenuCollection",
            "contextmenu .panel-heading": "fireMenuDatabase",
            "dblclick .collectionName": "viewDbCollection",
            "click .collapser":"doToggle"
        },
        fireMenuCollection: function (evt) {
            evt.preventDefault();
            var rData = {
                host:this.model.collection.requestData.host,
                port:this.model.collection.requestData.port,
                name:this.model.collection.requestData.name,
                collName:evt.currentTarget.getAttribute("targetcoll"),
                dbName:this.model.get("dbName")
            };
            $("#context-menu-placeholder").show().css({ position: "absolute",left: MongoWebClient.locate(evt).left,top: MongoWebClient.locate(evt).top}).off('click')
                .mouseleave(function(){$(this).hide();})
                .on('click',rData,this.clickCollectionMenuItem);
        },
        viewDbCollection: function(evt){
            var rData = {
                host:this.model.collection.requestData.host,
                port:this.model.collection.requestData.port,
                name:this.model.collection.requestData.name,
                collName:evt.currentTarget.getAttribute("targetcoll"),
                dbName:this.model.get("dbName")
            };
            MongoWebClient.trigger("event:viewCollection",rData);
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
        },
        doToggle: function(evt){
            this.$el.find(".collapsible").first().collapse('toggle');
            this.$el.find(".fa-caret-down").first().toggleClass('hide');
            this.$el.find(".fa-caret-right").first().toggleClass('hide');
            evt.stopPropagation();
        }
    });
    DatabaseLayout.Container = Backbone.Marionette.CompositeView.extend({
        childView: DatabaseLayout.Element,
        childViewContainer: null,
        template: null,
        className:"",
        events:{
            "click .collapser":"doToggle"
        },
        initialize: function () {
            this.childViewContainer = "#child" + this.collection.connectionName;
            this.template = Handlebars.compile($("#database-view-template").html());
            this.template = this.template({connectionName: this.collection.connectionName});
        },
        doToggle: function(){
            this.$el.find(".collapsible").first().collapse('toggle');
            this.$el.find(".fa-caret-down").first().toggleClass('hide');
            this.$el.find(".fa-caret-right").first().toggleClass('hide');
        }
    });
});
