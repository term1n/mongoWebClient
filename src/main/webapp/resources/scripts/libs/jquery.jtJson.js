/**
 * Created by IlyaMan on 07.12.2014.
 */
(function ($) {
    var _tjJson = "tjJson";
    $.fn[_tjJson] = function (opts) {
        var tjJsonApp = {};
        var data = opts.data;

        function toType(obj) {
            return ({}).toString.call(obj).match(/\s([a-z|A-Z]+)/)[1].toLowerCase();
        }

        tjJsonApp.View = Backbone.View.extend({
            tagName: "li",
            childViewContainer: ".tjJson-child-container",
            events: {
                "click .glyphicon": "doToggle"
            },
            doToggle: function (evt) {
                tjJsonApp.toggler(this.$el, evt);
            }
        });
        tjJsonApp.ObjectView = tjJsonApp.View.extend({
            className: "tjJson-object",
            initialize: function (opts) {
                this.name = opts.name;
                this.template = Handlebars.compile("<span class='glyphicon glyphicon-minus'  style='display: none'/><span class='glyphicon glyphicon-plus'/><span class='tjJson-key'>{{name}}</span><span class='tjJson-label'>{Object}</span><ul class='tjJson-child-container' style='display: none'></ul>");
            },
            render: function () {
                this.$el.append(this.template({name: this.name}));
                if (toType(this.model) == "object") {
                    for (var prop in this.model) {
                        tjJsonApp.defineView(this.$el.find(".tjJson-child-container").first(), this.model[prop], prop);
                    }
                }
                return this.$el;
            }
        });
        tjJsonApp.ArrayView = tjJsonApp.View.extend({
            className: "tjJson-array",
            initialize: function (opts) {
                this.name = opts.name;
                this.template = Handlebars.compile("<span class='glyphicon glyphicon-minus' style='display: none'/><span class='glyphicon glyphicon-plus'/><span class='tjJson-key'>{{name}}</span><span class='tjJson-label'>[Array{{length}}]</span><ul class='tjJson-child-container' style='display: none'></ul>");
            },
            render: function () {
                this.$el.append(this.template({name: this.name, length: this.model.length}));
                var counter = 1;
                _.each(this.model, function (item) {
                    tjJsonApp.defineView(this.$el.find(".tjJson-child-container").first(), item, counter);
                    counter++;
                }, this);
                return this.$el;
            }
        });
        tjJsonApp.toggler = function ($element, evt) {
            $element.find(".tjJson-label").first().toggle("hide");
            $element.find(".glyphicon-minus").first().toggle("hide");
            $element.find(".glyphicon-plus").first().toggle("hide");
            $element.find(".tjJson-child-container").first().toggle("hide");
            evt.stopPropagation();
        };
        tjJsonApp.defineView = function ($container, data, name) {
            if (toType(data) == "string") {
                $container.append(new tjJsonApp.StringView({
                    model: {
                        prop: name,
                        data: data
                    }
                }).render());
            } else if (toType(data) == "number" || toType(data) == "boolean") {
                $container.append(new tjJsonApp.NumberView({
                    model: {
                        prop: name,
                        data: data
                    }
                }).render());
            } else if (toType(data) == "object") {
                $container.append(new tjJsonApp.ObjectView({
                    model: data,
                    name: name
                }).render());
            } else if (toType(data) == "array") {
                $container.append(new tjJsonApp.ArrayView({
                    model: data,
                    name: name
                }).render());
            }
        };
        tjJsonApp.StringView = tjJsonApp.View.extend({
            events: {
                "click .hoverable": "doToggle"
            },
            doToggle: function (evt) {
                this.$el.find(".tjJson-string").first().toggle("hide");
                this.$el.find(".tjJson-string-short").first().toggle("hide");
                evt.stopPropagation();
            },
            initialize: function () {
                this.template = Handlebars.compile("{{#if prop}}<span class='tjJson-value hoverable'> {{prop}}: </span>{{/if}}<span class='tjJson-string' style='display: none'> {{data}}</span><span class='tjJson-string-short'>{{#cutter data 'longer' 100}}{{this}}{{/cutter}}</span>");
            },
            render: function () {
                this.$el.append(this.template(this.model));
                if(this.model.data && this.model.data.length < 100){
                    this.$el.find(".hoverable").removeClass("hoverable");
                }
                return this.$el;
            }
        });
        tjJsonApp.NumberView = tjJsonApp.View.extend({
            initialize: function () {
                this.template = Handlebars.compile("{{#if prop}}<span class='tjJson-value'> {{prop}}: </span>{{/if}}<span class='tjJson-number'> {{data}}</span>");
            },
            render: function () {
                this.$el.append(this.template(this.model));
                return this.$el;
            }
        });
        tjJsonApp.start = function ($container, data) {
            var $ul = $('<ul>');
            $ul.addClass("tjJson-child-container");
            for (var prop in data) {
                var $li = $('<li>');
                tjJsonApp.defineView($li, data[prop], prop);
                $ul.append($li);
            }
            $container.append($ul);
        };
        tjJsonApp.start(this, data);
    };
})(window.jQuery);

Handlebars.registerHelper("cutter", function (v1, op, v2, options) {
    if(op == "longer"){
        if (v1.length > v2) {
            if(v1.indexOf("<?xml version=") != -1){
                return "click to view xml";
            }
            return v1.substring(0, v2).toString()+"...";
        } else {
            return v1;
        }
    }
});