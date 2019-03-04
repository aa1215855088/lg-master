package com.lg.order.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.lg.commons.util.cookie.CookieUtil;
import com.lg.commons.util.wrapper.Wrapper;
import com.lg.order.model.domain.Cart;
import com.lg.order.service.CartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/cart",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "WEB - TbItemCatController",tags = "购物车Api",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class CartController {

//    @Reference(version = "1.0.0")
//    private CartService cartService;
//
//    @Autowired
//    private HttpServletRequest request;
//
//    @Autowired
//    private HttpServletResponse response;
//
//
//    @GetMapping("/findCartList")
//    @ApiOperation(httpMethod = "GET",value = "购物车")
//    public List<Cart> findCartList(){
//        System.out.println("在cookie在拿数据");
//        String cartlistString=CookieUtil.getCookieValue(request,"cartlist","UTF-8");
//        if (cartlistString==null||cartlistString.equals("")){
//            cartlistString="[]";
//        }
//        List<Cart> cartsCookie=JSON.parseArray(cartlistString,Cart.class);
//        return  cartsCookie;
//    }
//
//    @PostMapping("/addGoodsToCartList")
//    @ApiOperation(httpMethod = "POST",value = "加入购物车")
//    public String addGoodsToCartList(Long itemId,Integer num){
//        //从cookie中提取购物车
//        List<Cart> cartList=findCartList();
//        //调用服务层方法
//        cartList=this.cartService.addGoodsToCartList(cartList,itemId,num);
//
//        //将新购物车存入购物车
//        String carListString=JSON.toJSONString(cartList);
//        CookieUtil.setCookie(request,response,"cartlist",carListString,24*3600,"UTF-8");
//
//        System.out.println("商品添加到cookie中成功");
//        return null;
//    }



}
