/**
 * Created by manaev on 28.11.14.
 */
/**
 * Created by manaev on 28.11.14.
 */
MongoWebClient.module("DatabaseLayout", function (DatabaseLayout, MongoWebClient, Backbone, Marionette, $, _) {

    Handlebars.registerHelper("json", function (context) {
        if (({}).toString.call(context).match(/\s([a-z|A-Z]+)/)[1].toLowerCase() != 'string') {
            return JSON.stringify(context,null,"\t");
        } else {
            return context
        }
    });

    DatabaseLayout.ContentViewElement = Backbone.Marionette.ItemView.extend({
        events: {
            "click .contentViewHeader": "viewEntry",
            "contextmenu .contentViewHeader": "fireContextMenu"
        },
        viewEntry: function () {
            var temp = JSON.parse(JSON.stringify(this.model.collection.requestData));
            try {
                /**
                 * system.indexes bug
                 */
                temp["id"] = this.model.attributes["_id"]["$oid"];
                this.$el.find(".contentViewBody").toggleClass("hidden");
                if (this.$el.find(".contentViewBody").first().is(':visible')) {
                    var solo = new DatabaseLayout.CollectionEntity().fetch(temp);
                    this.$el.find(".contentViewBody").tjJson({data: solo.attributes});
                }
            } catch (e) {
//                ignore
            }

        },
        editEntry:function(){
            var temp = JSON.parse(JSON.stringify(this.model.collection.requestData));
            try {
                /**
                 * system.indexes bug
                 */
                temp["id"] = this.model.attributes["_id"]["$oid"];
                if(this.$el.find(".contentViewBody").is(":visible")){
                    this.$el.find(".contentViewBody").toggleClass("hidden");
                }
                var solo = new DatabaseLayout.CollectionEntity().fetch(temp);
                var e_edb = new  DatabaseLayout.EntityEditor({model:{header: this.model.attributes["_id"]["$oid"], data:solo.attributes, requestData: this.model.collection.requestData}});
                MongoWebClient.eEditRegion.show(e_edb);
                e_edb.showDialog();
            } catch (e) {
//                ignore
            }
        },
        fireContextMenu: function (evt) {
            var self = this;
            evt.preventDefault();
            $("#entry-menu-placeholder").show().css({ position: "absolute", left: MongoWebClient.locate(evt).left, top: MongoWebClient.locate(evt).top}).off('click')
                .mouseleave(function () {
                    $(this).hide();
                })
                .on('click', self, this.clickMenuItem);
        },
        clickMenuItem: function (evt) {
            $(this).hide();
//            TODO manage evt situations
            if ($(evt.target).hasClass("editEntry")) {
                evt.data.editEntry();
            }
            if ($(evt.target).hasClass("deleteEntry")) {
                console.log("deleteEntry");
            }
            if ($(evt.target).hasClass("viewEntry")) {
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
        className: "panel panel-default console-view",
        initialize: function () {
            this.template = Handlebars.compile($("#database-console-template").html());
        }
    });

    DatabaseLayout.AttributesView = Backbone.Marionette.ItemView.extend({
        template: null,
        tagName: "div",
        className: "panel panel-default attributes-view",
        initialize: function (opt) {
            this.model = opt;
            this.template = Handlebars.compile($("#database-collection-attributes-view-template").html());
        }
    });

    DatabaseLayout.EntityEditor = Backbone.Marionette.ItemView.extend({
        tagName: "div",
        events: {
            "click .validate": "bValidate",
            "click .save": "bSave",
            "click .maximize":"maximizePre"
        },
        initialize: function () {
            this.template = Handlebars.compile($("#edit-entry-modal-dialog").html());
        },
        bValidate: function () {
            try {
                JSON.parse(this.$el.find("pre").html());
                return true;
            } catch (e) {
                MongoWebClient.trigger("event:showErrorDialog",{modalHeader:"Error",modalBody:"Invalid json " + e});
                return false;
            }
        },
        maximizePre:function(){
            console.log("maximize");
            this.$el.find("pre").toggleClass("mwc-edit-sm-h");
        },
        render: function () {
            this.$el.append(this.template(this.model));
            return this.$el;
        },
        bSave: function () {
            if (this.bValidate()) {
                console.log("save");
                var dataToSend = JSON.parse(JSON.stringify(this.model.requestData));
                dataToSend["dbObject"] =  JSON.stringify(JSON.parse(this.$el.find("pre").html()));
                $.ajax({
                    dataType: "json",
                    type: "POST",
                    data: dataToSend,
                    url:"/mongoWebClient/mongo/updateEntity",
                    success: function (data) {
                        if (data.status === 'SUCCESS' && data.model) {
                            MongoWebClient.trigger("event:showSuccessDialog",{modalHeader:"Success",modalBody:"Object updated"})
                        } else{
                            MongoWebClient.trigger("event:showErrorDialog",{modalHeader:"Error",modalBody:data.model});
                        }
                    },
                    error:function(){
                        console.log("error")
                        MongoWebClient.trigger("event:showErrorDialog",{modalHeader:"Error",modalBody:"Can't establish connection"});
                    }
                });
            }
        },
        showDialog: function(){
             this.$el.find('.modal').modal('show');
        }
    })

});