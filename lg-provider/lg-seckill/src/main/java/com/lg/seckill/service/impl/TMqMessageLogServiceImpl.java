package com.lg.seckill.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lg.seckill.mapper.TMqMessageLogMapper;
import com.lg.seckill.model.domain.TMqMessageLog;
import com.lg.seckill.service.TMqMessageLogService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author xuzilou
 * @since 2019-03-08
 */
@Service
public class TMqMessageLogServiceImpl extends ServiceImpl<TMqMessageLogMapper, TMqMessageLog> implements TMqMessageLogService {

    @Override
    public void changeMessageStatus(long parseLong, int code) {
        this.baseMapper.changeMessageStatus(parseLong, code);
    }

    @Override
    public void updataNextRetryTimeForNow(long parseLong) {
        this.baseMapper.updataNextRetryTimeForNow(parseLong);
    }

    @Override
    public List<TMqMessageLog> getNotProcessingInByType(int code, Object o, int[] ints) {
        List<TMqMessageLog> messageLogs = this.baseMapper.selectList(new QueryWrapper<TMqMessageLog>()
                .eq("type", code)
                .in("status", ints)
                .ge("next_retry", LocalDateTime.now()));
        return messageLogs;
    }

    @Override
    public void updateByPrimaryKeySelective(TMqMessageLog msg) {
        this.baseMapper.updateByPrimaryKeySelective(msg);
    }

    @Override
    public int updateTryCount(TMqMessageLog msg) {
        return this.baseMapper.updateTryCount(msg);
    }
}
