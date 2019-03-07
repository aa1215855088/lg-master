//控制层
app.controller('brandController', function ($scope, $controller, brandService) {

    $controller('baseController', {$scope: $scope});//继承

    //读取列表数据绑定到表单中  
    $scope.findAll = function () {
        brandService.findAll().success(
            function (response) {
                $scope.list = response.result;
            }
        );
    }

    //分页
    $scope.findPage = function (page, rows) {
        brandService.findPage(page, rows).success(
            function (response) {
                $scope.list = response.result.rows;
                $scope.paginationConf.totalItems = response.result.total;//更新总记录数
            }
        );
    }

    //查询实体
    $scope.findOne = function (id) {
        brandService.findOne(id).success(
            function (response) {
                $scope.entity = response.result;
            }
        );
    }

    //保存
    $scope.save = function () {
        var serviceObject;//服务层对象
        if ($scope.entity.id != null) {//如果有ID
            serviceObject = brandService.update($scope.entity); //修改
        } else {
            serviceObject = brandService.add($scope.entity);//增加
        }
        serviceObject.success(
            function (response) {
                if (response.code == "200") {
                    layer.msg(response.message);
                    $scope.reloadList();//刷新列表
                    $scope.selectIds = [];
                } else {
                    layer.msg(response.message);
                }
            }
        );
    }


    //批量删除
    $scope.dele = function () {
        //获取选中的复选框
        if ($scope.selectIds.length == 0) {
            layer.msg("请选中一行进行操作");
            return;
        }
        layer.confirm("你确定要删除以下数据吗？",
            {btn: ['确定', '取消']},
            function () {
                //获取选中的复选框
                brandService.dele($scope.selectIds).success(
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


    //搜索
    $scope.search = function (page, rows) {
        brandService.search(page, rows, $scope.searchEntity).success(
            function (response) {
                $scope.list = response.result.rows;
                $scope.paginationConf.totalItems = response.result.total;//更新总记录数
            }
        );
    }

});	
