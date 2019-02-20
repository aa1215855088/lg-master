package com.lg.user.model.domain;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;


import lombok.Data;
import lombok.experimental.Accessors;

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
@TableName("tb_address")
public class TbAddress extends Model<TbAddress> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 用户ID
     */
	@TableField("user_id")
	private String userId;
    /**
     * 省
     */
	@TableField("province_id")
	private String provinceId;
    /**
     * 市
     */
	@TableField("city_id")
	private String cityId;
    /**
     * 县/区
     */
	@TableField("town_id")
	private String townId;
    /**
     * 手机
     */
	private String mobile;
    /**
     * 详细地址
     */
	private String address;
    /**
     * 联系人
     */
	private String contact;
    /**
     * 是否是默认 1默认 0否
     */
	@TableField("is_default")
	private String isDefault;
    /**
     * 备注
     */
	private String notes;
    /**
     * 创建日期
     */
	@TableField("create_date")
	private LocalDateTime createDate;
    /**
     * 别名
     */
	private String alias;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
