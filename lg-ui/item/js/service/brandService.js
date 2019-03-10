//服务层
app.service('brandService',function($http){
	    	
	//读取列表数据绑定到表单中
	this.findAll=function(){
		return $http.get('http://localhost:8081/tbBrand/list');
	}
	//分页

	this.findPage=function(page,rows){
		return $http.get('http://localhost:8081/tbBrand/listPage?page='+page+'&rows='+rows);
	}
	//查询实体
	this.findOne=function(id){
		return $http.get('http://localhost:8081/tbBrand/findOne/'+id);
	}
	//增加 
	this.add=function(entity){
		return  $http.post('http://localhost:8081/tbBrand/add',entity );
	}
	//修改 
	this.update=function(entity){
		return  $http.post('http://localhost:8081/tbBrand/update',entity );
	}
	//删除
	this.dele=function(ids){
		return $http.delete('http://localhost:8081/tbBrand/delete/'+ids);
	}
	//搜索
	this.search=function(page,rows,searchEntity){
		return $http.post('http://localhost:8081/tbBrand/search?page='+page+"&rows="+rows,searchEntity);
	}    
	//下拉列表数据
	this.selectOptionList=function(){
		return $http.get('../brand/selectOptionList.do');
	}



});
