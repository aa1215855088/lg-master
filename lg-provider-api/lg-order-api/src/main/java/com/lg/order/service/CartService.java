package com.lg.order.service;

import com.lg.commons.util.wrapper.Wrapper;
import com.lg.order.model.domain.Cart;

import java.util.List;

public interface
CartService {

    public List<Cart> addGoodsToCartList(List<Cart>cartList, Long itemId, Integer num);
}
