package com.lg.product.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lg.commons.base.enums.ErrorCodeEnum;
import com.lg.commons.base.exception.BusinessException;
import com.lg.commons.util.wrapper.WrapMapper;
import com.lg.commons.util.wrapper.Wrapper;
import com.lg.product.mapper.TbItemCatMapper;
import com.lg.product.model.domain.TbItem;
import com.lg.product.model.domain.TbItemCat;
import com.lg.product.service.TbItemCatService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * <p>
 * 商品类目 服务实现类
 * </p>
 *
 * @author xuzilou
 * @since 2019-02-19
 */
@Service(version = "1.0.0", timeout = 6000)
@Transactional
public class TbItemCatServiceImpl extends ServiceImpl<TbItemCatMapper, TbItemCat> implements TbItemCatService {


    @Override
    public Wrapper<List<TbItemCat>> findByParentId(Long parentId) {
        checkNotNull(parentId);
        List<TbItemCat> parentId1List = this.baseMapper.selectList(new QueryWrapper<TbItemCat>().eq("parent_id", parentId));
        if (parentId1List==null) {
            throw new BusinessException(ErrorCodeEnum.GL99990500, "没有该商品分类");
        }
        return WrapMapper.ok(parentId1List);
    }

    @Override
    public Wrapper<TbItemCat> findOne(Long id) {
        checkNotNull(id);
        TbItemCat tbItemCattypeID = this.baseMapper.selectOne(new QueryWrapper<TbItemCat>().eq("id",id));
        if (tbItemCattypeID==null){
            throw  new  BusinessException(ErrorCodeEnum.GL99990500,"没有查询到模板ID");
        }
        return WrapMapper.ok(tbItemCattypeID);
    }
}
