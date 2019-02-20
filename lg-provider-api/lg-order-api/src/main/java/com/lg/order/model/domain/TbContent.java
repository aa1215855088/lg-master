package com.lg.order.model.domain;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
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
@TableName("tb_content")
public class TbContent extends Model<TbContent> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 内容类目ID
     */
	@TableField("category_id")
	private Long categoryId;
    /**
     * 内容标题
     */
	private String title;
    /**
     * 链接
     */
	private String url;
    /**
     * 图片绝对路径
     */
	private String pic;
    /**
     * 状态
     */
	private String status;
    /**
     * 排序
     */
	@TableField("sort_order")
	private Integer sortOrder;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
