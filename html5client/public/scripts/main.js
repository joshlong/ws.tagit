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
    var appName = 'tagit';
     angular.module(appName, [])
        .controller('TagController', ['$scope', '$http', '$log' , 'TagService', function ($scope, $http, $log, tagService) {
//            $scope.message = 'Got the accessToken: ' + accessToken;
            $('#x').click(tagService.read)
        }])
        .service('TagService', function ($http) {
        })
        .run([ '$window' , '$http', function ($window, $http) {

            // todo this should only be after the first successful HTTP call
            // $http.defaults.headers.common['Authorization'] = 'Bearer ' + accessToken;
            $http({
                method: 'POST',
                params: {
                    'username': username,
                    'password': password,
                    'grant_type': 'password',
                    'scope': 'write',
                    'client_secret': clientSecret,
                    'client_id': clientId
                },
                url: urlBase + "/oauth/token",
                headers: {
                    'Authorization': "Basic " + encodedClientUserAndPassword
                }
            })
                .success(function (data, status, headers, config) {
                    // this callback will be called asynchronously
                    // when the response is available
                    $http.defaults.headers.common['Authorization'] = 'Bearer ' + data['access_token'];
                })
                .error(function (data, status, headers, config) {
                    // called asynchronously if an error occurs
                    // or server returns response with an error status.
                });
        }])
    ;
    angular.bootstrap(document, [ appName ]);
});



function setup(urlBase, require, angular, $, accessToken) {

    var appName = 'tagit';

    console.log('received accessToken: ' + accessToken);

    angular.module(appName, [])
        .controller('TagController', ['$scope', '$http', '$log' , 'TagService', function ($scope, $http, $log, TagService) {
            $scope.message = 'Got the accessToken: ' + accessToken;


        }])
        .service('TagService', function ($http) {
            $http({
                method: 'GET',
                url: urlBase + '/tags'//,
                //params: 'limit=10, sort_by=created:desc',
                //headers: {'Authorization': 'Token token=xxxxYYYYZzzz'}
            }).success(function (data) {
                // With the data succesfully returned, call our callback
                console.log(JSON.stringify(data))
            }).error(function () {
                alert("error");
            });

        })
        .run([ '$window' , '$http', function ($window, $http) {
            $http.defaults.headers.common['Authorization'] = 'Bearer ' + accessToken;
        }]);


    angular.bootstrap(document, [ appName ]);

}


/**
 Rewrite this as an angular.js controller that initializes by calling the post method using  jquery.
 it should wrap the callback in $.apply( ... ) ; */
