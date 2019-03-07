package com.lg.biz.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.lg.commons.util.validators.Insert;
import com.lg.commons.util.validators.Update;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
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
@TableName("tb_seller")
public class TbSeller extends Model<TbSeller> {

    public static final Integer
            PAGE_SIZE = 5;

    public static final Integer PAGE_NUM = 1;

    /**
     * 用户ID
     */
    @NotBlank(groups={Update.class, Insert.class})
    @TableId(value = "seller_id",type = IdType.INPUT)
	private String sellerId;
    /**
     * 公司名
     */
    @NotBlank(groups={Update.class, Insert.class})
	private String name;
    /**
     * 店铺名称
     */
    @NotBlank(groups={Update.class, Insert.class})
	@TableField("nick_name")
	private String nickName;
    /**
     * 密码
     */
    @NotBlank(groups={Update.class, Insert.class})
	private String password;
    /**
     * EMAIL
     */
    @NotBlank(groups={Update.class, Insert.class})
	private String email;
    /**
     * 公司手机
     */
    @NotBlank(groups={Update.class, Insert.class})
	private String mobile;
    /**
     * 公司电话
     */
    @NotBlank(groups={Update.class, Insert.class})
	private String telephone;
    /**
     * 状态
     */
    @NotBlank(groups={Update.class, Insert.class})
	private String status;
    /**
     * 详细地址
     */
    @NotBlank(groups={Update.class, Insert.class})
	@TableField("address_detail")
	private String addressDetail;
    /**
     * 联系人姓名
     */
    @NotBlank(groups={Update.class, Insert.class})
	@TableField("linkman_name")
	private String linkmanName;
    /**
     * 联系人QQ
     */
    @NotBlank(groups={Update.class, Insert.class})
	@TableField("linkman_qq")
	private String linkmanQq;
    /**
     * 联系人电话
     */
    @NotBlank(groups={Update.class, Insert.class})
	@TableField("linkman_mobile")
	private String linkmanMobile;
    /**
     * 联系人EMAIL
     */
    @NotBlank(groups={Update.class, Insert.class})
	@TableField("linkman_email")
	private String linkmanEmail;
    /**
     * 营业执照号
     */
    @NotBlank(groups={Update.class, Insert.class})
	@TableField("license_number")
	private String licenseNumber;
    /**
     * 税务登记证号
     */
    @NotBlank(groups={Update.class, Insert.class})
	@TableField("tax_number")
	private String taxNumber;
    /**
     * 组织机构代码
     */
    @NotBlank(groups={Update.class, Insert.class})
	@TableField("org_number")
	private String orgNumber;
    /**
     * 公司地址
     */
    @NotBlank(groups={Update.class, Insert.class})
	private Long address;
    /**
     * 公司LOGO图
     */
    @NotBlank(groups={Update.class, Insert.class})
	@TableField("logo_pic")
	private String logoPic;
    /**
     * 简介
     */
    @NotBlank(groups={Update.class, Insert.class})
	private String brief;
    /**
     * 创建日期
     */
    @NotBlank(groups={Update.class, Insert.class})
	@TableField("create_time")
	private LocalDateTime createTime;
    /**
     * 法定代表人
     */
    @NotBlank(groups={Update.class, Insert.class})
	@TableField("legal_person")
	private String legalPerson;
    /**
     * 法定代表人身份证
     */
    @NotBlank(groups={Update.class, Insert.class})
	@TableField("legal_person_card_id")
	private String legalPersonCardId;
    /**
     * 开户行账号名称
     */
    @NotBlank(groups={Update.class, Insert.class})
	@TableField("bank_user")
	private String bankUser;
    /**
     * 开户行
     */
    @NotBlank(groups={Update.class, Insert.class})
	@TableField("bank_name")
	private String bankName;


	@Override
	protected Serializable pkVal() {
		return this.sellerId;
	}

}
