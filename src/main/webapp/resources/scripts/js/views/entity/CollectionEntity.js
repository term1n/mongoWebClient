/**
 * Created by manaev on 27.11.14.
 */
MongoWebClient.module("CollectionEntity", function (CollectionEntity, MongoWebClient, Backbone, Marionette, $, _) {

    CollectionEntity.Entity = Backbone.Model.extend({
    });

    CollectionEntity.Collection = Backbone.Collection.extend({
        url:"/mongoWebClient/mongo/viewCollectionEntities",
        model:CollectionEntity.Entity,
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
        },
        parse: function (response, options) {
            return response.model;
        }
    });
});