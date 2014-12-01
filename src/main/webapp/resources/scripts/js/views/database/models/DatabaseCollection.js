/**
 * Created by manaev on 26.11.14.
 */
MongoWebClient.module("DatabaseLayout", function (DatabaseLayout, MongoWebClient, Backbone, Marionette, $, _) {

    DatabaseLayout.ConnectionCollection = Backbone.Model.extend({
        addConnection:function(opt){
            var tOpt = JSON.parse(JSON.stringify(opt));
            var temp = new DatabaseLayout.DatabaseCollection(tOpt);
            temp.fetch();
            /*for bootstrap accordion href working properly*/
            temp.each(function(model){model.set("dataParent","child"+tOpt.name)});
            this.models.push(temp);
        },
        initialize:function(){
            this.models = [];
        }
    });

    DatabaseLayout.DatabaseEntry = Backbone.Model.extend({
    });

    DatabaseLayout.DatabaseCollection = Backbone.Collection.extend({
        url:"/mongoWebClient/control/createMongoConnection",
        model:DatabaseLayout.DatabaseEntry,
        comparator:"dbName",
        initialize: function(opt){
            this.requestData = opt;
            this.connectionName = opt.name;
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

                    }else{
                        MongoWebClient.trigger("event:closeDMCDialog",this);
                    }
                }
            });
        },
        parse: function (response, options) {
            return response.model;
        }
    });
});