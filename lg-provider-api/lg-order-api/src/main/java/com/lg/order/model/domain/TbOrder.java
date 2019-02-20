package com.lg.order.model.domain;

import java.io.Serializable;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("tb_order")
public class TbOrder extends Model<TbOrder> {

    private static final long serialVersionUID = 1L;

    /**
     * 订单id
     */
    @TableId("order_id")
	private Long orderId;
    /**
     * 实付金额。精确到2位小数;单位:元。如:200.07，表示:200元7分
     */
	private BigDecimal payment;
    /**
     * 支付类型，1、在线支付，2、货到付款
     */
	@TableField("payment_type")
	private String paymentType;
    /**
     * 邮费。精确到2位小数;单位:元。如:200.07，表示:200元7分
     */
	@TableField("post_fee")
	private String postFee;
    /**
     * 状态：1、未付款，2、已付款，3、未发货，4、已发货，5、交易成功，6、交易关闭,7、待评价
     */
	private String status;
    /**
     * 订单创建时间
     */
	@TableField("create_time")
	private LocalDateTime createTime;
    /**
     * 订单更新时间
     */
	@TableField("update_time")
	private LocalDateTime updateTime;
    /**
     * 付款时间
     */
	@TableField("payment_time")
	private LocalDateTime paymentTime;
    /**
     * 发货时间
     */
	@TableField("consign_time")
	private LocalDateTime consignTime;
    /**
     * 交易完成时间
     */
	@TableField("end_time")
	private LocalDateTime endTime;
    /**
     * 交易关闭时间
     */
	@TableField("close_time")
	private LocalDateTime closeTime;
    /**
     * 物流名称
     */
	@TableField("shipping_name")
	private String shippingName;
    /**
     * 物流单号
     */
	@TableField("shipping_code")
	private String shippingCode;
    /**
     * 用户id
     */
	@TableField("user_id")
	private String userId;
    /**
     * 买家留言
     */
	@TableField("buyer_message")
	private String buyerMessage;
    /**
     * 买家昵称
     */
	@TableField("buyer_nick")
	private String buyerNick;
    /**
     * 买家是否已经评价
     */
	@TableField("buyer_rate")
	private String buyerRate;
    /**
     * 收货人地区名称(省，市，县)街道
     */
	@TableField("receiver_area_name")
	private String receiverAreaName;
    /**
     * 收货人手机
     */
	@TableField("receiver_mobile")
	private String receiverMobile;
    /**
     * 收货人邮编
     */
	@TableField("receiver_zip_code")
	private String receiverZipCode;
    /**
     * 收货人
     */
	private String receiver;
    /**
     * 过期时间，定期清理
     */
	private LocalDateTime expire;
    /**
     * 发票类型(普通发票，电子发票，增值税发票)
     */
	@TableField("invoice_type")
	private String invoiceType;
    /**
     * 订单来源：1:app端，2：pc端，3：M端，4：微信端，5：手机qq端
     */
	@TableField("source_type")
	private String sourceType;
    /**
     * 商家ID
     */
	@TableField("seller_id")
	private String sellerId;


	@Override
	protected Serializable pkVal() {
		return this.orderId;
	}

}
