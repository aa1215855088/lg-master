package com.lg.user.mapper;

import com.lg.user.model.domain.TbUser;
import com.lg.commons.core.mybatis.MyMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
  * 用户表 Mapper 接口
 * </p>
 *
 * @author xuzilou
 * @since 2019-02-19
 */
@Mapper
public interface TbUserMapper extends MyMapper<TbUser> {

}