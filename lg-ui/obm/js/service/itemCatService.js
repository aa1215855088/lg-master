app.service('itemCatService',function($http){

    this.findAll = function(){
        return $http.get("http://localhost:8081/tbItemCat")
    }

})