package com.lg.product.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lg.commons.base.vo.PageVO;
import com.lg.commons.base.vo.Result;
import com.lg.commons.util.wrapper.Wrapper;
import com.lg.product.model.domain.TbBrand;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lg.product.vo.BrandVo;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author xuzilou
 * @since 2019-02-19
 */
public interface TbBrandService extends IService<TbBrand> {


    /**
     * 查询商品列表
     * @param queryWrapper
     * @return
     */
    public Wrapper<List<TbBrand>> list(QueryWrapper<TbBrand> queryWrapper);

    /**
     * 添加商品品牌
     *
     * @param brand
     * @return
     */
    public Wrapper save(TbBrand brand);

    /**
     * 按分页查询
     */
    public Wrapper<PageVO<TbBrand>> listPaVo(Integer pageNum, Integer pageSize);

    /**
     * 查找分页显示品牌列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    public Wrapper<PageVO<TbBrand>> findPage(Integer pageNum, Integer pageSize, BrandVo brandVo);

    /**
     * 按编号查询品牌信息
     * @param id
     * @return
     */
    public Wrapper<TbBrand> findById(Integer id);

    /**
     * 删除品牌
     * @param ids
     */
    public Wrapper delByBrand(Long[] ids);
    /**
     *  修改品牌信息
     * @param tbBrand
     */
    public Wrapper updateTbBrand(TbBrand tbBrand);


}
