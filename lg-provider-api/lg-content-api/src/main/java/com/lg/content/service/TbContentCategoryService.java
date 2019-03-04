package com.lg.content.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lg.commons.base.vo.PageVO;
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



    //显示
    Wrapper<List<TbContentCategory>> findAll();

    //分页
    Wrapper<PageVO<TbContentCategory>> findPage(Integer pageNum, Integer pageSize);

    //模糊查询 名字
    Wrapper<PageVO<TbContentCategory>> findPage(Integer pageNum, Integer pageSize, TbContentCategory contentCategory);

    //查询 ID
    Wrapper<TbContentCategory> findById(Integer id);

    //新建
    Wrapper save(TbContentCategory contentCategory);

    //修改
    Wrapper update(TbContentCategory contentCategory);

    //删除
    Wrapper delete(Long[] ids);

}
