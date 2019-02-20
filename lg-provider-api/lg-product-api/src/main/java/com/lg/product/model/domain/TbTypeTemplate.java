package com.lg.product.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.experimental.Accessors;

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
@TableName("tb_type_template")
public class TbTypeTemplate extends Model<TbTypeTemplate> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 模板名称
     */
	private String name;
    /**
     * 关联规格
     */
	@TableField("spec_ids")
	private String specIds;
    /**
     * 关联品牌
     */
	@TableField("brand_ids")
	private String brandIds;
    /**
     * 自定义属性
     */
	@TableField("custom_attribute_items")
	private String customAttributeItems;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
