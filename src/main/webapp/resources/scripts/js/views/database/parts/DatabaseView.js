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
                collName:evt.currentTarget.innerHTML,
                dbName:this.model.get("dbName")
            };
            $("#context-menu-placeholder").show().css({ position: "absolute",left: this.locate(evt).left,top: this.locate(evt).top}).off('click')
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
        locate:function(event){
            var eventDoc, doc, body;
            event = event || window.event; // IE-ism
            if (event.pageX == null && event.clientX != null) {
                eventDoc = (event.target && event.target.ownerDocument) || document;
                doc = eventDoc.documentElement;
                body = eventDoc.body;
                event.pageX = event.clientX +
                    (doc && doc.scrollLeft || body && body.scrollLeft || 0) -
                    (doc && doc.clientLeft || body && body.clientLeft || 0);
                event.pageY = event.clientY +
                    (doc && doc.scrollTop  || body && body.scrollTop  || 0) -
                    (doc && doc.clientTop  || body && body.clientTop  || 0 );
            }
            return {top:event.pageY-5,left:event.pageX-5}
        },
        fireMenuDatabase: function (evt) {
            evt.preventDefault();
            var rData = {
                host:this.model.collection.requestData.host,
                port:this.model.collection.requestData.port,
                dbName:this.model.get("dbName")
            };
            $("#database-menu-placeholder").show().css({ position: "absolute",left: this.locate(evt).left,top: this.locate(evt).top})
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
