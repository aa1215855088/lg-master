package com.lg.product.mapper;

import com.lg.product.model.domain.TbItem;
import com.lg.commons.core.mybatis.MyMapper;import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
  * 商品表 Mapper 接口
 * </p>
 *
 * @author xuzilou
 * @since 2019-02-19
 */
@Mapper
public interface TbItemMapper extends MyMapper<TbItem> {

}