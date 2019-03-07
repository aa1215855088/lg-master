app.service("typeTemplateService", function ($http) {

    this.findAll = function () {
        return $http.get("http://localhost:8081/tbTypeTemplate/findAllList");
    }

    this.findPage = function (page, rows) {
        return $http.get("http://localhost:8081/tbTypeTemplate/findPage?page=" + page + "&rows=" + rows);
    }

    this.findById = function (id) {
        return $http.get('http://localhost:8081/tbTypeTemplate/findOne?id=' + id);
    }

    this.save = function (entity) {
        return $http.post("http://localhost:8081/tbTypeTemplate/Insert", entity);
    }

    this.update = function (entity) {
        return $http.post("http://localhost:8081/tbTypeTemplate/update", entity);
    }

    this.delete = function (ids) {
        return $http.post('http://localhost:8081/tbTypeTemplate/delete/' + ids);
    }

    this.search = function (page, rows, name) {
        return $http.get('http://localhost:8081/tbTypeTemplate/findByName?page=' + page + '&rows=' + rows + "&name=" + name);
    }
})