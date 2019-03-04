package com.lg.product.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.lg.commons.util.validators.Insert;
import com.lg.commons.util.validators.Update;
import io.swagger.models.auth.In;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 商品表
 * </p>
 *
 * @author xuzilou
 * @since 2019-02-19
 */
@Data
@Accessors(chain = true)
@TableName("tb_item")
public class TbItem extends Model<TbItem> {

    private static final long serialVersionUID = 1L;

    /**
     * 商品id，同时也是商品编号
     */

	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 商品标题
     */
	private String title;
    /**
     * 商品卖点
     */
	@TableField("sell_point")
	private String sellPoint;
    /**
     * 商品价格，单位为：元
     */
	private BigDecimal price;
	@TableField("stock_count")
	private Integer stockCount;
    /**
     * 库存数量
     */
	private Integer num;
    /**
     * 商品条形码
     */
	private String barcode;
    /**
     * 商品图片
     */
	private String image;
    /**
     * 所属类目，叶子类目
     */
    @TableField("categoryId")
	private Long categoryId;
    /**
     * 商品状态，1-正常，2-下架，3-删除
     */
	private String status;
    /**
     * 创建时间
     */
	@TableField("create_time")
	private LocalDateTime createTime;
    /**
     * 更新时间
     */
	@TableField("update_time")
	private LocalDateTime updateTime;


	@TableField("item_sn")
	private String itemSn;

	@TableField("cost_pirce")
	private BigDecimal costPirce;

	@TableField("market_price")
	private BigDecimal marketPrice;

	@TableField("is_default")
	private String isDefault;

	@TableField("goods_id")
	private Long goodsId;

	@TableField("seller_id")
	private String sellerId;

	@TableField("cart_thumbnail")
	private String cartThumbnail;

	private String category;

	private String brand;

	private String spec;


	private String seller;

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
