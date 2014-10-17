/**
 * Created by manaev on 17.10.14.
 */
MongoWebClient.module("Templates", function (Templates, ContactManager, Backbone, Marionette, $, _) {
//Finally, all templates should appear only here
    Templates.navigationPanel = [
        "<div class='navbar-header'>",
            "<span class='navbar-brand h-cursor-pointer'>{{appName}}</span>",
        "</div>",
        "<div class='collapse navbar-collapse'>",
        "<ul class='nav navbar-nav'>",
            "<li>",
                "<a class='h-cursor-pointer' id='create-connection'>Create connection</a>",
            "</li>",
        "</ul>",
        "</div>"
    ].join("\n");
});