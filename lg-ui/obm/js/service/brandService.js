// 定义服务层:
app.service("brandService",function($http){
	this.findAll = function(){
		return $http.get("../brand/findAll.do");
	}
	
	this.findByPage = function(page,rows){
		return $http.get("../brand/findByPage.do?page="+page+"&rows="+rows);
	}
	
	this.save = function(entity){
		return $http.post("../brand/save.do",entity);
	}
	
	this.update=function(entity){
		return $http.post("../brand/update.do",entity);
	}
	
	this.findById=function(id){
		return $http.get("../brand/findById.do?id="+id);
	}
	
	this.dele = function(ids){
		return $http.get("../brand/delete.do?ids="+ids);
	}
	
	this.search = function(page,rows,searchEntity){
		return $http.post("../brand/search.do?page="+page+"&rows="+rows,searchEntity);
	}

	//品牌的下拉列表
	this.selectBrandOptionList = function(){
		return $http.get("http://localhost:8081/tbBrand/selectOptionList");
	}
});