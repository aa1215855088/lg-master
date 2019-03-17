app.service('cartService', function ($http) {
    //购物车列表
    this.findCartList = function (username) {
        return $http.get('http://localhost:8083/cart/findCartList?username=' + username);
    }

    //添加数量
    this.addGoodsToCartList = function (itemId, num, username) {
        return $http.post('http://localhost:8083/cart/addGoodsToCartList?itemId=' + itemId + "&num=" + num + "&username=" + username);
    }

    //合计数
    this.sum = function (cartList) {
        var totalValue = {totalNum: 0, totalMoney: 0};
        for (var i = 0; i < cartList.length; i++) {
            var cart = cartList[i];
            for (var j = 0; j < cart.orderItemList.length; j++) {
                var orderItem = cart.orderItemList[j];
                totalValue.totalNum += orderItem.num;
                totalValue.totalMoney += orderItem.totalFee;
            }

        }
        return totalValue;
    }

    //查询库存
    this.findKucun = function (itemId) {
        return $http.get('http://localhost:8083/cart/findKucun?itemId=' + itemId);
    }

    //删除商品
    this.deleteCartToRedis = function (ids, username) {
        return $http.get('http://localhost:8083/cart/deleteCartToRedis?ids=' + ids + "&username=" + username);
    }

    //修改商品数量
    this.updateNum = function (id, num, username) {
        return $http.get('http://localhost:8083/cart/updateNum?id=' + id + "&num=" + num + "&username=" + username);
    }

    //滚动商品
    this.findItemList = function () {
        return $http.get('http://localhost:8083/cart/findItem');
    }

    //移动商品到关注
    this.moveCart = function (ids, username) {
        return $http.get('http://localhost:8083/cart/moveCart?ids=' + ids + "&username=" + username);
    };

    //查询关注商品
    this.findmoveCart = function (username) {
        return $http.get('http://localhost:8083/cart/findmoveCart?username=' + username);
    }

});