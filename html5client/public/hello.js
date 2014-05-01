$(function (evt) {

    var clientId = 'android-tags';
    var clientSecret = '123456';
    var username = 'joshlong';
    var password = 'password';
    var oauthTokenEndpoint = "http://localhost:8080/oauth/token";
    $.ajax({
        method: 'POST',
        url: oauthTokenEndpoint,
        dataType: 'json',
        async: false,
        headers: {
            "Authorization": btoa(clientId + ":" + clientSecret)
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


});