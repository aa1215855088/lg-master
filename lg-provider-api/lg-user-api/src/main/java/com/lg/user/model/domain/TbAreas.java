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
 * 行政区域县区信息表
 * </p>
 *
 * @author xuzilou
 * @since 2019-02-19
 */
@Data
@Accessors(chain = true)
@TableName("tb_areas")
public class TbAreas extends Model<TbAreas> {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一ID
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 区域ID
     */
	private String areaid;
    /**
     * 区域名称
     */
	private String area;
    /**
     * 城市ID
     */
	private String cityid;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
