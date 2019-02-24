package com.lg.product.model.vo;

import com.lg.product.model.domain.TbGoods;
import com.lg.product.model.domain.TbGoodsDesc;
import com.lg.product.model.domain.TbItem;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;
@Data
@Accessors(chain = true)
public class Goods implements Serializable {

    private static final long serialVersionUID = -629258389710064860L;
    private TbGoods goods;//商品基本信息
    private TbGoodsDesc goodsDesc;//商品SUP扩展信息
    private List<TbItem> itemList;//SKU列表
}
