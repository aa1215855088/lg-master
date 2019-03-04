package com.lg.product.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lg.commons.base.vo.GoodsVo;
import com.lg.commons.base.vo.PageVO;
import com.lg.commons.util.wrapper.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lg.commons.base.vo.PageVO;
import com.lg.commons.util.wrapper.WrapMapper;
import com.lg.commons.util.wrapper.Wrapper;
import com.lg.product.model.domain.TbGoods;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lg.product.model.domain.TbItemCat;


import java.io.Serializable;
import java.util.List;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xuzilou
 * @since 2019-02-19
 */
public interface TbGoodsService extends IService<TbGoods> {

   /* Wrapper save(Goods goods);*/
    /**
     * 查询所有
     * @param queryWrapper
     * @return
     */
    Wrapper<List<TbGoods>> list(QueryWrapper<TbGoods> queryWrapper);


    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @return
     */
    Wrapper<PageVO<TbGoods>> pageList(Integer pageNum,Integer pageSize);

    /**
     * 搜索
     * @param pageNum
     * @param pageSize
     * @param goodsVo
     * @return
     */
    Wrapper<PageVO<TbGoods>> search(Integer pageNum, Integer pageSize, GoodsVo goodsVo);


    /**
     * 删除
     * @param ids
     * @return
     */
    Wrapper delete(Long[] ids);


    /**
     * 查询实体
     * @param id
     * @return
     */
    Wrapper<TbGoods> findOne(Long id);

    /**
     * 修改
     * @param tbGoods
     * @return
     */
    Wrapper update(TbGoods tbGoods);

    /**
     * 屏蔽
     */
    Wrapper shield(Long[] ids);


public interface TbGoodsService {

    public Wrapper<List<TbGoods>> findAll();

    public Wrapper<PageVO<TbGoods>> findPage(Integer page, Integer rows);

    public Wrapper updateStatus(Long[] ids, String status);

    public Wrapper deleteGoods(Long[] ids);

    public Wrapper<PageVO<TbGoods>> findPageAndName(String name, Integer page, Integer rows);
}
