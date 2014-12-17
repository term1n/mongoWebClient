/**
 * Created by manaev on 17.10.14.
 */
MongoWebClient.module("Templates", function (Templates, MongoWebClient, Backbone, Marionette, $, _) {
//Finally, all templates should appear only here
//    Templates.navigationPanel = [
//        "<div class='navbar-header'>",
//        "<span class='navbar-brand h-cursor-pointer' id='mWcAppName'>{{appName}}</span>",
//        "</div>",
//        "<div class='collapse navbar-collapse'>",
//        "<ul class='nav navbar-nav'>",
//        "<li id='create-connection'>",
//        "<a class='h-cursor-pointer'>Create connection</a>",
//        "</li>",
//        "<li>",
//        "</li>",
//        "</ul>",
//        "</div>"
//    ].join("\n");
    Templates.mongoWebClientManual = [
        "<div>",
        "<h1>Mongo Web Client</h1>",
        "<p class='lead'>Comilito equinus</p>",

        "<p class='lead'>Orbitas lacuna</p>",

        "<p class='lead'>G'Odhun A'l Korok Boda Uh'm</p>",

        "<p class='lead'>Boda Uh'm Ron'Kashal</p>",

        "<p class='lead'>Detrimentum a do sola ditas.</p>",
        "</div>"
    ].join("\n");
});