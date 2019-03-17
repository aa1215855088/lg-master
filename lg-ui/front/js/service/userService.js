//服务层
app.service('userService', function ($http) {
    this.sendCode = function (phone) {
        return $http.get('http://localhost:9909/sms/register/' + phone);
    }
    this.add = function (user) {
        return $http.post('http://localhost:9090/register',user);
    }
    this.checkCode = function (code, phone) {
        return $http.get('http://localhost:9909/sms/checkVerificationCode?code=' + code + "&phone=" + phone);
    }
});