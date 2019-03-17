package com.lg.order.model.domain;

import java.io.Serializable;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableField;


import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author xuzilou
 * @since 2019-02-19
 */
@Data
@Accessors(chain = true)
@TableName("tb_order_item")
public class TbOrderItem extends Model<TbOrderItem> implements Serializable{

    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type = IdType.INPUT)
	private Long id;
    /**
     * 商品id
     */
	@TableField("item_id")
	private Long itemId;
    /**
     * SPU_ID
     */
	@TableField("goods_id")
	private Long goodsId;
    /**
     * 订单id
     */
	@TableField("order_id")
	private Long orderId;
    /**
     * 商品标题
     */
	private String title;
    /**
     * 商品单价
     */
	private BigDecimal price;
    /**
     * 商品购买数量
     */
	private Integer num;
    /**
     * 商品总金额
     */
	@TableField("total_fee")
	private BigDecimal totalFee;
    /**
     * 商品图片地址
     */
	@TableField("pic_path")
	private String picPath;

	@TableField("seller_id")
	private String sellerId;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
