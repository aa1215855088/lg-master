package com.lg.product.mapper;

import com.lg.product.model.domain.TMqMessageLog;
import com.lg.commons.core.mybatis.MyMapper;import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author xuzilou
 * @since 2019-03-08
 */
@Mapper
public interface TMqMessageLogMapper extends MyMapper<TMqMessageLog> {

    /**
     * 修改消息表状态
     * @param messageId
     * @param status
     */
    void changeMessageStatus(@Param("messageId") long messageId, @Param("status") int status);

    /**
     *
     * @param messageId
     */
    void updataNextRetryTimeForNow(Long messageId);


    void updateByPrimaryKeySelective(TMqMessageLog msg);

    int updateTryCount(TMqMessageLog msg);
}