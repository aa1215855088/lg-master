package com.lg.content.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lg.commons.util.wrapper.WrapMapper;
import com.lg.commons.util.wrapper.Wrapper;
import com.lg.content.mapper.TbContentCategoryMapper;
import com.lg.content.model.domain.TbContentCategory;
import com.lg.content.service.TbContentCategoryService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 内容分类 服务实现类
 * </p>
 *
 * @author xuzilou
 * @since 2019-02-19
 */
@Service(version = "1.0.0", timeout = 6000)
@Transactional
public class TbContentCategoryServiceImpl extends ServiceImpl<TbContentCategoryMapper, TbContentCategory> implements TbContentCategoryService {
    @Override
    public Wrapper<List<TbContentCategory>> findAll(QueryWrapper<TbContentCategory> queryWrapper) {
        List<TbContentCategory> contentCategoryList = this.baseMapper.selectList(queryWrapper);
        return WrapMapper.ok(contentCategoryList);
    }
}
