//控制层
app.controller('loginController', function ($scope, $controller, loginService) {
    $scope.username = "";
    $scope.password = "";
    $scope.login = function (username, password) {
        if (username.trim() == "" || username == null) {
            layer.msg("请输入用户名")
        }
        if (password.trim() == "" || password == null) {
            layer.msg("请输入密码")
        }
        // loginService.login(username, password).success(function (response) {
        //     if (response.code = "500") {
        //         layer.msg("账号和密码有误!")
        //     }
        // });
    }
});