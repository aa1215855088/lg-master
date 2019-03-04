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
import org.hibernate.validator.constraints.Length;

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
@TableName("tb_brand")
public class TbBrand extends Model<TbBrand> {

    private static final long serialVersionUID = 1L;

    public static Integer PAGE_NUM=1;

	public static Integer PAGE_SIZE=5;


	@Min(1)
    @NotNull(groups = Update.class)
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 品牌名称
     */
	@NotBlank(groups = {Update.class, Insert.class})
	private String name;
    /**
     * 品牌首字母
     */
	@TableField("first_char")
	@NotBlank(groups = {Update.class, Insert.class})
	@Length(max = 1, groups = {Update.class, Insert.class})
	private String firstChar;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}


}
