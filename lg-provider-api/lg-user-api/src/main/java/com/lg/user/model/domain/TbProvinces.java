package com.lg.user.model.domain;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;


import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 省份信息表
 * </p>
 *
 * @author xuzilou
 * @since 2019-02-19
 */
@Data
@Accessors(chain = true)
@TableName("tb_provinces")
public class TbProvinces extends Model<TbProvinces> {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一ID
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 省份ID
     */
	private String provinceid;
    /**
     * 省份名称
     */
	private String province;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
