app.service('uploadService',function ($http) {
    //上传文件
    this.uploadFile=function () {
        var formdata=new FormData();
        formdata.append('file',file.files[0]);//file 文件上传的name
        return $http({
            url:'http://localhost:8084/upload/uploadImage',
            method:'post',
            date: formdata,
            headers:{'Content-Type':undefined},
            transformRequest:angular.identity
        })
    }
})