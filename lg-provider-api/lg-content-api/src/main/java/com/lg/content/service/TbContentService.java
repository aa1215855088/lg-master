package com.lg.content.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lg.commons.base.vo.PageVO;
import com.lg.commons.util.wrapper.Wrapper;
import com.lg.content.model.domain.TbContent;
import com.lg.content.model.vo.TbContentVO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author xuzilou
 * @since 2019-02-19
 */
public interface TbContentService extends IService<TbContent> {
    Wrapper<List<TbContent>> findAll();

    Wrapper save(TbContent tbContent);

    Wrapper delByIds(Long[] ids);

    Wrapper updateTbCentent(TbContent tbContent);

    Wrapper<TbContent> findById(Long id);


}
