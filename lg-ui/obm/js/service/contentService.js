//服务层
app.service('contentService',function($http){
	    	
	//读取列表数据绑定到表单中
	this.findAll=function(){
		return $http.get('http://localhost:8085/tbContent');
	}
	//分页 
	this.findPage=function(page,rows){
		return $http.get('http://localhost:8085/tbContent/findPage/page='+page+'&rows/'+rows);
	}
	//查询实体
	this.findOne=function(id){
		return $http.get('http://localhost:8085/tbContent/findById/'+id);
	}
	//增加 
	this.add=function(entity){
		return  $http.post('../content/add.do',entity );
	}
	//修改 
	this.update=function(entity){
		return  $http.post('../content/update.do',entity );
	}
	//删除
	this.dele=function(ids){
		return $http.delete('http://localhost:8085/tbContent/delete/'+ids);
	}
	//搜索
	this.search=function(page,rows,searchEntity){
		return $http.post('../content/search.do?page='+page+"&rows="+rows, searchEntity);
	}

    // 查询所有广告分类
    this.findContentCategoryList = function(){
        return $http.get('http://localhost:8085/tbContent/findAllCategory')
    }
});
