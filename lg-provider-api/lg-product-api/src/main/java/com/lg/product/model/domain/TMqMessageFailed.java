package com.lg.product.model.domain;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;
import com.lg.commons.core.mybatis.BaseEntity;


import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author xuzilou
 * @since 2019-03-08
 */
@Data
@Accessors(chain = true)
@TableName("t_mq_message_failed")
public class TMqMessageFailed  implements Serializable{

    private static final long serialVersionUID = 1L;

    /**
     * 处理失败唯一ID
     */
    @TableId("fail_id")
    private Long failId;
    /**
     * 消息唯一ID
     */
    @TableField("message_id")
    private Long messageId;
    /**
     * 失败原因-简略
     */
    @TableField("fail_title")
    private String failTitle;
    /**
     * 失败原因-详细
     */
    @TableField("fail_desc")
    private String failDesc;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;

    public TMqMessageFailed(Long failId, Long messageId, String failTitle, String failDesc, LocalDateTime createTime,
                            LocalDateTime updateTime) {
        this.failId = failId;
        this.messageId = messageId;
        this.failTitle = failTitle;
        this.failDesc = failDesc;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public TMqMessageFailed(Long messageId, String failTitle, String failDesc) {
        this.messageId = messageId;
        this.failTitle = failTitle;
        this.failDesc = failDesc;
        this.createTime = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();
    }


}
