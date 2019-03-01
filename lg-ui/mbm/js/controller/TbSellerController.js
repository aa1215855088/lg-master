//控制层
app.controller('TbSellerController', function ($scope, $controller, TbSellerService) {

    $controller('baseController',{$scope:$scope});


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
                    location.href = "http://localhost/mbm/shoplogin.html";
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
    $scope.update = function(){
        TbSellerService.update($scope.entity).success(
            function (response) {
                if (response.code == "200") {
                    // 修改成功
                    layer.msg(response.message);
                } else {
                    //修改失败
                    layer.msg(response.message);
                }
            }
        );
    }


    //根据id修改密码
    $scope.updatePassword = function(){
        TbSellerService.updatePassword($scope.entity).success(
                  function (response){
                if (response.code == "200") {
                    // 修改成功
                    layer.msg("操作成功!");
                    $scope.name="";
                } else {
                    //修改失败
                    layer.msg("原密码错误!");
                }
            }
        );
    }

    $scope.searchEntity = {};//定义搜索对象

});