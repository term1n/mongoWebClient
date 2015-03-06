/**
 * Created by manaev on 05.03.15.
 */
/**
 * Created by manaev on 26.11.14.
 */
MWCAdmin.module("MWCNUserMan", function (MWCNUserMan, MWCAdmin, Backbone, Marionette, $, _) {
    Handlebars.registerHelper('stripeRows', function (rows, fn) {
        var buffer = [],
            i, len;

        for (i = 0, len = rows.length; i < len; ++i) {
            var row = rows[i];
            row.rowClass = (i + 1) % 2 === 0 ? 'even-div' : 'odd-div';

            buffer.push(fn(row));
        }

        return buffer.join('');
    });
    MWCNUserMan.SimpleModel = Backbone.Model.extend({});
    MWCNUserMan.MWCUser = Backbone.Model.extend({
        update: function () {
            this.attributes["_csrf"] = $("#logoutForm").find("input").val();
            this.attributes["sauthorities"] = JSON.stringify(this.get("authorities"));
            $.ajax({
                dataType: "json",
                type: "POST",
                data: this.attributes,
                url: "/mongoWebClient/ad_services/updateUser",
                success: function (data) {
                    if (data.status === 'SUCCESS' && data.model) {
                        MongoWebClient.trigger("event:showSuccessDialog", {modalHeader: "Success", modalBody: "User data updated"});
                    } else {
                        MongoWebClient.trigger("event:showErrorDialog", {modalHeader: "Error", modalBody: "Internal error" + JSON.stringify(data.model)});
                    }
                },
                error: function (err) {
                    MongoWebClient.trigger("event:showErrorDialog", {modalHeader: "Error", modalBody: JSON.stringify(err)});
                }
            });
        },
        deleteUser: function () {
            this.attributes["_csrf"] = $("#logoutForm").find("input").val();
            $.ajax({
                dataType: "json",
                type: "GET",
                data: this.attributes,
                url: "/mongoWebClient/ad_services/removeUser",
                success: function (data) {
                    if (data.status === 'SUCCESS' && data.model) {
                        MongoWebClient.trigger("event:showSuccessDialog", {modalHeader: "Success", modalBody: "User data removed"});
                    } else {
                        MongoWebClient.trigger("event:showErrorDialog", {modalHeader: "Error", modalBody: "Internal error" + JSON.stringify(data.model)});
                    }
                },
                error: function (err) {
                    MongoWebClient.trigger("event:showErrorDialog", {modalHeader: "Error", modalBody: JSON.stringify(err)});
                }
            });
        }
    });

    MWCNUserMan.MWCUsers = Backbone.Collection.extend({
        url: "/mongoWebClient/ad_services/getMWCUsers",
        model: MWCNUserMan.MWCUser,
        comparator: "_id",
        fetch: function () {
            Backbone.Collection.prototype.fetch.call(this, {
                reset: true,
                type: "GET",
                async: false,
                error: function () {
                    MongoWebClient.trigger("event:showErrorDialog", {modalHeader: "Error", modalBody: "Can't establish connection"});
                },
                success: function (collection, response, options) {
                    if (response.status == "FAILED") {
                        MongoWebClient.trigger("event:showErrorDialog", {modalHeader: "Error", modalBody: response.model});
                    }
                }
            });
            return this;
        },
        parse: function (response, options) {
            return response.model;
        }
    });

    MWCNUserMan.ShowUser = Backbone.Marionette.ItemView.extend({
        tagName: "div",
        className: "row padding-t10 mwc-users-childRow",
        events: {
            "click .removeRole": "removeRole",
            "click .remove-user-button": "removeUser"
        },
        modelEvents: {
            'change': 'render'
        },
        removeUser: function () {
            this.model.deleteUser();
            this.destroy();
        },
        removeRole: function (evt) {
            if (this.hasRole(evt.currentTarget.getAttribute("roletodel"), true)) {
                this.model.update();
                this.model.trigger("change");
            }
        },
        initialize: function () {
            this.template = Handlebars.compile($("#nusersman-collection-item-tpl").html());
        },
        onRender: function () {
            var self = this;
            this.$el.find(".first-dropdown li a").click(function () {
                if (!self.hasRole($(this).text())) {
                    self.model.get("authorities").push({role: $(this).text()});
                    self.model.update();
                    self.model.trigger("change");
                }
            });
        },
        hasRole: function (role, toRemove) {
            var result = false;
            for (var i = 0; i < this.model.get("authorities").length; i++) {
                if (this.model.get("authorities")[i].role == role) {
                    if (toRemove) {
                        this.model.get("authorities").splice(i, 1);
                    }
                    result = true;
                }
            }
            return result;
        }
    });

    MWCNUserMan.ShowUsers = Backbone.Marionette.CompositeView.extend({
        childView: MWCNUserMan.ShowUser,
        childViewContainer: "#nusersman-item-container",
        tagName: "div",
        className: "panel panel-default",
        events: {
            "click .addUser": "addUser"
        },
        initialize: function () {
            this.template = Handlebars.compile($("#nusersman-collection-tpl").html());
        },
        onRender: function () {
            this.$el.find(this.childViewContainer).find(".mwc-users-childRow:odd").css("background-color", "#fcf8e3")
        },
        addUser: function () {
            MWCAdmin.showAddUserDialog();
        }
    });

    MWCNUserMan.AddUserDialog = Marionette.ItemView.extend({
        template: null,
        tagName: "div",
        className: "container-fluid",
        events: {
            "click .btn-add-user": "addUser"
        },
        serializeObject: function (elem) {
            var o = {};
            var a = elem.serializeArray();
            $.each(a, function () {
                if (o[this.name]) {
                    if (!o[this.name].push) {
                        o[this.name] = [o[this.name]];
                    }
                    o[this.name].push(this.value || '');
                } else {
                    o[this.name] = this.value || '';
                }
            });
            return o;
        },
        addUser: function () {
            if (this.validatorr.form()) {
                var dataToSend = this.serializeObject(this.$el.find('form'));
                dataToSend["_csrf"] = $("#logoutForm").find("input").val();
                $.ajax({
                    dataType: "json",
                    type: "POST",
                    data: dataToSend,
                    url: "/mongoWebClient/ad_services/addUser",
                    success: function (data) {
                        if (data.status === 'SUCCESS' && data.model) {
                            MWCAdmin.showNUserManagement();
                            MongoWebClient.trigger("event:showSuccessDialog", {modalHeader: "Success", modalBody: "User added"});
                        } else {
                            MongoWebClient.trigger("event:showErrorDialog", {modalHeader: "Error", modalBody: "Internal error" + JSON.stringify(data.model)});
                        }
                    },
                    error: function (err) {
                        MongoWebClient.trigger("event:showErrorDialog", {modalHeader: "Error", modalBody: JSON.stringify(err)});
                    }
                });
            }
        },
        initialize: function (opt) {
            this.model = opt;
            this.template = Handlebars.compile($("#add-user-modal-dialog-tpl").html());
        },
        showDialog: function () {
            this.$el.find('.modal').modal('show');
        },
        onRender: function () {
            this.validatorr = this.$el.find('form').validate({
                rules: {
                    username: {
                        minlength: 3,
                        maxlength: 20,
                        required: true
                    },
                    password: {
                        minlength: 4,
                        maxlength: 12,
                        required: true
                    },
                    eMail: {
                        email: true,
                        required: true
                    }
                },
                highlight: function (element) {
                    $(element).closest('.form-group').addClass('has-error');
                },
                unhighlight: function (element) {
                    $(element).closest('.form-group').removeClass('has-error');
                },
                errorElement: 'span',
                errorClass: 'help-block',
                errorPlacement: function (error, element) {
                    if (element.parent('.input-group').length) {
                        error.insertAfter(element.parent());
                    } else {
                        error.insertAfter(element);
                    }
                }
            });

        }
    });

})
;