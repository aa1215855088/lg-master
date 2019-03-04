//服务层
app.service('contentService',function($http){
	    	
	//读取列表数据绑定到表单中
	this.findAll=function(){
		return $http.get('http://localhost:8085/tbContent');
	}
	//查询实体
	this.findOne=function(id){
		return $http.get('http://localhost:8085/tbContent/findById/'+id);
	}
	//增加 
	this.add=function(content){
		return  $http.post('http://localhost:8085/tbContent/addContent/',content );
	}
	//修改 
	this.update=function(content){
		return  $http.put('http://localhost:8085/tbContent/updateById/',content );
	}
	//删除
	this.dele=function(ids){
		return $http.delete('http://localhost:8085/tbContent/delete/'+ids);
	}
    // 查询所有广告分类
    this.findContentCategoryList = function(){
        return $http.get('http://localhost:8085/tbContent/findAllCategory')
    }
});
