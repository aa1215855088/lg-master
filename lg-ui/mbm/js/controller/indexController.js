//控制层
app.controller('indexController', function ($scope, $controller, indexService) {

    $controller('baseController', {$scope: $scope});

    $scope.getName = function () {
        indexService.getName().success(function (response) {
            var responseStr = response.toString();
            if (responseStr.indexOf("请输入用户名")!=-1) {
                window.location = "http://localhost/mbm/shoplogin.html"
            }
            $scope.name = response.result;
        })
    }

});