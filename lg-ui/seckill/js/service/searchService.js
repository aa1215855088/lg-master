app.service('searchService', function ($http) {


    this.search = function (searchMap) {
        return $http.post('http://localhost:8099/search', searchMap);
    }

});