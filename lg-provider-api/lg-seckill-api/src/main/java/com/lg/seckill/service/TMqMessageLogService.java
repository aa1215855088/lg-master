package com.lg.seckill.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.lg.seckill.model.domain.TMqMessageLog;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author xuzilou
 * @since 2019-03-08
 */
public interface TMqMessageLogService extends IService<TMqMessageLog> {


    /**
     *
     * @param parseLong
     * @param code
     */
    void changeMessageStatus(long parseLong, int code);

    void updataNextRetryTimeForNow(long parseLong);


    List<TMqMessageLog> getNotProcessingInByType(int code, Object o, int[] ints);

    void updateByPrimaryKeySelective(TMqMessageLog msg);

    int updateTryCount(TMqMessageLog msg);
}
