package com.lg.product.service.impl;


import cn.hutool.core.util.StrUtil;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageHelper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lg.commons.base.enums.ErrorCodeEnum;
import com.lg.commons.base.exception.BusinessException;
import com.lg.commons.base.vo.PageVO;
import com.lg.commons.base.vo.Result;
import com.lg.commons.util.validators.BeanValidators;
import com.lg.commons.util.validators.Insert;
import com.lg.commons.util.validators.Update;
import com.lg.commons.util.wrapper.WrapMapper;
import com.lg.commons.util.wrapper.Wrapper;
import com.lg.product.mapper.TbBrandMapper;
import com.lg.product.model.domain.TbBrand;
import com.lg.product.service.TbBrandService;
import com.lg.product.vo.BrandVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Validator;

import static com.google.common.base.Preconditions.checkNotNull;


import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author xuzilou
 * @since 2019-02-19
 */
@Service(version = "1.0.0", timeout = 6000)
@Transactional
public class TbBrandServiceImpl extends ServiceImpl<TbBrandMapper, TbBrand> implements TbBrandService {


    @Autowired
    private Validator validator;

    /**
     * 查询品牌列表
     * @param queryWrapper
     * @return
     */
    @Override
    public Wrapper<List<TbBrand>>  list(QueryWrapper<TbBrand> queryWrapper) {
        List<TbBrand> list = baseMapper.selectList(queryWrapper);
        return WrapMapper.ok(list);
    }

    /**
     * 添加品牌
     * @param brand
     * @return
     */
    @Override
    public Wrapper save(TbBrand brand) {
       BeanValidators.validateWithException(validator, brand, Insert.class);
       Integer num = this.baseMapper.insert(brand);
       if (num != 1){
           throw new BusinessException(ErrorCodeEnum.GL99990500, "添加商品品牌信息失败");
       }
        return WrapMapper.ok();
    }

    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @return
     */
    public Wrapper< PageVO<TbBrand>> listPaVo(Integer pageNum, Integer pageSize) {
        if (pageNum == null || pageNum == 0) {
            pageNum = TbBrand.PAGE_NUM;
        }
        if (pageSize == null || pageSize == 0) {
            pageSize = TbBrand.PAGE_SIZE;
        }
        //PageHelper.startPage(pageNum, pageSize);
        IPage<TbBrand> iPage = this.baseMapper.selectPage(new Page<>(pageNum,pageSize),new QueryWrapper<TbBrand>());
        PageVO<TbBrand> pageVO = new PageVO<>();
        pageVO.setRows(iPage.getRecords());
        pageVO.setTotal(iPage.getTotal());
        return WrapMapper.ok(pageVO);
    }


    /**
     *  按条件 分页 查询品牌
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public Wrapper<PageVO<TbBrand>> findPage(Integer pageNum, Integer pageSize, BrandVo brandVo) {
        if (pageNum == null || pageNum == 0) {
            pageNum = TbBrand.PAGE_NUM;
        }
        if (pageSize == null || pageSize == 0) {
            pageSize = TbBrand.PAGE_SIZE;
        }
        if(brandVo==null){
            return listPaVo(pageNum,pageSize);
        }
        IPage<TbBrand> brandIPage = this.baseMapper.selectPage(
                new Page<>(pageNum, pageSize),
                new QueryWrapper<TbBrand>().like(StrUtil.isNotBlank(brandVo.getName()), "name",brandVo.getName()).
                        eq(StrUtil.isNotBlank(brandVo.getFirstChar()),
                        "first_char",brandVo.getFirstChar()));
        PageVO<TbBrand> pageVO = new PageVO<>();
        pageVO.setRows(brandIPage.getRecords());
        pageVO.setTotal(brandIPage.getTotal());
        return WrapMapper.ok(pageVO);
}

    /**
     * 删除品牌
     * @param ids
     * @return
     */
    @Override
    public Wrapper delByBrand(Long[] ids) {
        checkNotNull(ids);
        Integer index = this.baseMapper.deleteBatchIds(Arrays.asList(ids));
        if (index != ids.length) {
            throw new BusinessException(ErrorCodeEnum.GL99990500, "删除商品品牌信息失败");
        }
        return WrapMapper.ok();
    }

    /**
     * 按编号查询品牌
     * @param id
     * @return
     */
    @Override
    public Wrapper<TbBrand>  findById(Integer id) {
        checkNotNull(id);
        TbBrand tbBrand = this.baseMapper.selectById(id);
        if (null == tbBrand) {
            throw new BusinessException(ErrorCodeEnum.GL99990500, "没有该商品品牌信息");
        }
        return WrapMapper.ok(tbBrand);
    }

    /**
     * 修改品牌信息
     * @param tbBrand
     * @return
     */
    @Override
    public Wrapper updateTbBrand(TbBrand tbBrand) {
        BeanValidators.validateWithException(validator, tbBrand, Update.class);
        Integer index = this.baseMapper.updateById(tbBrand);
        if (index != 1) {
            throw new BusinessException(ErrorCodeEnum.GL99990500, "更新商品品牌信息失败");
        }
        return WrapMapper.ok();
    }

    @Override
    public List<Map> selectOptionList() {
        return this.baseMapper.selectOptionList();
    }

}
