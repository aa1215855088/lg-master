//控制层
app.controller('contentCategoryController', function ($scope, $controller, contentCategoryService, $http) {

    $controller('baseController', {$scope: $scope});//继承

    //读取列表数据绑定到表单中  
    $scope.findAll = function () {
        contentCategoryService.findAll().success(
            function (response) {
                $scope.list = response.result;
            }
        );
    }

    //分页
    $scope.findPage = function (page, rows) {
        contentCategoryService.findPage(page, rows).success(
            function (response) {
                $scope.list = response.result.rows;
                $scope.paginationConf.totalItems = response.result.total;//更新总记录数
            }
        );
    }

    //查询实体
    $scope.findById = function (id) {
        contentCategoryService.findById(id).success(
            function (response) {
                $scope.entity = response.result;
            }
        );
    }

    //保存
    $scope.save = function () {
        var serviceObject;//服务层对象
        if ($scope.entity.id != null) {//如果有ID
            serviceObject = contentCategoryService.update($scope.entity); //修改
        } else {
            serviceObject = contentCategoryService.add($scope.entity);//增加
        }
        serviceObject.success(
            function (response) {
                if (response.code == "200") {
                    /*layer.msg(response, message);
                    layer.msg("添加成功");*/
                    //重新查询
                    $scope.reloadList();//重新加载
                    // window.location=location;
                } else {
                    alert(response.message);
                }
            }
        );
    }


    //批量删除
    $scope.delete = function () {
        if ($scope.selectIds.length == 0) {
            layer.msg("请选中一行进行操作");
            icon : 2;
            time:1000;
            return;
        }
        layer.confirm("你确定要删除以下数据吗？",
            {btn: ['确定', '取消']},
            function () {
                //获取选中的复选框
                contentCategoryService.delete($scope.selectIds).success(
                    function (response) {
                        if (response.code == "200") {
                            layer.msg(response.message);
                            $scope.reloadList();//刷新列表
                            $scope.selectIds = [];
                        } else {
                            layer.msg(response.message);
                        }
                    })
            }
        );
    }

    // 复选框全选
    $scope.selectAll = false;
    $scope.all = function (m) {
        for (var i = 0; i < $scope.list.length; i++) {
            if (m === true) {
                $scope.list[i].state = true;
            } else {
                $scope.list[i].state = false;
            }
        }
    };


    $scope.searchEntity = {};//定义搜索对象
    //搜索
    $scope.search = function (page, rows) {
        contentCategoryService.search(page, rows, $scope.searchEntity).success(
            function (response) {
                $scope.list = response.result.rows;
                $scope.paginationConf.totalItems = response.result.total;//更新总记录数
            }
        );
    }

    //显示广告图片
    $scope.contentList = [];//所有广告列表
    $scope.findByCategoryId = function (categoryId) {
        contentCategoryService.findByCategoryId(categoryId).success(
            function (response) {
                $scope.contentList[categoryId] = response.result;
            }
        );

    };



});	
