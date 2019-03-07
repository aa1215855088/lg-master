//服务层
app.service('TbSellerService', function ($http) {

    /* this.getName = function () {
         return $http.get("http://localhost:8084/tbSeller/sellerInfo")
     }*/


    //增加
    this.add = function (entity) {
        return $http.post('http://localhost:8084/tbSellers/register', entity);
    }

    //查询实体
    this.findOne = function () {
        return $http.get('http://localhost:8084/tbSellers/findSellerById');
    }


    //修改
    this.update=function(entity){
        return $http.post("http://localhost:8084/tbSellers/updateSellerInfo",entity);
    }

    //根据id修改密码
    this.updatePassword=function(entity){
        return $http.post("http://localhost:8084/tbSellers/updatePasswordById",entity);
    }
});