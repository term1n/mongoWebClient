/**
 * Created by manaev on 27.11.14.
 */
MongoWebClient.module("DatabaseLayout", function (DatabaseLayout, MongoWebClient, Backbone, Marionette, $, _) {

    DatabaseLayout.Entity = Backbone.Model.extend({});

    DatabaseLayout.CollectionEntity = Backbone.Model.extend({
        url:"/mongoWebClient/mongo/viewCollectionEntity",
        fetch: function (opt) {
            Backbone.Model.prototype.fetch.call(this, {
                reset: true,
                type: "GET",
                data: opt,
                async: false,
                error:function(){
                    MongoWebClient.trigger("event:showErrorDialog",{modalHeader:"Error",modalBody:"Can't establish connection"});
                },
                success:function(collection, response, options){
                    if(response.status == "FAILED"){
                        MongoWebClient.trigger("event:showErrorDialog",{modalHeader:"Error",modalBody:response.model});
                    }
                }
            });
            return this;
        },
        parse: function (response, options) {
            return JSON.parse(response.model);
        }
    });

    DatabaseLayout.CollectionEntities = Backbone.Collection.extend({
        url:"/mongoWebClient/mongo/viewCollectionEntities",
        model:DatabaseLayout.Entity,
        comparator:"_id",
        initialize: function(opt){
            this.requestData = opt;
        },
        fetch: function () {
            Backbone.Collection.prototype.fetch.call(this, {
                reset: true,
                type: "GET",
                data: this.requestData,
                async: false,
                error:function(){
                    MongoWebClient.trigger("event:showErrorDialog",{modalHeader:"Error",modalBody:"Can't establish connection"});
                },
                success:function(collection, response, options){
                    if(response.status == "FAILED"){
                        MongoWebClient.trigger("event:showErrorDialog",{modalHeader:"Error",modalBody:response.model});
                    }
                }
            });
            return this;
        },
        parse: function (response, options) {
            return JSON.parse(response.model);
        }
    });

    DatabaseLayout.LayoutViewEntity = Backbone.Model.extend({
        sameAs: function(data){
            return this.get("name") == data.name && this.get("collName") == data.collName && this.get("dbName") == data.dbName;
        }
    });

    DatabaseLayout.LayoutViewCollection = Backbone.Collection.extend({
        model:DatabaseLayout.LayoutViewEntity,
        comparator:null,
        initialize:function(){
        }
    })

});