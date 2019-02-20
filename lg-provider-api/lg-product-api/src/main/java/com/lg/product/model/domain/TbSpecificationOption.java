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
@TableName("tb_specification_option")
public class TbSpecificationOption extends Model<TbSpecificationOption> {

    private static final long serialVersionUID = 1L;

    /**
     * 规格项ID
     */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 规格项名称
     */
	@TableField("option_name")
	private String optionName;
    /**
     * 规格ID
     */
	@TableField("spec_id")
	private Long specId;
    /**
     * 排序值
     */
	private Integer orders;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
