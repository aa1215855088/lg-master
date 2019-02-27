//服务层
app.service('TbSellerService',function($http){

    //增加
    this.add=function(entity){
        return  $http.post('http://localhost:8084/tbSellers/SellerInsert',entity );
}
});