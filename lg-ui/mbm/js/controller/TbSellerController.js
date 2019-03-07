//控制层
app.controller('TbSellerController', function ($scope, $controller, TbSellerService) {

    $controller('baseController', {$scope: $scope});


    /*$scope.getName = function () {
        TbSellerService.getName().success(function (response) {
            $scope.name = response.name;
        })
    }*/


    //新增
    $scope.add = function () {
        TbSellerService.add($scope.entity).success(
            function (response) {
                if (response.code == "200") {
                    //如果注册成功，跳转到登录页
                    layer.msg("信息提交成功!审核结果将在三个工作日发送信息到您的手机上!", {
                        icon: 1,
                        time: 5000
                    })
                } else {
                    layer.msg(response.message)
                }
            }
        );
    }

    //查询实体
    $scope.findOne = function () {
        TbSellerService.findOne().success(function (response) {
            $scope.entity = response.result;
        });
    }


    //修改
    $scope.update = function () {
        TbSellerService.update($scope.entity).success(
            function (response) {
                if (response.code == "200") {
                    // 修改成功
                    layer.msg(response.message, {
                        icon: 1,
                        time: 2000
                    }, function () {
                        window.parent.location.href = "http://localhost/mbm/admin/index.html";
                    });
                } else {
                    //修改失败
                    layer.msg(response.message);
                }
            }
        );
    }


    //根据id修改密码
    $scope.updatePassword = function () {
        if ($("#oldPassword").val() == null || $("#oldPassword").val() == "") {
            return layer.msg("请输入原密码");
        }
        if ($("#newPassword").val() == null || $("#newPassword").val() == "") {
            return layer.msg("请输入新密码");
        }
        TbSellerService.updatePassword($scope.entity).success(
            function (response) {
                if (response.code == "200") {
                    // 修改成功
                    layer.msg("操作成功", {
                        icon: 1,
                        time: 2000
                    }, function () {
                        window.parent.location = "http://localhost:8084/logout";
                    });
                } else {
                    //修改失败
                    layer.msg(response.message);
                }
            }
        );
    }

    $scope.searchEntity = {};//定义搜索对象

});