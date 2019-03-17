<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE">
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7"/>
    <title>我的购物车</title>

    <link rel="stylesheet" type="text/css" href="css/webbase.css"/>
    <link rel="stylesheet" type="text/css" href="css/pages-cart.css"/>
    <script src="plugins/jquery/jquery.min.js"></script>
    <script src="plugins/angularjs/angular.min.js"></script>
    <script src="js/base.js"></script>
    <script src="js/controller/cartController.js"></script>
    <script src="js/service/cartService.js"></script>
    <script type="text/javascript" src="js/layer/layer.js"></script>
</head>

<body ng-app="pinyougou" ng-controller="cartController" ng-init="isLogin()">
<!--head-->
<!--head-->

<div class="top">
    <div class="py-container">
        <div class="shortcut">
            <ul class="fl" ng-if="user.code!=200">
                <li class="f-item">乐购欢迎您！</li>
                <li class="f-item">请<a href="http://localhost:8080/cas/login?service=http://localhost:8083/cart"
                                       target="_blank">登录</a>
                    　<span><a href="http://localhost/front/register.html" target="_blank">免费注册</a></span></li>
            </ul>
            <ul class="fr" ng-if="user.code==200">
                <li class="f-item">我的订单</li>
                <li class="f-item space"></li>
                <li class="f-item"><a href="home.html" target="_blank">我的乐购</a></li>
                <li class="f-item space"></li>
                <li class="f-item">乐购会员</li>
                <li class="f-item space"></li>
                <li class="f-item">企业采购</li>
                <li class="f-item space"></li>
                <li class="f-item">关注乐购</li>
                <li class="f-item space"></li>
                <li class="f-item" id="service">
                    <span>客户服务</span>
                    <ul class="service">
                        <li><a href="cooperation.html" target="_blank">合作招商</a></li>
                        <li><a href="shoplogin.html" target="_blank">商家后台</a></li>
                        <li><a href="cooperation.html" target="_blank">合作招商</a></li>
                        <li><a href="#">商家后台</a></li>
                    </ul>
                </li>
                <li class="f-item space"></li>
                <li class="f-item">网站导航</li>
            </ul>
        </div>
    </div>
