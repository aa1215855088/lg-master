app.controller('tbItemCat',function ($scope,$controller,tbItemCatService) {
    $controller('baseController',{$scope:$scope})

    $scope.searchEntity={};

    $scope.findAll=function () {
        tbItemCatService.findAll().success(
            function (response) {
                $scope.list=response.result;
            }
            )
    }

    $scope.findById=function (id) {
        tbItemCatService.findById(id).success(
            function (response) {
                $scope.entity=response.result;
            }
        )
    }

    $scope.findPage=function (page,rows) {
        tbItemCatService.findPage(page,rows).success(
            function (response) {
                $scope.list=response.result.rows;
                $scope.paginationConf.totalItems=response.result.total;
            }
        )
    }



    //保存
    $scope.save=function (entity) {
        /*tbItemCatService.add($scope.entity).success(
            function (response) {
            alter(response.message)
        }
        )*/
        var serviceObject;
        if ($scope.entity.id!=null) {
            serviceObject=tbItemCatService.update($scope.entity)
        }else {
            serviceObject=tbItemCatService.add($scope.entity);
        }
        serviceObject.success(
            function (response) {
            if (response.code="200") {
                layer.msg(response.message);
                //alert(response.message)
                layer.msg("保存成功");
                $scope.reloadList();
            }else {
                alert(response.message)
            }
        })

    }



    $scope.delete=function () {
        if($scope.selectIds.length==0){
            layer.msg("请选中一行进行操作",{
                icon:2,
                time:2000
            })
            return
        }
        tbItemCatService.findSonId($scope.selectIds).success(
            function (response) {
                //alert(response.result)
                if (response.result!=""){
                    layer.msg("该商品下面还有子类不可删除,请选择商品重新删除",{
                        icon:2,
                        time:2000
                    });

                }else {
                    layer.confirm('确定是否删除以下数据？', {
                        btn: ['确定','取消'] //按钮
                    }, function(){
                        tbItemCatService.del($scope.selectIds).success(
                            function (response) {
                                if (response.code=="200") {
                                    layer.msg(response.message);
                                    $scope.reloadList();
                                    $scope.selectIds = [];
                                }else {
                                    layer.msg(response.message);
                                }
                            })
                    }, function(){

                    });
                }

            }
        )



}

    //根据ID找父类
    $scope.findByParentId=function (parentId) {
        tbItemCatService.findParentId(parentId).success(
            function (response) {
                $scope.list=response.result;
            }
        )
    }

    //下级列表
    $scope.grade = 1;

    $scope.setGrade = function(value){
        $scope.grade = value;
    }

    $scope.selectList = function(p_entity){

        if($scope.grade == 1){
            $scope.entity_1 = null;
            $scope.entity_2 = null;
        }
        if($scope.grade == 2){
            $scope.entity_1 = p_entity;
            $scope.entity_2 = null;
        }
        if($scope.grade == 3){
            $scope.entity_2 = p_entity;
        }

        $scope.findByParentId(p_entity.id);
    }

    $scope.xinjian=function () {
        if($scope.entity_1.name!=null){
            $("#input1").val($scope.entity_1.id);
        }else if($scope.entity_1.name!=null&&$scope.entity_2.name!=null){
            $("#input1").val($scope.entity_2.id);
        }

    }

})