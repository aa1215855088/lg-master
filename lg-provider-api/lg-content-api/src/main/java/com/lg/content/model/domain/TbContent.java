package com.lg.content.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.lg.commons.util.validators.Insert;
import com.lg.commons.util.validators.Update;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

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
@TableName("tb_content")
public class TbContent extends Model<TbContent> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Min(1)
    @NotNull(groups = {Update.class})
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 内容类目ID
     */
    @Min(1)
    @NotNull(groups = {Update.class, Insert.class})
    @TableField("category_id")
    private Long categoryId;
    /**
     * 内容标题
     */
    @NotBlank(groups = {Update.class, Insert.class})
    private String title;
    /**
     * 链接
     */
    @NotBlank(groups = {Update.class})
    private String url;
    /**
     * 图片绝对路径
     */
    @NotBlank(groups = {Update.class, Insert.class})
    private String pic;
    /**
     * 状态
     */
    
    private String status;
    /**
     * 排序
     */
    @Min(1)
    @NotNull(groups = {Update.class, Insert.class})
    @TableField("sort_order")
    private Integer sortOrder;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
