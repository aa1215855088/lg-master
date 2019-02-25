package com.lg.product.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.lg.commons.util.validators.Insert;
import com.lg.commons.util.validators.Update;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

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
@TableName("tb_goods_desc")
public class TbGoodsDesc extends Model<TbGoodsDesc> {

    private static final long serialVersionUID = 1L;

    /**
     * SPU_ID
     */
    @TableId(value = "goods_id",type = IdType.INPUT)
	private Long goodsId;
    /**
     * 描述
     */

	private String introduction;
    /**
     * 规格结果集，所有规格，包含isSelected
     */

	@TableField("specification_items")
	private String specificationItems;
    /**
     * 自定义属性（参数结果）
     */

	@TableField("custom_attribute_items")
	private String customAttributeItems;

	@TableField("item_images")
	private String itemImages;
    /**
     * 包装列表
     */
	@NotBlank(groups = {Update.class, Insert.class})
	@TableField("package_list")
	private String packageList;
    /**
     * 售后服务
     */
	@NotBlank(groups = {Update.class, Insert.class})
	@TableField("sale_service")
	private String saleService;


	@Override
	protected Serializable pkVal() {
		return this.goodsId;
	}

}
