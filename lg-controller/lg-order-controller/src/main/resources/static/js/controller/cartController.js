app.controller('cartController', function ($scope, cartService, $http) {


    $scope.username = "";
    $scope.isLogin = function () {
        $http.get("http://localhost:9090/me").success(function (res) {
            $scope.user = res;
            if (res.code == "200") {
                $scope.username = res.result;
                cartService.findCartList(res.result).success(
                    function (response) {
                        $scope.cartList = response;
                    }
                )
            } else {
                cartService.findCartList("").success(
                    function (response) {
                        $scope.cartList = response;
                    }
                )
            }

        })
    };
    $scope.createOrder = function () {
        if ($scope.selectIds.length == 0) {
            layer.msg("请选中一行商品进行操作?", {
                icon: 2,
                time: 2000
            });
            return;
        }
        window.location.href = "http://localhost:8083/order/createOrder?ids=" + $scope.selectIds;
    };
    //数量添加
    $scope.addGoodsToCartList = function (itemId, num) {
        cartService.addGoodsToCartList(itemId, num, $scope.username).success(
            function (response) {
                if (response.code = "200") {
                    window.location.href = "http://localhost:8083/cart"
                } else {
                    layer.msg(response.message)
                }

            }
        )
    };

    $scope.findCartList = function () {
        cartService.findCartList($scope.username).success(
            function (response) {
                $scope.cartList = response;
                //$scope.totalValue = cartService.sum($scope.cartList);
            }
        )
    }
    //查询库存
    $scope.findKucun = function (itemId, num) {
        //$scope.orderNum = num;
        cartService.findKucun(itemId).success(
            function (response) {
                if (num > 200) {
                    layer.msg("数量不能大于200,请重新选择数量");
                    //$event.target.val = $scope.orderNum;
                    return;
                }
                if (num == 0 || num == "") {
                    layer.msg("数量不能为空,请重新选择数量");
                    return;
                }
                if (num > response.num) {
                    layer.msg("库存不足,请重新选择数量");
                    return;
                }
                cartService.updateNum(itemId, num).success(
                    function (response) {
                        if (response.code = "200") {
                            window.location.href = "http://localhost:8083/cart"
                        } else {
                            layer.msg(response.message)
                        }

                    })

            }
        )
    }

    $scope.totalNum = 0;
    $scope.totalMoney = 0;

    $scope.selectIds = [];//选中的ID集合

    //更新复选
    $scope.updateSelection = function ($event, id) {
        if ($event.target.checked) {//如果是被选中,则增加到数组
            $scope.selectIds.push(id);
        } else {
            var idx = $scope.selectIds.indexOf(id);
            $scope.selectIds.splice(idx, 1);//删除
        }
    };

    //全部更新复选
    $scope.TbupdateSelection = function ($event) {
        cartService.findCartList($scope.username).success(
            function (response) {
                $scope.carts = response;
                for (var i = 0; i < $scope.carts.length; i++) {
                    var cat = $scope.carts[i];
                    for (var j = 0; j < cat.orderItemList.length; j++) {
                        var orderItem = cat.orderItemList[i];
                        if ($event.target.checked) {
                            $scope.selectIds.push(orderItem.itemId);
                        } else {
                            var idx = $scope.selectIds.indexOf(orderItem.itemId);
                            $scope.selectIds.splice(idx, 1);//删除
                        }
                    }
                }
            }
        )
    };


    //计算总价
    $scope.updateMoney = function ($event, num, price) {
        if ($event.target.checked) {
            $scope.totalNum += num;
            $scope.totalMoney += price;
        } else {
            $scope.totalNum -= num;
            $scope.totalMoney -= price;
        }
    }

    //全选全不选
    $scope.selectAll = false;

    $scope.all = function (m) {
        if (m === true) {
            $scope.selectAll = true;
        } else {
            $scope.selectAll = false;
        }


    };

    //删除商品
    $scope.deleteCartToRedis = function () {
        if ($scope.selectIds.length == 0) {
            layer.msg("请选中一行商品进行操作?", {
                icon: 2,
                time: 2000
            })
            return;
        }
        layer.confirm('确实将该商品移出购物车？', {
            btn: ['确定', '取消'] //按钮
        }, function () {
            cartService.deleteCartToRedis($scope.selectIds, $scope.username).success(
                function (response) {
                    if (response.code == "200") {
                        layer.msg(response.message);
                        window.location.href = "http://localhost:8083/cart";
                    } else {
                        layer.msg(response.message);
                    }
                })
        }, function () {

        });
    };
    //删除单件商品
    $scope.delCart = function (id) {
        layer.confirm('确实将该商品移出购物车？', {
            btn: ['确定', '取消'] //按钮
        }, function () {
            cartService.deleteCartToRedis(id, $scope.username).success(
                function (response) {
                    if (response.code == "200") {
                        layer.msg(response.message);
                        window.location.href = "http://localhost:8083/cart"
                        $scope.selectIds = [];
                    } else {
                        layer.msg(response.message);
                    }
                })
        }, function () {

        });
    };
    $scope.findItems = function () {
        cartService.findItemList().success(
            function (response) {
                $scope.ItemList = response.result;
            }
        )
    }

    //查询收藏商品
    $scope.findmoveCart = function () {
        cartService.findmoveCart($scope.username).success(
            function (response) {
                $scope.mCart = response;
            }
        )
    }

    //移除商品
    $scope.moveCart = function () {
        if ($scope.selectIds.length == 0) {
            layer.msg("请选中一件商品进行操作", {
                icon: 2,
                time: 2000
            });
            return;
        }
        layer.confirm('确定是否收藏以下商品？', {
            btn: ['确定', '取消'] //按钮
        }, function () {
            cartService.moveCart($scope.selectIds, $scope.username).success(
                function (response) {
                    if (response.code == "200") {
                        layer.msg(response.message);
                        window.location.href = "http://localhost:8083/cart"
                        $scope.selectIds = [];
                    } else {
                        layer.msg(response.message);
                    }
                })
        }, function () {

        });
    }


});