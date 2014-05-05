require.config({
    baseUrl: 'bower_components',
    paths: {
        angular: 'angular/angular',
        jquery: 'jquery/dist/jquery',
        domReady: 'requirejs-domready/domReady'
    },
    shim: {
        angular: {
            exports: 'angular'
        }
    }
});

define([ 'require', 'angular', 'jquery' ], function (require, angular, $) {
    'use strict';
    var username = 'rodj';
    var password = 'password';
    var grantType = 'password';
    var scope = 'write';
    var clientId = 'android-tags';
    var urlBase = 'http://localhost:8080';
    var clientSecret = '123456';
    var encodedClientUserAndPassword = btoa(clientId + ':' + clientSecret);

    $.ajax({
        type: 'POST',
        method: 'POST',
        cache: true,
        dataType: "json",
        url: urlBase + "/oauth/token", // built in caching should
        // return the same value anyway, right?
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Authorization", "Basic " + encodedClientUserAndPassword);
        },
        data: {
            username: username,
            password: password,
            grant_type: 'password',
            scope: 'write',
            client_secret: clientSecret,
            client_id: clientId
        },
        xhrFields: {
            withCredentials: true
        },
        context: {},
        success: function (data) {
            var accessToken = data ['access_token'];
            setup( urlBase, require, angular, $, accessToken);
        }
    });

});

function setup( urlBase, require, angular, $, accessToken) {

    var appName = 'tagit';

    console.log('received accessToken: ' + accessToken);

    /*angular.module('app', ['ngRoute', 'ngResource']).run(function($http) {
     $http.defaults.headers.common.language='fr-FR';
     });*/

    angular.module(appName, [])
        .controller('TagController', ['$scope', '$http', '$log' ,'TagService', function ($scope, $http, $log, TagService) {
            $scope.message = 'Got the accessToken: ' + accessToken;


        }])
        .service(  'TagService', function($http){
            $http({
                method: 'GET',
                url: urlBase + '/tags'//,
                //params: 'limit=10, sort_by=created:desc',
                //headers: {'Authorization': 'Token token=xxxxYYYYZzzz'}
            }).success(function(data){
                // With the data succesfully returned, call our callback
                 console.log( JSON.stringify(  data ))
            }).error(function(){
                alert("error");
            });

        })
        .run ( [ '$window' , '$http',   function($window, $http ){
          $http.defaults.headers.common['Authorization'] ='Bearer ' + accessToken ;
        }]);


    angular.bootstrap(document, [ appName ]);

}


/**
 Rewrite this as an angular.js controller that initializes by calling the post method using  jquery.
 it should wrap the callback in $.apply( ... ) ; */
