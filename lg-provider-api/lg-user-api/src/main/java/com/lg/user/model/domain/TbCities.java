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
 * 行政区域地州市信息表
 * </p>
 *
 * @author xuzilou
 * @since 2019-02-19
 */
@Data
@Accessors(chain = true)
@TableName("tb_cities")
public class TbCities extends Model<TbCities> {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一ID
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 城市ID
     */
	private String cityid;
    /**
     * 城市名称
     */
	private String city;
    /**
     * 省份ID
     */
	private String provinceid;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
