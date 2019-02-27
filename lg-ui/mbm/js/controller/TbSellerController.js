//控制层
app.controller('TbSellerController', function ($scope, $controller, TbSellerService) {

    $controller('baseController', {$scope: $scope});//继承

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

    $scope.searchEntity = {};//定义搜索对象


});