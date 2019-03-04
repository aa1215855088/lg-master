app.service("indexService", function ($http) {

    this.getName = function () {
        return $http.get("http://localhost:8084/tbSeller/sellerInfo")
    }

});