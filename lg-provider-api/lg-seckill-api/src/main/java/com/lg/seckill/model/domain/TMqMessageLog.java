package com.lg.seckill.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
 * @since 2019-03-08
 */
@Data
@Accessors(chain = true)
@TableName("t_mq_message_log")
public class TMqMessageLog implements Serializable
{

    private static final long serialVersionUID = 1L;

    /**
     * 消息唯一ID
     */
    @TableId(value = "message_id", type = IdType.INPUT)
    private Long messageId;
    /**
     * 业务类型
     */
    private Integer type;
    /**
     * 消息内容
     */
    private String message;
    /**
     * 重试次数
     */
    @TableField("try_count")
    private Integer tryCount;
    /**
     * 消息投递状态  0 投递中 1 投递成功   2 投递失败  4处理中
     */
    private Integer status;
    /**
     * --下一次重试时间 或 超时时间
     */
    @TableField("next_retry")
    private LocalDateTime nextRetry;
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

    @TableField(exist = false)
    private TMqMessageFailed tMqMessageFailed;

    public TMqMessageLog(Long messageId, Integer type, String message, Integer tryCount, Integer status,
                         LocalDateTime nextRetry) {
        this.messageId = messageId;
        this.type = type;
        this.message = message;
        this.tryCount = tryCount;
        this.status = status;
        this.nextRetry = nextRetry;
        this.createTime = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();
    }


    public TMqMessageLog(Long messageId, Integer type, String message, Integer tryCount, Integer status,
                         LocalDateTime nextRetry,
                         LocalDateTime createTime, LocalDateTime updateTime) {
        super();
        this.messageId = messageId;
        this.type = type;
        this.message = message;
        this.tryCount = tryCount;
        this.status = status;
        this.nextRetry = nextRetry;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public TMqMessageLog(Long messageId, Integer type, String message, Integer tryCount, Integer status,
                         LocalDateTime nextRetry,
                         LocalDateTime createTime, LocalDateTime updateTime, TMqMessageFailed messageFailed) {
        super();
        this.messageId = messageId;
        this.type = type;
        this.message = message;
        this.tryCount = tryCount;
        this.status = status;
        this.nextRetry = nextRetry;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.tMqMessageFailed = messageFailed;
    }

}
