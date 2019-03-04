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
 * 商品类目
 * </p>
 *
 * @author xuzilou
 * @since 2019-02-19
 */
@Data
@Accessors(chain = true)
@TableName("tb_item_cat")
public class TbItemCat extends Model<TbItemCat> {

    private static final long serialVersionUID = 1L;

    /**
     * 类目ID
     */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 父类目ID=0时，代表的是一级的类目
     */
	@TableField("parent_id")
	private Long parentId;
    /**
     * 类目名称
     */
	private String name;
    /**
     * 类型id
     */
	@TableField("type_id")
	private Long typeId;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}


}
