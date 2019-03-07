// 定义控制器:
app.controller("typeTemplateController",function($scope,$controller,typeTemplateService,brandService,specificationService){

    // AngularJS中的继承:伪继承
    $controller('baseController',{$scope:$scope});



    //商品所有的商品规格选项列表
    $scope.findAll=function () {
        typeTemplateService.findAll().success(
            function (response) {
                $scope.list = response.result;
            }
        )
    }


    //分页
    $scope.findPage=function (page,rows) {
        typeTemplateService.findPage(page,rows).success(
            function (response) {
                $scope.list=response.result.rows;
                $scope.paginationConf.totalItems=response.result.total;
            }
        )
    }


    // 查询一个:
    $scope.findById = function(id){
        typeTemplateService.findById(id).success(
            function(response){
            $scope.entity = response.result;

            $scope.entity.brandIds = JSON.parse($scope.entity.brandIds);

            $scope.entity.specIds = JSON.parse($scope.entity.specIds);

            $scope.entity.customAttributeItems = JSON.parse($scope.entity.customAttributeItems);
        });
    }

    //保存
    $scope.save=function () {

        var serviceObject;
        $scope.entity.brandIds = angular.toJson($scope.entity.brandIds);
        $scope.entity.specIds = angular.toJson($scope.entity.specIds);
        $scope.entity.customAttributeItems =angular.toJson($scope.entity.customAttributeItems);


        if ($scope.entity.id!=null) {
            serviceObject=typeTemplateService.update($scope.entity)
        }else {
            serviceObject=typeTemplateService.save($scope.entity);
        }
        serviceObject.success(
            function (response) {
                if (response.code == "200") {
                    layer.msg("保存成功");
                    $scope.reloadList();
                }else {
                    alert(response.message)
                }
            })
    }



    $scope.delete = function(){
        typeTemplateService.delete($scope.selectIds).success(
            function(response){
            // 判断保存是否成功:
            if(response.code == "200"){
                // 删除成功
                layer.msg("删除成功");
                $scope.reloadList();
                $scope.selectIds = [];
            }else{
                // 删除失败
                alert(response.message);
            }
        });
    }


    $scope.searchEntity={};
    //搜索
    $scope.search=function(page,rows){
        typeTemplateService.search(page,rows,$("#name").val()).success(
            function(response){
                $scope.paginationConf.totalItems = response.result.total;
                $scope.list = response.result.rows;
            }
        );
    }

     //$scope.brandList={data:[{id:1,text:'联想'},{id:2,text:'华为'},{id:3,text:'小米'},{id:4,text:'安卓'}]};
     $scope.brandList={data:[]};
     // 查询关联的品牌信息:
    $scope.findBrandList = function(){
        brandService.selectBrandOptionList().success(function(response){
            $scope.brandList = {data:response};
        });
    }


    $scope.specList={data:[]}
    // 查询关联的规格信息:
    $scope.findSpecList = function(){
        specificationService.selectSpecificationOptionList().success(function(response){
            $scope.specList = {data:response};
        });
    }



    //给扩展属性添加行
    $scope.entity={customAttributeItems:[]};
    $scope.addTableRow = function(){
        $scope.entity.customAttributeItems.push({});
    }

    $scope.deleteTableRow = function(index){
        $scope.entity.customAttributeItems.splice(index,1);
    }

})