$(function (evt) {


    var username = 'joshlong';
    var password = 'password';
    var grantType = 'password';
    var scope = 'write';
    var clientId = 'android-tags';
    var clientSecret = '123456';
    var encodedClientUserAndPassword = btoa(clientId + ':' + clientSecret);

    $.ajax({
        type: 'POST',
        method: 'POST',
        dataType: "json",
        url: "http://localhost:8080/oauth/token",
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Authorization", "Basic " + encodedClientUserAndPassword);
        },
        data: {
            username: 'joshlong',
            password: 'password',
            grant_type: 'password',
            scope: 'write',
            client_secret: '123456',
            client_id: 'android-tags'
        },
        xhrFields: {
            withCredentials: true
        },
        success: function (data) {
            var accessToken = data ['access_token'] ;
            console.log( accessToken);


        }
    });


});