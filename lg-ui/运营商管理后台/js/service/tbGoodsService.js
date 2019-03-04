app.service("tbGoodsService",function($http){

    this.findAll = function () {
        return $http.get("http://localhost:8081/tbGoods");
    }

    this.findPage = function(page,rows){
        return $http.get('http://localhost:8081/tbGoods/findPage?&page='+page+'&rows='+rows);
    }

    this.updateStatus = function(ids,status){
        return $http.get('http://localhost:8081/tbGoods/updateStatus?ids='+ids+"&status="+status);
    }

    this.deleteGoods = function(ids){
        return $http.get('http://localhost:8081/tbGoods/deleteGoods?ids='+ids);
    }

    this.findPageAndName = function(page,rows,name){
        return $http.get('http://localhost:8081/tbGoods/findPageAndName?name='+name+'&page='+page+'&rows='+rows);
    }
})