 //品牌控制层 
app.controller('baseController' ,function($scope){	
	
    //重新加载列表 数据
    $scope.reloadList=function(){
        //切换页码
    	$scope.search($scope.paginationConf.currentPage, $scope.paginationConf.itemsPerPage);
    }

	//分页控件配置 
	$scope.paginationConf = {
         currentPage: 1,
         totalItems: 5,
         itemsPerPage: 5,
         perPageOptions: [5,10, 20, 30, 40, 50],
         onChange: function(){
        	 $scope.reloadList();//重新加载
     	 }
	}; 
	
	$scope.selectIds=[];//选中的ID集合 

	//更新复选
	$scope.updateSelection = function($event, id) {		
		if($event.target.checked){//如果是被选中,则增加到数组
			$scope.selectIds.push( id);			
		}else{
			var idx = $scope.selectIds.indexOf(id);
            $scope.selectIds.splice(idx, 1);//删除 
		}
	}

    //全选操作
    $scope.selectAll = function ($event) {
        var checkbox = $event.target;
        var action = (checkbox.checked ? 'add' : 'remove');
        // 就是监控是否全部需要进行选中或者不选中
        for (var i = 0; i < $scope.content.length; i++) {
            var contact = $scope.content[i];
            updateSelected(action, contact.id);
        }
    };
    $scope.isSelected = function (id) {
        return $scope.selected.indexOf(id) >= 0;
    };
    $scope.isSelectedAll = function () {
        // 判断当前内容是否全部被选中，
        return $scope.selected.length === $scope.content.length;
    };

	// 定义方法：获取JSON字符串中的某个key对应值的集合
	$scope.jsonToString = function(jsonStr,key){
		// 将字符串转成JSOn:
		var jsonObj = JSON.parse(jsonStr);

		var value = "";
		for(var i=0;i<jsonObj.length;i++){

			if(i>0){
				value += ",";
			}

			value += jsonObj[i][key];
		}
		return value;
	}
});	