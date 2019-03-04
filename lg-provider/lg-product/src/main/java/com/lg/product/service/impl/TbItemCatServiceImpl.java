package com.lg.product.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lg.commons.base.enums.ErrorCodeEnum;
import com.lg.commons.base.exception.BusinessException;
import com.lg.commons.base.vo.PageVO;
import com.lg.commons.util.validators.BeanValidators;
import com.lg.commons.util.validators.Insert;
import com.lg.commons.util.wrapper.WrapMapper;
import com.lg.commons.util.wrapper.Wrapper;
import com.lg.product.mapper.TbItemCatMapper;
import com.lg.product.model.domain.TbItem;
import com.lg.product.model.domain.TbItemCat;
import com.lg.product.service.TbItemCatService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Validator;
import java.util.Arrays;
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


    @Autowired
    private Validator validator;

    @Override
    public Wrapper<List<TbItemCat>> findBySonId(Long[] ids) {
        checkNotNull(ids);
        List<TbItemCat> tbItemCatList=null;
        //List<TbItemCat> tbItemCatList = this.baseMapper.selectBatchIds(Arrays.asList(ids));
        for (int i=0;i<ids.length;i++){
             tbItemCatList= this.baseMapper.selectList(new QueryWrapper<TbItemCat>().eq("parent_id",ids[i]));
        }
       /*if (tbItemCatList==null) {
            throw new BusinessException(ErrorCodeEnum.GL99990500, "没有该商品的子分类");
        }*/
        return WrapMapper.ok(tbItemCatList);
    }

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

    @Override
    public Wrapper<List<TbItemCat>> list(QueryWrapper<TbItemCat> queryWrapper) {
        List<TbItemCat> list= baseMapper.selectList(queryWrapper);
        return WrapMapper.ok(list);
    }

    @Override
    public Wrapper<PageVO<TbItemCat>> findByPage(Integer pageNum, Integer pageSize) {
        if (pageNum == null || pageNum == 0) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize == 0) {
            pageSize = 10;
        }

        IPage<TbItemCat> iPage=this.baseMapper.selectPage(new Page<>(pageNum,pageSize),new QueryWrapper<TbItemCat>());

        PageVO<TbItemCat> pageVO=new PageVO();
        pageVO.setRows(iPage.getRecords());
        pageVO.setTotal(iPage.getTotal());
        return WrapMapper.ok(pageVO);
    }

    @Override
    public Wrapper save(TbItemCat tbItemCat) {
        BeanValidators.validateWithException(validator,tbItemCat, Insert.class);
        Integer index= this.baseMapper.insert(tbItemCat);
        if (index!=1){
            throw new BusinessException(ErrorCodeEnum.GL99990500, "添加商品品牌信息失败");
        }
        return WrapMapper.ok();

    }

    @Override
    public Wrapper<PageVO<TbItemCat>> listPaVo(Integer pageNum, Integer pageSize) {
        if (pageNum==null||pageNum==0){
            //pageNum=TbItemCat
        }
        return null;
    }

    @Override
    public Wrapper<TbItemCat> findById(Integer id) {
        checkNotNull(id);
        TbItemCat tbItemCat=this.baseMapper.selectById(id);
        //baseMapper.selectOne()
        if (tbItemCat==null){
            throw new BusinessException(ErrorCodeEnum.GL99990500, "没有该商品类");
        }
        return  WrapMapper.ok(tbItemCat);
    }

    @Override
    public Wrapper delTbItem(Long[] ids) {
        checkNotNull(ids);
        List<Long> idList = Arrays.asList(ids);
        Integer index = this.baseMapper.deleteBatchIds(idList);
        if (index!=idList.size()){
            throw new BusinessException(ErrorCodeEnum.GL99990500, "删除商品类失败");
        }
        return WrapMapper.ok();
    }


    @Override
    public Wrapper updateTbItem(TbItemCat tbItemCat) {
        BeanValidators.validateWithException(validator,tbItemCat,Insert.class);
        Integer index=this.baseMapper.updateById(tbItemCat);
        if (index!=1){
            throw new BusinessException(ErrorCodeEnum.GL99990500, "更新商品类失败");
        }
        return WrapMapper.ok();

    }


    @Override
    public Wrapper<List<TbItemCat>> findAll() {
        return WrapMapper.ok(this.baseMapper.selectList(new QueryWrapper<TbItemCat>()));
    }
}
