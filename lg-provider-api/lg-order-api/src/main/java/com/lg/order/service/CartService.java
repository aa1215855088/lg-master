package com.lg.order.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lg.commons.util.wrapper.Wrapper;
import com.lg.order.model.domain.Cart;
import com.lg.order.model.domain.TbOrderItem;
import com.lg.product.model.domain.TbItem;


import java.util.List;

public interface CartService {

    //添加购物车
    public List<Cart> addGoodsToCartList(List<Cart> cartList, Long itemId, Integer num);
    //查询购物车
    public List<Cart> findCartListToRedis(String username);
    //将购物车保存到redis
    public void saveCartListToRedis(String username, List<Cart> cartList);
    //合并购物车
    public List<Cart> mergeCartList(List<Cart> cartList1, List<Cart> cartList2);
    //根据Id查询商品
    public TbItem findByItemid(Long itemId);
    //删除rdis中的商品
    public void deleteCartToRedis(String username, Long[] itemIds);
    //修改商品数量
    public void updateNum(String username, Long itemId, Integer num);
    //滚动商品广告
    public List<TbItem> findItem();
    //移动商品到我的关注
    public void moveCart(String username, Long[] itemIds);
    //查询移动商品
    public List<Cart> findmoveCart(String username);

    List<Cart> getRedisCartByUserAndIds(String name, Long[] ids);
}
