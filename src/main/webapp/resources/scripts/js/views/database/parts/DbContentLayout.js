/**
 * Created by manaev on 28.11.14.
 */
MongoWebClient.module("DatabaseLayout", function (DatabaseLayout, MongoWebClient, Backbone, Marionette, $, _) {

    DatabaseLayout.ContentViewHolder = Backbone.Marionette.CollectionView.extend({
        template: null,
        tagName: "div",
        className:"panel panel-default no-border-radius-top",
        childView: DatabaseLayout.ContentViewElement,
        onRender: function() {
            this.$el.css("border-top",0);
            if(this.collection.length == 0){
                this.$el.append(Handlebars.compile($("#database-content-view-holder-empty-template").html())());
                this.$el.css("padding",20);
            }
        }
    });

    DatabaseLayout.ContentLayout = Backbone.Marionette.LayoutView.extend({
        template: null,
        tagName:"div",
        className:"tab-pane",
        regions:{
            consoleEl:".console",
            contentEl:".content",
            attributesEl:".attributes"
        },
        initialize: function () {
            this.template = Handlebars.compile($("#database-content-template").html());
            this.tabControlTpl = Handlebars.compile($("#database-tab-control-template").html());
            var temp = this.model.get("name")+"_"+this.model.get("dbName")+"_"+this.model.get("collName");
            this.model.set("tabName",temp);
            this.$el.attr("role", "tabpanel");
            $("#dbContent-tab-content").find('div.active').removeClass('active');
            this.$el.addClass("active");
        },
        onShow: function() {
            this.$el.prop("id", this.model.attributes.contentId);
            $("#dbContent-tab-panel").find('li.active').removeClass('active');
            $("#dbContent-tab-panel").append(this.tabControlTpl(this.model.attributes));
            this.consoleEl.show(new DatabaseLayout.ConsoleView());
            this.attributesEl.show(new DatabaseLayout.AttributesView(new DatabaseLayout.Entity(this.model.attributes)));
            this.contentEl.show(new DatabaseLayout.ContentViewHolder({collection:new DatabaseLayout.CollectionEntities(this.model.attributes).fetch()}));
        }
    });

    DatabaseLayout.ContentHolder = Backbone.Marionette.CompositeView.extend({
        childView: DatabaseLayout.ContentLayout,
        childViewContainer: "#dbContent-tab-content",
        tagName:"div",
        className:"container-fluid",
        initialize:function(){
            this.template = Handlebars.compile($("#database-content-layout-template").html());
        },
        onBeforeAddChild:function(childView){
            var temp = childView.model.get("name")+"_"+childView.model.get("dbName")+"_"+childView.model.get("collName")+"_"+Date.now();
            childView.model.set("contentId",temp);
        }
    });
});