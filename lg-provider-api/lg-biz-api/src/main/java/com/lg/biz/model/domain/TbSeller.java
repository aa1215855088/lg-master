package com.lg.biz.model.domain;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;


import com.sun.xml.internal.txw2.annotation.XmlNamespace;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.*;

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
@TableName("tb_seller")
public class
TbSeller extends Model<TbSeller> {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @TableId(value = "seller_id",type = IdType.INPUT)
	@NotBlank(message = "用户ID不能为空")
	private String sellerId;
    /**
     * 公司名
     */
	@NotBlank(message = "公司名不能为空")
	private String
            name;
    /**
     * 店铺名称
     */
	@NotBlank(message = "公司名不能为空")
	@TableField("nick_name")
	private String nickName;
    /**
     * 密码
     */
	@NotBlank(message = "密码不能为空")
	private String password;
    /**
     * EMAIL
     */
	private String email;
    /**
     * 公司手机
     */

    private String mobile;
    /**
     * 公司电话
     */
    @NotEmpty(message = "公司电话电话不能为空")
	@Pattern(regexp = "^1[3|4|5|7|8][0-9]\\d{8}$",message = "公司电话不能为空")
	private String telephone;
    /**
     * 状态
     */
	private String
            status;
    /**
     * 详细地址
     */
    @NotBlank(message = "公司详细地址不能为空")
	@TableField("address_detail")
	private String addressDetail;
    /**
     * 联系人姓名
     */
    @NotBlank(message = "联系人姓名不能为空")
	@TableField("linkman_name")
	private String linkmanName;
    /**
     * 联系人QQ
     */
    @NotEmpty(message = "qq不能为空")
	@Pattern(regexp = "^[1-9][0-9]{4,11}$",message = "qq格式错,请输入5到12联系人qq")
	@TableField("linkman_qq")
	private String linkmanQq;
    /**
     * 联系人电话
     */
    @NotEmpty(message = "联系人电话不能为空")
	@Pattern(regexp = "^1[3|4|5|7|8][0-9]\\d{8}$",message = "联系人电话格式错误")
	@TableField("linkman_mobile")
	private String linkmanMobile;
    /**
     * 联系人EMAIL
     */
    @Email(message = "email格式错误")
	@NotBlank(message = "邮箱不能为空")
	@TableField("linkman_email")
	private String linkmanEmail;
    /**
     * 营业执照号
     */
    @NotBlank(message = "营业执照号不能为空")
	@TableField("license_number")
	private String licenseNumber;
    /**
     * 税务登记证号
     */
	@NotBlank(message = "税务登记证号不能为空")
	@TableField("tax_number")
	private String taxNumber;
    /**
     * 组织机构代码
     */
	@NotBlank(message = "组织机构代码不能为空")
	@TableField("org_number")
	private String orgNumber;
    /**
     * 公司地址
     */
	private Long address;
    /**
     * 公司LOGO图
     */
	@TableField("logo_pic")
	private String logoPic;
    /**
     * 简介
     */
	private String brief;
    /**
     * 创建日期
     */
	@TableField("create_time")
	private LocalDateTime createTime;
    /**
     * 法定代表人
     */
    @NotBlank(message = "法定代表人不能为空")
	@TableField("legal_person")
	private String legalPerson;
    /**
     * 法定代表人身份证
     */
	@NotBlank(message = "法定代表人身份证不能为空")
	@Pattern(regexp = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$",message = "法定代表人身份证格式错误")
	@TableField("legal_person_card_id")
	private String legalPersonCardId;
    /**
     * 开户行账号名称
     */
	@NotBlank(message = "开户行账号名称不能为空")
	@TableField("bank_user")
	private String bankUser;
    /**
     * 开户行
     */
	@NotBlank(message = "开户行不能为空")
	@TableField("bank_name")
	private String bankName;


	@Override
	protected Serializable pkVal() {
		return this.sellerId;
	}

}
