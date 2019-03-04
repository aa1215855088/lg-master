package com.lg.biz.mapper;


import com.lg.biz.model.domain.TbSeller;
import com.lg.commons.core.mybatis.MyMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author xuzilou
 * @since 2019-02-19
 */
@Mapper
public interface TbSellerMapper extends MyMapper<TbSeller> {

    /**
     * 商家入驻
     * @return
     */
    Integer insert(TbSeller tbSeller);

    /**
     * 修改密码
     * @return
     */
    Integer  updatePassword(TbSeller tbSeller);

}