</div>
<div class="cart py-container">
    <!--logoArea-->
    <div class="logoArea">
        <div class="fl logo"><span class="title">购物车</span></div>
        <div class="fr search">
            <form class="sui-form form-inline">
                <div class="input-append">
                    <input id="autocomplete" type="text"
                           class="input-error input-xxlarge" placeholder="乐购自营"/>
                    <button class="sui-btn btn-xlarge btn-danger"
                            onclick="window.location='http://localhost/front/search.html#?keywords='+$('#autocomplete').val()"
                            type="button">搜索
                </div>
            </form>
        </div>
    </div>
    <!--All goods-->
    <div class="allgoods">
        <h4>全部商品</h4>
        <div class="cart-main">
            <div class="yui3-g cart-th">
                <div class="yui3-u-1-4"><input type="checkbox" name="" id="" value="" ng-model="selectAll"
                                               ng-click="all(selectAll);TbupdateSelection($event)"/> 全部
                </div>
                <div class="yui3-u-1-4">商品</div>
                <div class="yui3-u-1-8">单价（元）</div>
                <div class="yui3-u-1-8">数量</div>
                <div class="yui3-u-1-8">小计（元）</div>
                <div class="yui3-u-1-8">操作</div>
            </div>
            <div class="cart-item-list"  ng-repeat="cart in cartList">
                <div class="cart-shop">
                    <input type="checkbox" name="" id="" value="" ng-click="updateSelection($event,cart.sellerId)"
                           ng-checked="selectAll"/>
                    <span class="shopname self">{{cart.sellerName}}</span>
                </div>
                <div class="cart-body">
                    <div class="cart-list" ng-repeat="item in cart.orderItemList">
                        <ul class="goods-list yui3-g">
                            <li class="yui3-u-1-24">
                                <input type="checkbox" name="" id="" value=""
                                       ng-click="updateSelection($event,item.itemId);updateMoney($event,item.num,item.totalFee)"
                                       ng-checked="selectAll"/>
                            </li>
                            <li class="yui3-u-11-24">
                                <div class="good-item">
                                    <div class="item-img"><img src="{{item.picPath}}"/></div>
                                    <div class="item-msg">
                                        {{item.title}}
                                    </div>
                                </div>
                            </li>

                            <li class="yui3-u-1-8"><span class="price">￥{{item.price}}</span></li>
                            <li class="yui3-u-1-8">
                                <a href="javascript:void(0)" class="increment mins"
                                   ng-click="addGoodsToCartList(item.itemId,-1)">-</a>

                                <input autocomplete="off" type="tel" value="1" minnum="1" class="itxt"
                                       ng-model="item.num" oninput="value=value.replace(/[^\d]/g,'')"
                                       ng-blur="findKucun(item.itemId,item.num);findCartList()"/>
                                <#--<input type="text" value="">-->


                                <a href="javascript:void(0)" class="increment plus"
                                   ng-click="addGoodsToCartList(item.itemId,1)">+</a>
                            </li>
                            <li class="yui3-u-1-8"><span class="sum">{{item.totalFee}}</span></li>
                            <li class="yui3-u-1-8">
                                <a href="#none" ng-click="delCart(item.itemId)">删除</a><br/>
                                <a href="#none" ng-click="moveCart()">移到我的收藏</a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>

        </div>
        <div class="cart-tool">
            <div class="select-all">
                <input ng-if="cartList!=null" type="checkbox" name="" id="" value="" ng-model="selectAll"
                       ng-click="all(selectAll);TbupdateSelection($event)"/>
                <span ng-if="cartList!=null">全选</span>
                <h2 ng-if="cartList==null">购物车内暂时没有商品，登录后将显示您之前加入的商品</h2>
            </div>
            <div class="option" ng-if="cartList!=null">
                <a href="#none" ng-click="deleteCartToRedis()">删除选中的商品</a>
                <a href="#none" ng-click="moveCart()">移到我的关注</a>
                <#--<a href="#none">清除下柜商品</a>-->

            </div>
            <div class="toolbar">
                <div class="chosed" ng-if="cartList!=null">已选择<span>{{totalNum}}</span>件商品</div>
                <div class="sumprice" ng-if="cartList!=null" >
                    <span><em>总价（不含运费） ：</em><i class="summoney">¥{{totalMoney.toFixed(2)}}</i></span>
                    <#--<span><em>已节省：</em><i>-¥20.00</i></span>-->
                </div>
                <div class="sumbtn">
                    <a class="sum-btn" ng-click="createOrder()" ng-if="cartList!=null" target="_blank">结算</a>
                    <a class="sum-btn" onclick="window.location='http://localhost/front/'" ng-if="cartList==null" target="_blank">继续购物</a>
                </div>
            </div>
        </div>
        <div class="clearfix"></div>
        <div class="deled">
            <#--<span>已删除商品，您可以重新购买或加关注：</span>-->
            <#--<div class="cart-list del">-->
            <#--<ul class="goods-list yui3-g">-->
            <#--<li class="yui3-u-1-2">-->
            <#--<div class="good-item">-->
            <#--<div class="item-msg">Apple Macbook Air 13.3英寸笔记本电脑 银色（Corei5）处理器/8GB内存</div>-->
            <#--</div>-->
            <#--</li>-->
            <#--<li class="yui3-u-1-6"><span class="price">8848.00</span></li>-->
            <#--<li class="yui3-u-1-6">-->
            <#--<span class="number">1</span>-->
            <#--</li>-->
            <#--<li class="yui3-u-1-8">-->
            <#--<a href="#none">重新购买</a>-->
            <#--<a href="#none">移到我的关注</a>-->
            <#--</li>-->
            <#--</ul>-->
            <#--</div>-->
        </div>
        <#--{{selectIds}}-->
        <div class="liked" ng-init="findItems()">
            <ul class="sui-nav nav-tabs">
                <li class="active">
                    <a href="#index" data-toggle="tab">猜你喜欢</a>
                </li>
                <li>
                    <a href="#profile" data-toggle="tab">特惠换购</a>
                </li>
            </ul>
            <div class="clearfix"></div>
            <div class="tab-content">
                <div id="index" class="tab-pane active">
                    <div id="myCarousel" data-ride="carousel" data-interval="4000" class="sui-carousel slide">
                        <div class="carousel-inner">
                            <div class="active item">
                                <ul>
                                    <li ng-repeat="tbItem in ItemList | limitTo:3">
                                        <img src="{{tbItem.image}}" style="width: 230px;height: 200px;"/>
                                        <div class="intro">
                                            <i>{{tbItem.title}}</i>
                                        </div>
                                        <div class="money">
                                            <span>￥{{tbItem.price}}</span>
                                        </div>
                                        <div class="incar">
                                            <a href="#" class="sui-btn btn-bordered btn-xlarge btn-default"
                                               ng-click="addGoodsToCartList(tbItem.id,1)"><i class="car"></i><span
                                                        class="cartxt">加入购物车</span></a>
                                        </div>
                                    </li>
                                </ul>
                            </div>
                            <div class="item">
                                <ul>
                                    <li ng-repeat="tbItem in ItemList | limitTo:3">
                                        <img src="{{tbItem.image}}" style="width: 230px;height: 200px;"/>
                                        <div class="intro">
                                            <i>{{tbItem.title}}</i>
                                        </div>
                                        <div class="money">
                                            <span>{{tbItem.price}}</span>
                                        </div>
                                        <div class="incar">
                                            <a href="#" class="sui-btn btn-bordered btn-xlarge btn-default"
                                               ng-click="addGoodsToCartList(tbItem.id,1)"><i class="car"></i><span
                                                        class="cartxt">加入购物车</span></a>
                                        </div>
                                    </li>
                                </ul>
                            </div>
                        </div>
                        <a href="#myCarousel" data-slide="prev" class="carousel-control left">‹</a>
                        <a href="#myCarousel" data-slide="next" class="carousel-control right">›</a>
                    </div>
                </div>
                <div id="profile" class="tab-pane">
                    <p>特惠选购</p>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- 底部栏位 -->
