//控制层
app.controller('contentController', function ($scope, $controller, contentService, uploadService) {

    $controller('baseController', {$scope: $scope});//继承

    //读取列表数据绑定到表单中  
    $scope.findAll = function () {
        contentService.findAll().success(
            function (response) {
                $scope.list = response.result;
            }
        );
    }

    /*    //分页
        $scope.findPage = function (page, rows) {
            contentService.findPage(page, rows).success(
                function (response) {
                    $scope.list = response.rows;
                    $scope.paginationConf.totalItems = response.total;//更新总记录数
                }
            );
        }*/

    //查询实体
    $scope.findOne = function (id) {
        contentService.findOne(id).success(
            function (response) {
                $scope.content = response.result;
                if ($scope.content.status == "1") {
                    $("#isOK").prop("checked", true);
                } else {
                    $("#isOK").prop("checked", false);
                }
            }
        );
    }

    //保存
    $scope.save = function () {
        var serviceObject;//服务层对象
        if ($scope.content.id != null) {//如果有ID
            serviceObject = contentService.update($scope.content); //修改
        } else {
            $scope.content.pic = $scope.content.url;
            $scope.content.url = "www.baidu.com";
            
            serviceObject = contentService.add($scope.content);//增加
        }
        serviceObject.success(
            function (response) {
                if (response.code == 200) {
                    layer.msg("操作成功");
                    // $scope.reloadList();//重新加载
                    window.location.reload();
                } else {
                    layer.msg(response.message);
                }
            }
        );
    }


    //批量删除
    $scope.dele = function () {
        if ($scope.selectIds.length == 0) {
            layer.msg("请选中一行进行操作");
            return;
        }
        layer.confirm("你确定要删除以下数据吗？",
            {btn: ['确定', '取消']},
            function () {
                //获取选中的复选框
                contentService.dele($scope.selectIds).success(
                    function (response) {
                        if (response.code == "200") {
                            layer.msg(response.message);
                            window.location.reload();//刷新列表
                            $scope.selectIds = [];
                        } else {
                            layer.msg(response.message);
                        }
                    })
            }
        );
    }

    $scope.searchEntity = {};//定义搜索对象

    //搜索
    $scope.search = function (page, rows) {
        contentService.search(page, rows, $scope.searchEntity).success(
            function (response) {
                $scope.list = response.rows;
                $scope.paginationConf.totalItems = response.total;//更新总记录数
            }
        );
    }

    $scope.findContentCategoryList = function () {
        contentService.findContentCategoryList().success(function (response) {
            $scope.contentCategoryList = response.result;
        });
    }

    //上传图片
    $scope.uploadFile = function () {
        // 调用uploadService的方法完成文件的上传
        uploadService.uploadFile().success(function (response) {
            $scope.content.url = response.result;
        });
    }

    $scope.all = function (checkAll) {
        for (var i = 0; i < $scope.list.length; i++) {
            if (i == true) {
                $scope.list[i].state = true;
            } else {
                $scope.list[i].state = false;
            }
        }
    }

});	
