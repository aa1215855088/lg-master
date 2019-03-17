package com.lg.order.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lg.commons.base.enums.ErrorCodeEnum;
import com.lg.commons.base.exception.BusinessException;
import com.lg.commons.util.wrapper.Wrapper;
import com.lg.order.model.domain.Cart;
import com.lg.order.model.domain.TbOrderItem;
import com.lg.order.service.CartService;
import com.lg.product.model.domain.TbItem;
import com.lg.product.service.TbItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Validator;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service(version = "1.0.0", timeout = 6000)
@Transactional
public class CartServiceImpl implements CartService {

    @Autowired
    private Validator validator;

    @Reference(version = "1.0.0")
    private TbItemService tbItemService;

    @Override
    public List<Cart> addGoodsToCartList(List<Cart> cartList, Long itemId, Integer num) {
        TbItem item = this.tbItemService.selectById(itemId);
        if (item == null) {
            throw new BusinessException(ErrorCodeEnum.GL99990500, "没有该商品");
        }
        if (item.getStatus().equals("2")) {
            throw new BusinessException(ErrorCodeEnum.GL99990500, "该商品不合法");
        }
        //2.根据SKU对象获得商家ID
        String sellerId = item.getSellerId();
        Cart cart = null;
        if (CollUtil.isEmpty(cartList)) {
            cartList = new ArrayList<>();
        } else {
            cart = searchCartBySellerId(cartList, sellerId);
        }
        //3.根据商家ID在购物车列表中查询购物车对象
        if (cart == null) {
            //4.如果购物车列表中不存在购物车对象
            //4.1创建新的购物车对象
            cart = new Cart();
            cart.setSellerId(sellerId);
            cart.setSellerName(item.getSeller());
            List<TbOrderItem> orderItemList = new ArrayList<>();
            TbOrderItem tbOrderItem = creatOrderItem(item, num);
            orderItemList.add(tbOrderItem);
            cart.setOrderItemList(orderItemList);
            //4.2将新的购物车对象加到购物车列表中
            cartList.add(cart);
        } else {//5.如果购物车列表中存在购物车对象
            //判断该商品是否在购物车的商品详情列表中
            TbOrderItem orderItem = searchOrderItemByItemId(cart.getOrderItemList(), itemId);
            if (orderItem == null) {
                //5.1如果不在，创建商品详情列表，并添加到新的详情列表中
                orderItem = creatOrderItem(item, num);
                cart.getOrderItemList().add(orderItem);
            } else {
                //5.2如果在，修改数量，并修改金额
                orderItem.setNum(orderItem.getNum() + num);
                orderItem.setTotalFee(new BigDecimal(orderItem.getPrice().doubleValue() * orderItem.getNum()));
                //如果该商品详情数量小于0，则移除该商品详情
                if (orderItem.getNum() <= 0) {
                    cart.getOrderItemList().remove(orderItem);
                }
                //如果购物车的详情数量为0，则在购物车列表中移除该购物车
                if (cart.getOrderItemList().size() == 0) {
                    cartList.remove(cart);
                }
            }

        }
        return cartList;
    }

    @Autowired
    private RedisTemplate redisTemplate;
    //private StringRedisTemplate redisTemplate;

    @Override
    public List<Cart> mergeCartList(List<Cart> cartList1, List<Cart> cartList2) {
        for (Cart cart : cartList2) {
            for (TbOrderItem tbOrderItem : cart.getOrderItemList()) {
                cartList1 = addGoodsToCartList(cartList1, tbOrderItem.getItemId(), tbOrderItem.getNum());
            }
        }
        return cartList1;
    }

    @Override
    public TbItem
    findByItemid(Long itemId) {
        TbItem item = this.tbItemService.selectById(itemId);
        return item;
    }

    @Override
    public void updateNum(String username, Long itemId, Integer num) {
        List<Cart> cartList = (List<Cart>) redisTemplate.boundHashOps("cartlist").get(username);
        for (Iterator<Cart> iterator = cartList.iterator(); iterator.hasNext(); ) {
            Cart cart = iterator.next();
            for (Iterator<TbOrderItem> cartIterator = cart.getOrderItemList().iterator(); cartIterator.hasNext(); ) {
                TbOrderItem tborderitem = cartIterator.next();
                if (tborderitem.getItemId().equals(itemId)) {
                    cartIterator.remove();
                }
            }
        }
        List<Cart> cartList2 = addGoodsToCartList(cartList, itemId, num);
        redisTemplate.boundHashOps("cartlist").put(username, cartList2);
    }

    @Override
    public List<TbItem> findItem() {
        Wrapper<List<TbItem>> all = this.tbItemService.findAll();
        return all.getResult();
    }

