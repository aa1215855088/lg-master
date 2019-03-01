app.service("loginService",function ($http) {

    this.login=function (username,password) {
        return $http.post("" +
            "?username="+username+"&password="+password)
    }
});