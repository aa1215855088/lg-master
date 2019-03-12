//服务层
app.service('contentCategoryService',function($http){
	    	
	//读取列表数据绑定到表单中
	this.findAll=function(){
		return $http.get('http://localhost:8085/contentCategory/findAll');
	}
	//分页 
	this.findPage=function(page,rows){
		return $http.get('http://localhost:8085/contentCategory/findPage?page='+page+'&rows='+rows);
	}
	//查询实体
	this.findById=function(id){
		return $http.get('http://localhost:8085/contentCategory/findById/'+id);
	}
	//增加 
	this.add=function(entity){
		return  $http.post('http://localhost:8085/contentCategory/save',entity );
	}
	//修改 
	this.update=function(entity){
		return  $http.put('http://localhost:8085/contentCategory/update',entity );
	}
	//删除
	this.delete=function(ids){
		return $http.delete('http://localhost:8085/contentCategory/delete/'+ids);
	}
	//搜索
	this.search=function(page,rows,searchEntity){
		return $http.post('http://localhost:8085/contentCategory/search?page='+page+"&rows="+rows,searchEntity);
	}
	// 查询所有广告分类
	this.findContentCategoryList = function(){
		return $http.get('http://localhost:8085/tbContent/findAllCategory')
	}
	//广告图片
	this.findByCategoryId=function (categoryId) {
		return $http.get('http://localhost:8085/tbContent/findByCategoryId?categoryId='+categoryId);

	}
	
});
