package com.lg.order.model.domain;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

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
@TableName("tb_pay_log")
public class TbPayLog extends Model<TbPayLog> {

    private static final long serialVersionUID = 1L;

    /**
     * 支付订单号
     */
    @TableId("out_trade_no")
	private String outTradeNo;
    /**
     * 创建日期
     */
	@TableField("create_time")
	private LocalDateTime createTime;
    /**
     * 支付完成时间
     */
	@TableField("pay_time")
	private LocalDateTime payTime;
    /**
     * 支付金额（分）
     */
	@TableField("total_fee")
	private Long totalFee;
    /**
     * 用户ID
     */
	@TableField("user_id")
	private String userId;
    /**
     * 交易号码
     */
	@TableField("transaction_id")
	private String transactionId;
    /**
     * 交易状态
     */
	@TableField("trade_state")
	private String tradeState;
    /**
     * 订单编号列表
     */
	@TableField("order_list")
	private String orderList;
    /**
     * 支付类型
     */
	@TableField("pay_type")
	private String payType;


	@Override
	protected Serializable pkVal() {
		return this.outTradeNo;
	}

}
