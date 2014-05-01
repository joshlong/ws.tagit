/**
 * myApp.factory('authInterceptor', function ($rootScope, $q, $window) {
  return {
    request: function (config) {
      config.headers = config.headers || {};
      if ($window.sessionStorage.token) {
        config.headers.Authorization = 'Bearer ' + $window.sessionStorage.token;
      }
      return config;
    },
    response: function (response) {
      if (response.status === 401) {
        // handle the case where the user is not authenticated
      }
      return response || $q.when(response);
    }
  };
});

 myApp.config(function ($httpProvider) {
  $httpProvider.interceptors.push('authInterceptor');
});
 */
$(function (evt) {
    // alert('moo')

// Create the XHR object.
    /*
     function cors(method, url, prep, load, error) {
     var xhr = new XMLHttpRequest();
     if ("withCredentials" in xhr) {
     // XHR for Chrome/Firefox/Opera/Safari.
     xhr.open(method, url, true);
     } else if (typeof XDomainRequest != "undefined") {
     // XDomainRequest for IE.
     xhr = new XDomainRequest();
     xhr.open(method, url);
     } else {
     xhr = null;
     }
     if (!xhr) {
     console.log('CORS not supported');
     }
     prep(xhr);
     xhr.onload = load;
     xhr.onerror = error;
     xhr.send();
     }*/

    /*
     curl -i -H "Access-Control-Request-Method: POST" -H "Origin: http://localhost" -X OPTIONS http://localhost:8080/oauth/token
     */
    /*  cors(
     'POST',
     'http://localhost:8080/oauth/token',
     function (*/
    /* prep */
    /*xhr) {

     var headers = {
     'Access-Control-Request-Method': 'POST',
     'Origin': 'http://localhost:9000/client.html'
     };

     for (var k in headers) {
     xhr.setRequestHeader(k, headers[k]);
     }


     },
     function (*/
    /*load */
    /* xhr) {
     },
     function (*/
    /*error*/
    /*) {
     }
     );
     */

    $.ajax({
        type: 'POST', method: 'POST',
        dataType: "json",
        //  cache: false,
        url: "http://localhost:8080/oauth/token",
        beforeSend: function (xhr) {
            //  xhr.setRequestHeader("Origin", 'http://localhost:9000/hello.html');
            //  xhr.setRequestHeader("Authorization", make_base_auth("foo","bar"));
        },
        xhrFields: {
            withCredentials: true
        },
        success: function (data) {
            console.log(data);
        }
    });

    /*
     var clientId = 'android-tags';
     var clientSecret = '123456';
     var username = 'joshlong';
     var password = 'password';
     var oauthTokenEndpoint = "http://localhost:8080/oauth/token";
     $.ajax({
     type: 'POST',
     url: oauthTokenEndpoint,
     dataType: 'json',
     async: false,
     headers: {
     "Authorization": btoa(clientId + ":" + clientSecret)
     },

     crossDomain: true ,
     xhrFields: {
     withCredentials: true
     },
     data: {
     username: username,
     password: password,
     grant_type: 'password',
     scope: 'write',
     client_secret: clientSecret,
     client_id: clientId
     },
     success: function (response) {
     alert( JSON.stringify (response))
     }
     });
     */


});