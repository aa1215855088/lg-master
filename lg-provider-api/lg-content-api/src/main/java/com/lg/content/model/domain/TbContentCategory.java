package com.lg.content.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * 内容分类
 * </p>
 *
 * @author xuzilou
 * @since 2019-02-19
 */
@Data
@Accessors(chain = true)
@TableName("tb_content_category")
public class TbContentCategory extends Model<TbContentCategory> implements Serializable{

    private static final long serialVersionUID = 1L;

	/**
     * 类目ID
     */
	@Min(1)
    @NotNull(groups = {Update.class})
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
	/**
	 * 分类名称
	 */
	@NotBlank(groups = {Update.class, Insert.class})
	private String name;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
