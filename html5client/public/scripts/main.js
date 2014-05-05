require.config({
    baseUrl: 'bower_components',
    paths: {
        'angular': 'angular/angular',
        'angular-route': 'angular-route/angular-route',
        'jquery': 'jquery/dist/jquery',
        'domReady': 'requirejs-domready/domReady'
    },
    shim: {
        'angular': {
            deps: ['jquery'],
            exports: 'angular'
        },
        'angular-route': {
            deps: ['angular']
        }
    }
});

define([ 'require', 'angular', 'angular-route', 'jquery' ], function (require, angular, angularRouter, $) {
    'use strict';


    var grantType = 'password';
    var scope = 'write';
    var clientId = 'android-tags';
    var urlBase = 'http://localhost:8080';
    var clientSecret = '123456';
    var encodedClientUserAndPassword = btoa(clientId + ':' + clientSecret);
    var appName = 'tagit';

    angular.module(appName, ['ngRoute'])
        .config(['$routeProvider', function ($routeProvider) {
            $routeProvider.
                when('/login', {
                    templateUrl: 'login.html',
                    controller: 'LoginController'
                }).
                when('/tags', {
                    templateUrl: 'tags.html',
                    controller: 'TagsController'
                }).
                otherwise({
                    redirectTo: '/login'
                });
        }])
        .controller('LoginController', ['$window' , '$scope', '$http', '$log' , 'TagService', function ($window, $scope, $http, $log, tagService) {

            // preload
            $scope.username = 'joshl';
            $scope.password = 'password';

            var loadAccessToken = function (cb) {
                $scope.message = $scope.username + ':' + $scope.password;
                $http({
                    method: 'POST',
                    params: {
                        'username': $scope.username, // $window.prompt('Username?'),
                        'password': $scope.password,/// $window.prompt('Password?'),
                        'grant_type': grantType,
                        'scope': scope ,
                        'client_secret': clientSecret,
                        'client_id': clientId
                    },
                    url: urlBase + "/oauth/token",
                    headers: {
                        'Authorization': "Basic " + encodedClientUserAndPassword
                    }
                }).success(function (data, status, headers, config) {
                    $http.defaults.headers.common['Authorization'] = 'Bearer ' + data['access_token'];
                    cb();

                }).error(function (data, status, headers, config) {
                    console.log('could\'nt obtain an accessToken, ' +
                        'security can not be established.');
                });
            };


            $scope.login = function () {
                loadAccessToken(function () {

                    tagService.read(function (data) {
                        $scope.message = JSON.stringify(data);
                    });

                });
            };

        }])
        .controller('TagController', ['$scope', '$http', '$log' , 'TagService', function ($scope, $http, $log, tagService) {


        }])
        .service('TagService', function ($http) {
            return {
                read: function (callback) {
                    $http({
                        method: 'GET',
                        url: urlBase + '/tags'
                    }).success(function (data) {
                        console.log(JSON.stringify(data))
                        callback(data);
                    }).error(function (e) {
                        console.log("ERROR!!! " + ( e && JSON.stringify(e) || '{}'));
                    });
                }
            };
        })
        .run([ '$window' , '$http', function ($window, $http) {
            // setup?
        }]);

    angular.bootstrap(document, [ appName ]);


});



