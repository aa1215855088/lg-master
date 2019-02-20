package com.lg.order.model.domain;

import java.io.Serializable;

import java.math.BigDecimal;
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
@TableName("tb_freight_template")
public class TbFreightTemplate extends Model<TbFreightTemplate> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 商家ID
     */
	@TableField("seller_id")
	private String sellerId;
    /**
     * 是否默认   （‘Y’是   'N'否）
     */
	@TableField("is_default")
	private String isDefault;
    /**
     * 模版名称
     */
	private String name;
    /**
     * 发货时间（1:12h  2:24h  3:48h  4:72h  5:7d 6:15d ）
     */
	@TableField("send_time_type")
	private String sendTimeType;
    /**
     * 统一价格
     */
	private BigDecimal price;
    /**
     * 创建时间
     */
	@TableField("create_time")
	private LocalDateTime createTime;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
