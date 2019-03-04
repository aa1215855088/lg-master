package com.lg.product.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lg.commons.base.vo.GoodsVo;
import com.lg.commons.base.vo.PageVO;
import com.lg.commons.util.wrapper.Wrapper;
import com.lg.product.model.domain.TbGoods;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lg.product.model.domain.TbItemCat;
import com.lg.product.model.dto.GoodDTD;
import com.lg.product.model.dto.Goods;
import com.lg.product.model.vo.GoodsVO;


import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author xuzilou
 * @since 2019-02-19
 */
public interface TbGoodsService extends IService<TbGoods> {

    /* Wrapper save(Goods goods);*/

    /**
     * 查询所有
     *
     * @param queryWrapper
     * @return
     */
    Wrapper<List<TbGoods>> list(QueryWrapper<TbGoods> queryWrapper);


    /**
     * 分页查询
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    Wrapper<PageVO<TbGoods>> pageList(Integer pageNum, Integer pageSize);

    /**
     * 搜索
     *
     * @param pageNum
     * @param pageSize
     * @param goodsVo
     * @return\
     *
     */
    Wrapper<PageVO<Goods>> search(Integer pageNum, Integer pageSize, GoodsVO goodsVo);


    /**
     * 删除
     *
     * @param ids
     * @return
     */
    Wrapper delete(Long[] ids);


    /**
     * 查询实体
     *
     * @param id
     * @return
     */
    Wrapper<TbGoods> findOne(Long id);

    /**
     * 修改
     *
     * @param tbGoods
     * @return
     */
    Wrapper update(TbGoods tbGoods);

    /**
     * 屏蔽
     */
    Wrapper shield(Long[] ids);


    /**
     * 添加商品
     *
     * @param goods
     * @return
     */
    Wrapper save(GoodDTD goods);

    /**
     * 查询所有
     * @return
     */
    List<Goods> findAll();

    /**
     * 提交审核
     * @param ids
     * @return
     */
    Wrapper submitAudit(Long[] ids);
}
