package com.lg.order.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lg.commons.base.enums.ErrorCodeEnum;
import com.lg.commons.base.exception.BusinessException;
import com.lg.commons.util.wrapper.WrapMapper;
import com.lg.commons.util.wrapper.Wrapper;
import com.lg.order.model.domain.Cart;
import com.lg.order.model.domain.TbOrder;
import com.lg.order.model.domain.TbOrderItem;
import com.lg.order.service.CartService;
import com.lg.product.model.domain.TbItem;
import com.lg.product.model.domain.TbItemCat;
import com.lg.product.service.TbItemService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Validator;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CartServiceImpl  implements CartService {

    @Autowired
    private Validator validator;

    @Reference(version = "1.0.0")
    private TbItemService tbItemService;

//    @Override
//    public List<Cart> addGoodsToCartList(List<Cart> cartList, Long itemId, Integer num) {
//        //1.根据skuId查询SKU对象
//        //this.baseMapper.selectOne(new QueryWrapper<TbItem>().eq("item_id",itemId));
//        TbItem item = this.tbItemService.
//        if (item == null) {
//            throw new BusinessException(ErrorCodeEnum.GL99990500, "没有该商品");
//        }
//        if (item.getStatus().equals("1")) {
//            throw new BusinessException(ErrorCodeEnum.GL99990500, "该商品不合法");
//        }
//        //2.根据SKU对象获得商家ID
//        String sellerId = item.getSellerId();
//
//        //3.根据商家ID在购物车列表中查询购物车对象
//        Cart cart = searchCartBySellerId(cartList, sellerId);
//
//        if (cart == null) {//4.如果购物车列表中不存在购物车对象
//            //4.1创建新的购物车对象
//            cart = new Cart();
//            cart.setSellerId(sellerId);
//            cart.setSellerName(item.getSeller());
//            List<TbOrderItem> orderItemList = new ArrayList<>();
//            TbOrderItem tbOrderItem = creatOrderItem(item, num);
//            orderItemList.add(tbOrderItem);
//            cart.setOrderItemList(orderItemList);
//            //4.2将新的购物车对象加到购物车列表中
//            cartList.add(cart);
//
//        } else {//5.如果购物车列表中存在购物车对象
//            //判断该商品是否在购物车的商品详情列表中
//            TbOrderItem orderItem = searchOrderItemByItemId(cart.getOrderItemList(), itemId);
//            if (orderItem == null) {
//                //5.1如果不在，创建商品详情列表，并添加到新的详情列表中
//                orderItem = creatOrderItem(item, num);
//                cart.getOrderItemList().add(orderItem);
//            } else {
//                //5.2如果在，修改数量，并修改金额
//                orderItem.setNum(orderItem.getNum() + num);
//                orderItem.setTotalFee(new BigDecimal(orderItem.getPrice().doubleValue() * orderItem.getNum()));
//                //如果该商品详情数量小于0，则移除该商品详情
//                if (orderItem.getNum() <= 0) {
//                    cart.getOrderItemList().remove(orderItem);
//                }
//                //如果购物车的详情数量为0，则在购物车列表中移除该购物车
//                if (cart.getOrderItemList().size() == 0) {
//                    cartList.remove(cart);
//                }
//            }
//
//        }
//
//        return cartList;
//    }
//
//    /**
//     * 根据商家ID在购物车列表中查询购物车对象
//     *
//     * @param cartList
//     * @param SellerId
//     * @return
//     */
//    private Cart searchCartBySellerId(List<Cart> cartList, String SellerId) {
//        for (Cart cart : cartList) {
//            if (cart.getSellerId().equals(SellerId)) {
//                return cart;
//            }
//        }
//        return null;
//    }
//
//    /**
//     * 创建商品详情对象
//     *
//     * @param item
//     * @param num
//     * @return
//     */
//    private TbOrderItem creatOrderItem(TbItem item, Integer num) {
//        TbOrderItem tbOrderItem = new TbOrderItem();
//        tbOrderItem.setGoodsId(item.getGoodsId());
//        tbOrderItem.setItemId(item.getId());
//        tbOrderItem.setNum(num);
//        tbOrderItem.setPicPath(item.getImage());
//        tbOrderItem.setPrice(item.getPrice());
//        tbOrderItem.setSellerId(item.getSellerId());
//        tbOrderItem.setTitle(item.getTitle());
//        tbOrderItem.setTotalFee(new BigDecimal(item.getPrice().doubleValue() * num));
//        return tbOrderItem;
//
//    }
//
//    /**
//     * 根据itemId购物车详情列表中查询商品详情对象
//     *
//     * @param tbOrderItemList
//     * @param itemId
//     * @return
//     */
//    private TbOrderItem searchOrderItemByItemId(List<TbOrderItem> tbOrderItemList, Long itemId) {
//        for (TbOrderItem tbOrderItem : tbOrderItemList) {
//            if (tbOrderItem.getItemId().longValue() == itemId.longValue()) {
//                return tbOrderItem;
//            }
//        }
//        return null;
//    }
}
