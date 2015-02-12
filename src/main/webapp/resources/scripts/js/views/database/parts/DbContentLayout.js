/**
 * Created by manaev on 28.11.14.
 */
MongoWebClient.module("DatabaseLayout", function (DatabaseLayout, MongoWebClient, Backbone, Marionette, $, _) {

    DatabaseLayout.ContentViewHolder = Backbone.Marionette.CollectionView.extend({
        template: null,
        tagName: "div",
        className: "panel panel-default no-border-radius-top",
        childView: DatabaseLayout.ContentViewElement,
        onRender: function () {
            this.$el.css("border-top", 0);
            if (this.collection.length == 0) {
                this.$el.append(Handlebars.compile($("#database-content-view-holder-empty-template").html())());
                this.$el.css("padding", 20);
            }
        }
    });

    DatabaseLayout.ContentLayout = Backbone.Marionette.LayoutView.extend({
        template: null,
        tagName: "div",
        className: "tab-pane",
        regions: {
            consoleEl: ".console",
            contentEl: ".content",
            attributesEl: ".attributes"
        },
        initialize: function () {
            this.template = Handlebars.compile($("#database-content-template").html());
            this.tabControlTpl = Handlebars.compile($("#database-tab-control-template").html());
            var temp = this.model.get("name") + "_" + this.model.get("dbName") + "_" + this.model.get("collName");
            this.model.set("tabName", temp);
            this.$el.attr("role", "tabpanel");
            $("#dbContent-tab-content").find('div.active').removeClass('active');
            this.$el.addClass("active");
        },
        onShow: function () {
            this.$el.prop("id", this.model.attributes.contentId);
            $("#dbContent-tab-panel").find('li.active').removeClass('active');
            $("#dbContent-tab-panel").append(this.tabControlTpl(this.model.attributes));
            this.model.attributes["skip"] = 0;
            this.model.attributes["limit"] = 50;
            this.consoleEl.show(new DatabaseLayout.ConsoleView({model: new DatabaseLayout.ConsoleEntity(this.model.attributes)}));
            this.attributesEl.show(new DatabaseLayout.AttributesView(new DatabaseLayout.AttributesEntity(this.model.attributes)));
            this.contentEl.show(new DatabaseLayout.ContentViewHolder({collection: new DatabaseLayout.CollectionEntities(this.model.attributes).fetch()}));
            var self = this;
            this.listenTo(this.attributesEl.currentView, "event:refreshView", function () {
                self.refreshView()
            });
            this.listenTo(this.attributesEl.currentView, "event:addEntry", function () {
                var e_edb = new DatabaseLayout.EntityEditor({model: {header: "New Object", data: "", requestData: self.attributesEl.currentView.model.attributes}});
                MongoWebClient.eEditRegion.show(e_edb);
                e_edb.showDialog();
                self.listenTo(e_edb,"event:refreshViewAfterUpdate",function(){self.refreshView()});
            });

            this.listenTo(this.consoleEl.currentView, "event:refreshQueryResult", function (data, query) {
                self.refreshQueryResult(data, query)
            });
            var selector = "." + this.model.attributes.contentId + " .close";
            $("#dbContent-tab-panel").on("click", selector, {elem: this}, this.doClose);
        },
        toType: function (obj) {
            return ({}).toString.call(obj).match(/\s([a-z|A-Z]+)/)[1].toLowerCase();
        },
        refreshView: function () {
            this.consoleEl.currentView.model.attributes.limit = this.attributesEl.currentView.model.attributes.limit
            this.consoleEl.currentView.model.attributes.skip = this.attributesEl.currentView.model.attributes.skip
            this.attributesEl.currentView.refreshTotal(this.consoleEl.currentView.model.attributes);
            this.contentEl.show(new DatabaseLayout.ContentViewHolder({collection: new DatabaseLayout.CollectionEntities(this.consoleEl.currentView.model.attributes).fetch()}));
        },
//        TODO actually deprecated method
        refreshQueryResult: function (data, query) {
            if (this.toType(JSON.parse(data)) == "number") {
                var coll = new DatabaseLayout.CollectionEntities([{numb:data}]);
                this.attributesEl.currentView.setTotal(data);
                this.consoleEl.currentView.model.attributes.limit = this.attributesEl.currentView.model.attributes.limit;
                this.consoleEl.currentView.model.attributes.skip = this.attributesEl.currentView.model.attributes.skip;
                coll.requestData = this.attributesEl.currentView.model.attributes;
                this.contentEl.show(new DatabaseLayout.ContentViewHolder({collection: coll}));
            } else {
                var coll = new DatabaseLayout.CollectionEntities(JSON.parse(data));
                if (!query) {
                    this.attributesEl.currentView.refreshTotal();
                } else {
                    this.attributesEl.currentView.refreshTotal(query);
                }
                this.consoleEl.currentView.model.attributes.limit = this.attributesEl.currentView.model.attributes.limit;
                this.consoleEl.currentView.model.attributes.skip = this.attributesEl.currentView.model.attributes.skip;
                coll.requestData = this.attributesEl.currentView.model.attributes;
                this.contentEl.show(new DatabaseLayout.ContentViewHolder({collection: coll}));
            }

        },
        doClose: function (evt) {
            DatabaseLayout.ContentControllerLayout.removeView(evt.data.elem);
            evt.data.elem.destroy();
            evt.currentTarget.parentNode.parentNode.remove();
            $("#dbContent-tab-panel").find('li a').first().click();
        }
    });

    DatabaseLayout.ContentHolder = Backbone.Marionette.CompositeView.extend({
        childView: DatabaseLayout.ContentLayout,
        childViewContainer: "#dbContent-tab-content",
        tagName: "div",
        className: "container-fluid",
        initialize: function () {
            this.template = Handlebars.compile($("#database-content-layout-template").html());
        },
        onBeforeAddChild: function (childView) {
            var temp = childView.model.get("name") + "_" + childView.model.get("dbName") + "_" + childView.model.get("collName").replace(".", "_") + "_" + Date.now();
            childView.model.set("contentId", temp);
        }
    });
})
;