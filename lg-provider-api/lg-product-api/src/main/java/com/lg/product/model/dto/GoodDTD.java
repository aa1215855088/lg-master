package com.lg.product.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lg.product.model.domain.TbGoods;
import com.lg.product.model.domain.TbGoodsDesc;
import com.lg.product.model.domain.TbItem;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoodDTD implements Serializable {

    private static final long serialVersionUID = -629258389710064860L;


    private TbGoods goods;

    private TbGoodsDesc  goodsDesc;

  /*  private List<TbItem> itemList;*/
    private String itemList;
}
