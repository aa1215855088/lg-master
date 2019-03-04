app.service('goodsService2',function($http){

    //读取列表数据绑定到表单中
    this.findAll=function(){
        return $http.get('http://localhost:8084/tbGoods/findAll');
    }
    //分页

    this.findPage=function(page,rows){
        return $http.get('http://localhost:8084/tbGoods/findPage?page='+page+"&rows="+rows);
    }
    //查询实体
    this.findOne=function(id){
        return $http.get('http://localhost:8084/tbGoods/findOne/'+id);
    }
    //增加
    this.add=function(entity){
        return  $http.post('http://localhost:8084/tbGoods/add',entity );
    }
    //修改
    this.update=function(entity){
        return  $http.post('http://localhost:8084/tbGoods/update',entity );
    }
    //删除
    this.dele=function(ids){
        return $http.get('http://localhost:8084/tbGoods/delete/'+ids);
    }
    //搜索
    this.search=function(page,rows,searchEntity){
        return $http.post('http://localhost:8084/tbGoods/search?page='+page+"&rows="+rows,searchEntity);
    }
    //屏蔽
    this.shield=function (ids) {
        return  $http.post('http://localhost:8084/tbGoods/shield/'+ids);
    }

    this.submitAudit=function (ids) {
        return  $http.post('http://localhost:8084/tbGoods/submitAudit/'+ids);
    }
});