//定义控制器
app.controller("goodsController",function ($scope,$controller,itemCatService,tbGoodsService) {

    //angularjs中的继承：伪继承
    $controller('baseController',{$scope:$scope});

    //查询所有商品审核信息的方法
    $scope.findAll = function () {
        //向后台发送请求
        tbGoodsService.findAll().success(function (response) {
            $scope.list = response.result;

        })

    }

    $scope.findPage = function(page,rows){

        tbGoodsService.findPage(page,rows).success(function (response) {
            $scope.list = response.result.rows;
            $scope.paginationConf.totalItems = response.result.total;
        })
    }

    // 显示状态
    $scope.status = ["未审核","审核通过","审核未通过","关闭"];

    $scope.itemCatList = [];
    // 显示分类:
    $scope.findItemCatList = function(){

        itemCatService.findAll().success(function(response){

           $scope.list1 = response.result;
           for (var i = 0;i<$scope.list1.length;i++) {
               $scope.itemCatList[$scope.list1[i].id] = $scope.list1[i].name;
           }

        });
    }

    /*
    审核
     */
    $scope.updateStatus = function(status){

        if($scope.selectIds.length==0){
            layer.msg("请至少选择一条数据！",{
                icon:2,
                time:2000
            })
            return
        }

        for(var i=0;i<$scope.selectStatusIds.length;i++){

            if($scope.selectStatusIds[i]!=0){
                layer.msg("仅能操作未审核数据！",{
                    icon:2,
                    time:2000
                })

                $scope.selectStatusIds=[];
                $scope.selectIds = [];
                $scope.reloadList()
                return
            }
        }

        tbGoodsService.updateStatus($scope.selectIds,status).success(function(response){


                $scope.reloadList();//刷新列表
                $scope.selectIds = [];

                alert(response.message);

        });
    }
/*
刷新列表
 */
    $scope.reload = function(){
        $scope.reloadList();
        $scope.selectIds = [];
    }

    /*
    删除选中数据
     */
    $scope.deleteGoods = function(){

        if($scope.selectIds.length==0){
            layer.msg("请至少选择一条数据！",{
                icon:2,
                time:2000
            })
            return
        }
        tbGoodsService.deleteGoods($scope.selectIds).success(function(response){
            $scope.reloadList();
            $scope.selectIds = [];

            alert(response.message);
        })
    }

    /*
    条件查询+分页
     */

    $scope.findPageAndName =function(page,rows){

        tbGoodsService.findPageAndName(page,rows,$("#goodsName").val()).success(function (response) {
            $scope.list = response.result.rows;
            $scope.paginationConf.totalItems = response.result.total;
        })
    }
})