<!--页面底部-->
<div class="clearfix footer">
    <div class="py-container">
        <div class="footlink">
            <div class="Mod-service">
                <ul class="Mod-Service-list">
                    <li class="grid-service-item intro  intro1">

                        <i class="serivce-item fl"></i>
                        <div class="service-text">
                            <h4>正品保障</h4>
                            <p>正品保障，提供发票</p>
                        </div>

                    </li>
                    <li class="grid-service-item  intro intro2">

                        <i class="serivce-item fl"></i>
                        <div class="service-text">
                            <h4>正品保障</h4>
                            <p>正品保障，提供发票</p>
                        </div>

                    </li>
                    <li class="grid-service-item intro  intro3">

                        <i class="serivce-item fl"></i>
                        <div class="service-text">
                            <h4>正品保障</h4>
                            <p>正品保障，提供发票</p>
                        </div>

                    </li>
                    <li class="grid-service-item  intro intro4">

                        <i class="serivce-item fl"></i>
                        <div class="service-text">
                            <h4>正品保障</h4>
                            <p>正品保障，提供发票</p>
                        </div>

                    </li>
                    <li class="grid-service-item intro intro5">

                        <i class="serivce-item fl"></i>
                        <div class="service-text">
                            <h4>正品保障</h4>
                            <p>正品保障，提供发票</p>
                        </div>

                    </li>
                </ul>
            </div>
            <div class="clearfix Mod-list">
                <div class="yui3-g">
                    <div class="yui3-u-1-6">
                        <h4>购物指南</h4>
                        <ul class="unstyled">
                            <li>购物流程</li>
                            <li>会员介绍</li>
                            <li>生活旅行/团购</li>
                            <li>常见问题</li>
                            <li>购物指南</li>
                        </ul>

                    </div>
                    <div class="yui3-u-1-6">
                        <h4>配送方式</h4>
                        <ul class="unstyled">
                            <li>上门自提</li>
                            <li>211限时达</li>
                            <li>配送服务查询</li>
                            <li>配送费收取标准</li>
                            <li>海外配送</li>
                        </ul>
                    </div>
                    <div class="yui3-u-1-6">
                        <h4>支付方式</h4>
                        <ul class="unstyled">
                            <li>货到付款</li>
                            <li>在线支付</li>
                            <li>分期付款</li>
                            <li>邮局汇款</li>
                            <li>公司转账</li>
                        </ul>
                    </div>
                    <div class="yui3-u-1-6">
                        <h4>售后服务</h4>
                        <ul class="unstyled">
                            <li>售后政策</li>
                            <li>价格保护</li>
                            <li>退款说明</li>
                            <li>返修/退换货</li>
                            <li>取消订单</li>
                        </ul>
                    </div>
                    <div class="yui3-u-1-6">
                        <h4>特色服务</h4>
                        <ul class="unstyled">
                            <li>夺宝岛</li>
                            <li>DIY装机</li>
                            <li>延保服务</li>
                            <li>乐购E卡</li>
                            <li>乐购通信</li>
                        </ul>
                    </div>
                    <div class="yui3-u-1-6">
                        <h4>帮助中心</h4>
                        <img src="img/wx_cz.jpg">
                    </div>
                </div>
            </div>
            <div class="Mod-copyright">
                <ul class="helpLink">
                    <li>关于我们<span class="space"></span></li>
                    <li>联系我们<span class="space"></span></li>
                    <li>关于我们<span class="space"></span></li>
                    <li>商家入驻<span class="space"></span></li>
                    <li>营销中心<span class="space"></span></li>
                    <li>友情链接<span class="space"></span></li>
                    <li>关于我们<span class="space"></span></li>
                    <li>营销中心<span class="space"></span></li>
                    <li>友情链接<span class="space"></span></li>
                    <li>关于我们</li>
                </ul>
                <p>地址：北京市昌平区建材城西路金燕龙办公楼一层 邮编：100096 电话：400-618-4000 传真：010-82935100</p>
                <p>京ICP备08001421号京公网安备110108007702</p>
            </div>
        </div>
    </div>
</div>
<!--页面底部END-->

<script type="text/javascript" src="js/plugins/jquery/jquery.min.js"></script>
<script type="text/javascript" src="js/plugins/jquery.easing/jquery.easing.min.js"></script>
<script type="text/javascript" src="js/plugins/sui/sui.min.js"></script>
<script type="text/javascript" src="js/widget/nav.js"></script>
</body>

</html>