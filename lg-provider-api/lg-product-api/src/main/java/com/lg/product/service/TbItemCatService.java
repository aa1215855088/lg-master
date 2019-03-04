package com.lg.product.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lg.commons.base.vo.PageVO;
import com.lg.commons.util.wrapper.Wrapper;
import com.lg.product.model.domain.TbItemCat;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 商品类目 服务类
 * </p>
 *
 * @author xuzilou
 * @since 2019-02-19
 */
public interface TbItemCatService extends IService<TbItemCat> {

    Wrapper<List<TbItemCat>> findAll();



    Wrapper<List<TbItemCat>> findByParentId(Long parentId);

    Wrapper<TbItemCat>findOne(Long id);

    public Wrapper<List<TbItemCat>> list(QueryWrapper<TbItemCat> queryWrapper);

    public Wrapper save(TbItemCat tbItemCat);

    public Wrapper<PageVO<TbItemCat>> listPaVo(Integer pageNum, Integer pageSize);

    public Wrapper<TbItemCat> findById(Integer id);

    public Wrapper delTbItem(Long[] ids);

    public Wrapper updateTbItem(TbItemCat tbItemCat);

    public Wrapper<PageVO<TbItemCat>>

    findByPage(Integer pageNum, Integer pageSize);

    Wrapper<List<TbItemCat>> findBySonId(Long[] ids);


}
