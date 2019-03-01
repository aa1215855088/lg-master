package com.lg.content.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lg.commons.util.wrapper.Wrapper;
import com.lg.content.model.domain.TbContentCategory;

import java.util.List;

/**
 * <p>
 * 内容分类 服务类
 * </p>
 *
 * @author xuzilou
 * @since 2019-02-19
 */
public interface TbContentCategoryService extends IService<TbContentCategory> {
    Wrapper<List<TbContentCategory>> findAll(QueryWrapper<TbContentCategory> queryWrapper);
}
