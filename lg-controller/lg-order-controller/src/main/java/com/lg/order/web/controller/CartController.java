package com.lg.order.web.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lg.commons.core.controller.BaseController;
import com.lg.commons.util.cookie.CookieUtil;
import com.lg.commons.util.wrapper.WrapMapper;
import com.lg.commons.util.wrapper.Wrapper;
import com.lg.order.model.domain.Cart;
import com.lg.order.service.CartService;
import com.lg.order.web.config.AuthenticationFacade;
import com.lg.product.model.domain.TbItem;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/cart", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "WEB - CatController", tags = "购物车Api", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class CartController extends BaseController {

    @Reference(version = "1.0.0")
    private CartService cartService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;
    @Autowired
    private AuthenticationFacade authenticationFacade;

    @GetMapping("")
    public ModelAndView cart() {
        return new ModelAndView("cart");
    }

    @RequestMapping("/findCartList")
    @CrossOrigin(origins = "http://localhost:63342")
    public List<Cart> findCartList(String username) {
        logger.info("用户信息:{}",username);
        String cartlistString = CookieUtil.getCookieValue(request, "cartlist", "UTF-8");
        if (cartlistString == null || cartlistString.equals("")) {
            cartlistString = "[]";
        }
        List<Cart> cartsCookie = JSON.parseArray(cartlistString, Cart.class);
        if (StrUtil.isBlank(username)) {
            logger.info("获得Cookie中的购物车:{}", cartlistString);
            return cartsCookie;
        } else {//登陆了则取redis中的购物车
            logger.info("获取Redis的购物车:{}", cartlistString);
            List<Cart> cartRedis = cartService.findCartListToRedis(username);
            if (cartsCookie.size() > 0) {
                List<Cart> cartList = cartService.mergeCartList(cartRedis, cartsCookie);
                cartService.saveCartListToRedis(username, cartList);
                CookieUtil.deleteCookie(request, response, "cartlist");
                return cartList;
            }
            return cartRedis;
        }

    }

    @RequestMapping("/addGoodsToCartList")
    public Wrapper addGoodsToCartList(Long itemId, Integer num, HttpServletRequest request,String username) {
        //提取购物车
        List<Cart> cartList = findCartList(username);
        //调用服务层方法
        cartList = this.cartService.addGoodsToCartList(cartList, itemId, num);
        if (StrUtil.isBlank(username)) {
            //将新购物车存入cookie购物车
            String carListString = JSON.toJSONString(cartList);
            logger.info("购物车加入Cookie:{}", cartList);
            CookieUtil.setCookie(request, response, "cartlist", carListString, 24 * 3600, "UTF-8");
        } else {
            //将购物车存入redis
            logger.info("购物车加入Redis:{}", cartList);
            cartService.saveCartListToRedis(username, cartList);
        }

        return WrapMapper.ok();
    }

    @RequestMapping("/findKucun")
    public TbItem findKucun(Long itemId) {
        TbItem tbItem = this.cartService.findByItemid(itemId);
        System.out.println(tbItem.getNum());
        return tbItem;
    }

    @RequestMapping("/deleteCartToRedis")
    public Wrapper deleteCartToRedis(Long[] ids,String username) {
        this.cartService.deleteCartToRedis(username, ids);
        return WrapMapper.ok();
    }

    @RequestMapping("/updateNum")
    public Wrapper updateNum(Long id, Integer num,String username) {
        this.cartService.updateNum(username, id, num);
        return WrapMapper.ok();
    }

    @RequestMapping("/findItem")
    public Wrapper<List<TbItem>> findItem() {
        List<TbItem> tbItems = this.cartService.findItem();
        return WrapMapper.ok(tbItems);
    }

    @RequestMapping("/moveCart")
    public Wrapper moveCart(Long[] ids,String username) {
        this.cartService.moveCart(username, ids);
        return WrapMapper.ok();
    }

    @RequestMapping("/findmoveCart")
    public List<Cart> findmoveCart(String username) {
        List<Cart> moveCart = null;
        if (StrUtil.isBlank(username)) {
            return moveCart;
        } else {
            moveCart = this.cartService.findmoveCart(username);
            return moveCart;
        }

    }


}
