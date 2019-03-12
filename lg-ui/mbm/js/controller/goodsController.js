app.controller('goodsController', function ($scope, $controller, goodsService, itemCatService, typeTemplateService, uploadService) {

    $controller('baseController', {$scope: $scope});//继承

    //读取列表数据绑定到表单中
    $scope.findAll = function () {
        goodsService.findAll().success(
            function (response) {
                $scope.list = response;
            }
        );
    }

    //分页
    $scope.findPage = function (page, rows) {
        goodsService.findPage(page, rows).success(
            function (response) {
                $scope.list = response.rows;
                $scope.paginationConf.totalItems = response.total;//更新总记录数
            }
        );
    }

    //增加商品
    $scope.add = function () {
        var itemImagesArray = $scope.entity.goodsDesc.itemImages
        var customAttributeItemsArray = $scope.entity.goodsDesc.customAttributeItems
        var specificationItemsArray = $scope.entity.goodsDesc.specificationItems;
        var ItemJSON = $scope.entity.itemList
        $scope.entity.itemList = JSON.stringify(ItemJSON);
        $scope.entity.goodsDesc.specificationItems = JSON.stringify(specificationItemsArray);
        $scope.entity.goodsDesc.itemImages = JSON.stringify(itemImagesArray);
        $scope.entity.goodsDesc.customAttributeItems = JSON.stringify(customAttributeItemsArray);
        $scope.entity.goodsDesc.specificationItems = JSON.stringify(specificationItemsArray);
        $scope.entity.goodsDesc.itemImages = JSON.stringify(itemImagesArray);
        $scope.entity.goodsDesc.customAttributeItems = JSON.stringify(customAttributeItemsArray);
        $scope.entity.goodsDesc.introduction = editor.html();//富文本编辑器内容
        goodsService.add($scope.entity).success(
            function (response) {
                if (response.code == 200) {
                    layer.msg("添加成功")
                    $scope.entity = {}
                    editor.html("")
                } else {
                    layer.msg(response.message)
                }

            }
        );
    }

    //查询实体
    $scope.findOne = function (id) {
        goodsService.findOne(id).success(
            function (response) {
                $scope.entity = response;
            }
        );
    }

    //保存
    /*$scope.save = function () {
        var itemImagesArray = $scope.entity.goodsDesc.itemImages
        $scope.entity.goodsDesc.itemImages = JSON.stringify(itemImagesArray);
        var  customAttributeItemsArray = $scope.entity.goodsDesc.customAttributeItems
        var specificationItemsArray=$scope.entity.goodsDesc.specificationItems;
        var ItemJSON= $scope.entity.itemList;

        $scope.entity.itemList=JSON.stringify(ItemJSON);
        $scope.entity.goodsDesc.specificationItems=JSON.stringify(specificationItemsArray);
        $scope.entity.goodsDesc.customAttributeItems=JSON.stringify(customAttributeItemsArray);
        $scope.entity.goodsDesc.specificationItems=JSON.stringify(specificationItemsArray);
        $scope.entity.goodsDesc.itemImages = JSON.stringify(itemImagesArray);
        $scope.entity.goodsDesc.customAttributeItems=JSON.stringify(customAttributeItemsArray);
     /!*   $scope.entity.itemList=$scope.entity.itemList.replace("spec","");*!/
        $scope.entity.goodsDesc.introduction=editor.html();
        layer.msg($scope.entity.itemList);
        var serviceObject;//服务层对象
        if ($scope.entity.goods.id!= null) {//如果有ID
            serviceObject = goodsService.update($scope.entity); //修改
        } else {
            serviceObject = goodsService.add($scope.entity);//增加
        }
        serviceObject.success(
            function (response) {
                if (response.code==200) {
                    //重新查询
                    layer.msg("添加成功")
                    $scope.entity = {}
                    editor.html("")
                } else {
                    layer.msg(response.message);
                }
            }
        );
    }*/


    //批量删除
    $scope.dele = function () {
        //获取选中的复选框
        goodsService.dele($scope.selectIds).success(
            function (response) {
                if (response.success) {
                    $scope.reloadList();//刷新列表
                    $scope.selectIds = [];
                }
            }
        );
    }

    $scope.searchEntity = {};//定义搜索对象

    //搜索
    $scope.search = function (page, rows) {
        goodsService.search(page, rows, $scope.searchEntity).success(
            function (response) {
                $scope.list = response.rows;
                $scope.paginationConf.totalItems = response.total;//更新总记录数
            }
        );
    }
    //上传图片

    $scope.uploadFile = function () {
        // 调用uploadService的方法完成文件的上传
        uploadService.uploadFile().success(function (response) {
            $scope.imageUrl.url = response.result;
        });
    }


    //将当前上传的图片实体存入图片列表
    $scope.entity = {goods: {}, goodsDesc: {itemImages: [], specificationItems: []}};
    $scope.add_image_entity = function () {
        $scope.entity.goodsDesc.itemImages.push($scope.imageUrl);
    }

    //移除图片
    $scope.remove_image_entity = function (index) {
        $scope.entity.goodsDesc.itemImages.splice(index, 1);
    }


    //查询一级商品分类
    $scope.selectItemCat1List = function () {
        itemCatService.findByParentId(0).success(function (response) {
            $scope.itemCat1List = response.result;
        });
    }
    //查询二级分类商品
    $scope.$watch('entity.goods.category1Id', function (newValue, oldValue) {
        itemCatService.findByParentId(newValue).success(function (response) {
            $scope.itemCat2List = response.result;
        });
    })
    //查询三级分类商品
    $scope.$watch("entity.goods.category2Id", function (newValue, oldValue) {
        itemCatService.findByParentId(newValue).success(function (response) {
            if (newValue == undefined) {
                newValue == 0;
            }
            $scope.itemCat3List = response.result;
        });
    });

    //读取模板ID
    $scope.$watch("entity.goods.category3Id", function (newValue, oldValue) {
        itemCatService.findOne(newValue).success(function (response) {
            if (newValue == undefined) {
                newValue == 0;
            }

            $scope.entity.goods.typeTemplateId = response.result.typeId;
        });
    });

    //读取模板ID后读取品牌列表
    $scope.$watch("entity.goods.typeTemplateId", function (newValue, oldValue) {
        typeTemplateService.findOne(newValue).success(function (response) {
                if (newValue == undefined) {
                    newValue == 0;
                }
                $scope.typeTemplate = response.result;
                $scope.typeTemplate.brandIds = JSON.parse($scope.typeTemplate.brandIds)
                $scope.entity.goodsDesc.customAttributeItems = JSON.parse($scope.typeTemplate.customAttributeItems)
            }
        );
        //读取规格
        typeTemplateService.findBySpecList(newValue).success(function (response) {
            if (newValue == undefined) {
                newValue == 0;
            }
            $scope.specList = response.result;
        });


        $scope.updateSpecAttribute = function ($event, name, value) {


            var object = $scope.searchObjectByKey($scope.entity.goodsDesc.specificationItems, "attributeName", name);


            if (object !== null) {
                if ($event.target.checked) {
                    object.attributeValue.push(value);
                } else {//取消勾选
                    object.attributeValue.splice(object.attributeValue.indexOf(value), 1)//移除选项
                    //如果选项都取消，将此条记录移除
                }
                if (object.attributeValue.length == 0) {
                    var idx = $scope.entity.goodsDesc.specificationItems.indexOf(object);
                    $scope.entity.goodsDesc.specificationItems.splice(idx, 1);

                }

            } else {
                $scope.entity.goodsDesc.specificationItems.push({"attributeName": name, "attributeValue": [value]});

            }

        }
        //创建SKU列表
        $scope.createItemList = function () {
            $scope.entity.itemList = [{spec: {}, price: 0, num: 9999, status: '0', isDefault: '0'}];//列表初始化
            var items = $scope.entity.goodsDesc.specificationItems;
            for (var i = 0; i < items.length; i++) {

                $scope.entity.itemList = addColumn($scope.entity.itemList, items[i].attributeName, items[i].attributeValue);
            }

        }

        addColumn = function (list, columnName, columnValues) {

            var newList = [];
            for (var i = 0; i < list.length; i++) {
                var oldRow = list[i];

                for (var j = 0; j < columnValues.length; j++) {
                    var newRwo = JSON.parse(JSON.stringify(oldRow))//深克隆
                    newRwo.spec[columnName] = columnValues[j];
                    newList.push(newRwo);

                }
            }

            return newList;
        }

    });
});