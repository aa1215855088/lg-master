package com.lg.product.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lg.commons.base.vo.PageVO;
import com.lg.commons.util.wrapper.Wrapper;
import com.lg.product.model.domain.TbItemCat;
import com.lg.product.model.domain.TbTypeTemplate;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xuzilou
 * @since 2019-02-19
 */
public interface TbTypeTemplateService extends IService<TbTypeTemplate> {


    Wrapper<TbTypeTemplate> findOne(Long id);

    Wrapper<List<Map>>findSpecList(Long id);

    //查询数据所有的数据
    Wrapper<List<TbTypeTemplate>> list(QueryWrapper<TbTypeTemplate> queryWrapper);

    Wrapper<PageVO<TbTypeTemplate>>  findByPage(Integer pageNum, Integer pageSize);

    //增加数据
    Wrapper InsertTypeTemplate(TbTypeTemplate tbTypeTemplate);


    //删除数据
    Wrapper deleteByIds(Long[] ids);


    //修改数据
    Wrapper updateTypeTemplate(TbTypeTemplate tbTypeTemplate);

    //根据模板名称查找
    Wrapper<PageVO<TbTypeTemplate>> findByName(String name, Integer pageNum, Integer pageSize);
	
}
