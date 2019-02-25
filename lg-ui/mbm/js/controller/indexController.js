//控制层
app.controller('indexController', function ($scope, $controller, indexService) {

    $scope.getName = function () {
        indexService.getName().success(function (response) {
            $scope.name = response.name;
        })
    }
});