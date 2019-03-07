package com.lg.product.mapper;

import com.lg.product.model.domain.TbBrand;
import com.lg.commons.core.mybatis.MyMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author xuzilou
 * @since 2019-02-19
 */
@Mapper
public interface TbBrandMapper extends MyMapper<TbBrand> {


        List<Map> selectOptionList();


}