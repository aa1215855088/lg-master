//控制层
app.controller('userController', function ($scope, userService) {
    //发送验证码
    $scope.sendCode = function () {
        if ($("#phone").val() == null || $("#phone").val() == "") {
            layer.msg("请填写手机号码");
            return;
        }
        if (!(/^1[34578]\d{9}$/.test($("#phone").val()))) {
            layer.msg("手机号码有误，请重填");
            return false;
        }
        userService.sendCode($scope.user.phone).success(
            function (response) {
                if (response.code != "200") {
                    layer.msg(response.message);
                } else {
                    layer.msg("验证码已经发送!请注意查收")
                }
            }
        );
    }
    $scope.user = "";
    //注册用户
    $scope.reg = function () {
        if (!document.getElementById("mm").checked) {
            return
        }
        if ($scope.user.username == undefined || $scope.user.username == "") {
            layer.msg("请输入账号!");
            return;
        }
        if ($scope.user.password == undefined || $scope.user.password == "") {
            layer.msg("请输入密码");
            return;
        }
        //比较两次输入的密码是否一致
        if ($scope.password != $scope.user.password) {
            layer.msg("两次输入密码不一致，请重新输入");
            $scope.user.password = "";
            $scope.password = "";
            return;
        }
        if ($("#phone").val() == null || $("#phone").val() == "") {
            layer.msg("请填写手机号码");
            return;
        }
        if (!(/^1[34578]\d{9}$/.test($("#phone").val()))) {
            layer.msg("手机号码有误，请重填");
            return false;
        }
        if ($scope.user.code == "" || $scope.user.code == null) {
            layer.msg("请输入验证码");
        }
        userService.checkCode($scope.user.code, $scope.user.phone).success(function (response) {
            if (response.code != "200") {
                layer.msg("验证码有误请重新输入!")
                return
            }
        });
        //新增
        userService.add($scope.user).success(
            function (response) {
                if (response.code == "200") {
                    layer.msg("添加用户成功")
                } else {
                    layer.msg(response.message);
                }
            }
        );
    }
});	
