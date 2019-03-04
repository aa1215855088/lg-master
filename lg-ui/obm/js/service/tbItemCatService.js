app.service('tbItemCatService',function ($http) {
    
    this.findAll=function () {
        return $http.get('http://localhost:8081/tbItemCats/list');
    }

    this.findById=function (id) {
        return $http.get('http://localhost:8081/tbItemCats/findById/'+id);
    }

    this.findPage=function (page,rows) {
        return $http.get('http://localhost:8081/tbItemCats/findPage?page='+page+'&rows='+rows);
    }

    this.add=function (entity) {
        return $http.post('http://localhost:8081/tbItemCats/save',entity);
    }

    this.update=function (entity) {
        return $http.put('http://localhost:8081/tbItemCats/update',entity);
    }

    this.del=function (ids) {
        return $http.delete('http://localhost:8081/tbItemCats/delete/'+ids);

    }

    this.findParentId=function (parentId) {
        return $http.get('http://localhost:8081/tbItemCats/findByParentId?parentId='+parentId);

    }

    this.findSonId=function (id) {
        return $http.get('http://localhost:8081/tbItemCats/findBySonId/'+id);
    }

})