    @Override
    public void moveCart(String username, Long[] ids) {
        //获取购物车
        List<Cart> cartList = (List<Cart>) redisTemplate.boundHashOps("cartlist").get(username);
        List<Cart> moveCart = (List<Cart>) redisTemplate.boundHashOps("guanzhulist").get(username);
        //移除选中商品
        for (Iterator<Cart> iterator = cartList.iterator(); iterator.hasNext(); ) {
            Cart cart = iterator.next();
            for (Iterator<TbOrderItem> cartIterator = cart.getOrderItemList().iterator(); cartIterator.hasNext(); ) {
                TbOrderItem tborderitem = cartIterator.next();
                for (Long id : ids) {
                    if (tborderitem.getItemId().equals(id)) {
                        cartIterator.remove();
                        moveCart = addGoodsToCartList(moveCart, id, 1);
                    }
                }
            }
        }
        //将剩下的商品存入redis
        redisTemplate.boundHashOps("cartlist").put(username, cartList);
        //将移动商品存入另一个集合
        redisTemplate.boundHashOps("guanzhulist").put(username, moveCart);

    }

    @Override
    public List<Cart> findmoveCart(String username) {
        List<Cart> moveCart = (List<Cart>) redisTemplate.boundHashOps("guanzhulist").get(username);
        if (moveCart == null) {
            moveCart = new ArrayList();
        }
        return moveCart;
    }

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public List<Cart> getRedisCartByUserAndIds(String name, Long[] ids) {
        List<Cart> cartList = (List<Cart>) redisTemplate.boundHashOps("cartlist").get(name);
        List<Cart> carts = new ArrayList<>();
        for (Cart cart : cartList) {
            Cart order = new Cart();
            List<TbOrderItem> orderItemList = new ArrayList<>();
            for (TbOrderItem tbOrderItem : cart.getOrderItemList()) {
                Long itemId = tbOrderItem.getItemId();
                for (Long id : ids) {
                    if (id.equals(itemId)) {
                        orderItemList.add(tbOrderItem);
                    }
                }
            }
            order.setSellerId(cart.getSellerId());
            order.setSellerName(cart.getSellerName());
            order.setOrderItemList(orderItemList);
            carts.add(order);
        }
        //将生成的订单存入redis中
        stringRedisTemplate.opsForValue().set("ORDERLIST:" + name, JSON.toJSONString(carts), 30, TimeUnit.MINUTES);
        return carts;
    }

    @Override
    public void deleteCartToRedis(String username, Long[] ids) {

        List<Cart> cartList = (List<Cart>) redisTemplate.boundHashOps("cartlist").get(username);


        for (Iterator<Cart> iterator = cartList.iterator(); iterator.hasNext(); ) {
            for (Long id : ids) {
                Cart cart = iterator.next();
                for (Iterator<TbOrderItem> cartIterator = cart.getOrderItemList().iterator(); cartIterator.hasNext(); ) {
                    TbOrderItem tborderitem = cartIterator.next();
                    if (tborderitem.getItemId().equals(id)) {
                        cartIterator.remove();
                        if (CollUtil.isEmpty(cart.getOrderItemList())) {
                            iterator.remove();
                        }
                    }
                }
            }
        }
        redisTemplate.boundHashOps("cartlist").put(username, cartList);

    }


    @Override
    public List<Cart> findCartListToRedis(String username) {

        List<Cart> cartList = (List<Cart>) redisTemplate.boundHashOps("cartlist").get(username);
        if (cartList == null) {
            return null;
        }
        return cartList;
    }

    @Override
    public void saveCartListToRedis(String username, List<Cart> cartList) {
        /*if (ArrayUtil.isEmpty(cartList)) {
            return;
        }
        this.redisTemplate.opsForValue().set("CART:" + username, JSON.toJSON(cartList).toString());*/
        redisTemplate.boundHashOps("cartlist").put(username, cartList);
        //redisTemplate.boundHashOps("carlist").delete();
    }

    /**
     * 根据商家ID在购物车列表中查询购物车对象
     *
     * @param cartList
     * @param SellerId
     * @return
     */
    private Cart searchCartBySellerId(List<Cart> cartList, String SellerId) {
        if (cartList == null) {
            return null;
        }
        for (Cart cart : cartList) {
            if (cart.getSellerId().equals(SellerId)) {
                return cart;
            }
        }
        return null;
    }

    /**
     * 创建商品详情对象
     *
     * @param item
     * @param num
     * @return
     */
    private TbOrderItem creatOrderItem(TbItem item, Integer num) {
        TbOrderItem tbOrderItem = new TbOrderItem();
        tbOrderItem.setGoodsId(item.getGoodsId());
        tbOrderItem.setItemId(item.getId());
        tbOrderItem.setNum(num);
        tbOrderItem.setPicPath(item.getImage());
        tbOrderItem.setPrice(item.getPrice());
        tbOrderItem.setSellerId(item.getSellerId());
        tbOrderItem.setTitle(item.getTitle());
        tbOrderItem.setTotalFee(new BigDecimal(item.getPrice().doubleValue() * num));
        return tbOrderItem;

    }

    /**
     * 根据itemId购物车详情列表中查询商品详情对象
     *
     * @param tbOrderItemList
     * @param itemId
     * @return
     */
    private TbOrderItem searchOrderItemByItemId(List<TbOrderItem> tbOrderItemList, Long itemId) {
        for (TbOrderItem tbOrderItem : tbOrderItemList) {
            if (tbOrderItem.getItemId().longValue() == itemId.longValue()) {
                return tbOrderItem;
            }
        }
        return null;
    }
}
