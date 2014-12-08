/**
 * Created by manaev on 28.11.14.
 */
/**
 * Created by manaev on 28.11.14.
 */
MongoWebClient.module("DatabaseLayout", function (DatabaseLayout, MongoWebClient, Backbone, Marionette, $, _) {

    DatabaseLayout.ContentViewElement = Backbone.Marionette.ItemView.extend({
        events:{
            "click .contentViewHeader":"viewEntry",
            "contextmenu .contentViewHeader":"fireContextMenu"
        },
        viewEntry:function(){
            var temp = JSON.parse(JSON.stringify(this.model.collection.requestData));
            temp["id"] = this.model.attributes["_id"]["$oid"];
            var solo = new DatabaseLayout.CollectionEntity().fetch(temp);
            this.$el.find(".contentViewBody").html("").toggleClass("hidden");
            this.$el.find(".contentViewBody").tjJson({data:solo.attributes});

        },
        fireContextMenu:function(evt){
            var self = this;
            evt.preventDefault();
            $("#entry-menu-placeholder").show().css({ position: "absolute",left: MongoWebClient.locate(evt).left,top: MongoWebClient.locate(evt).top}).off('click')
                .mouseleave(function(){$(this).hide();})
                .on('click',self,this.clickMenuItem);
        },
        clickMenuItem:function(evt){
            $(this).hide();
//            TODO manage evt situations
            if($(evt.target).hasClass("editEntry")){
                console.log("editEntry")
            }
            if($(evt.target).hasClass("deleteEntry")){
                console.log("deleteEntry");
            }
            if($(evt.target).hasClass("viewEntry")){
                evt.data.viewEntry();
            }
        },
        initialize: function () {
            this.template = Handlebars.compile($("#database-content-view-template").html());
        }
    });

    DatabaseLayout.ConsoleView = Backbone.Marionette.ItemView.extend({
        template: null,
        tagName: "div",
        className:"panel panel-default console-view",
        initialize: function () {
            this.template = Handlebars.compile($("#database-console-template").html());
        }
    });

    DatabaseLayout.AttributesView = Backbone.Marionette.ItemView.extend({
        template: null,
        tagName: "div",
        className:"panel panel-default attributes-view",
        initialize: function (opt) {
            this.model = opt;
            this.template = Handlebars.compile($("#database-collection-attributes-view-template").html());
        }
    });

});