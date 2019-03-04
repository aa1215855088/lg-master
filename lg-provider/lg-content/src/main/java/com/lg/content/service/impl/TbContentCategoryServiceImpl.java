package com.lg.content.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.dubbo.config.annotation.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lg.commons.base.enums.ErrorCodeEnum;
import com.lg.commons.base.exception.BusinessException;
import com.lg.commons.base.vo.PageVO;
import com.lg.commons.util.validators.BeanValidators;
import com.lg.commons.util.validators.Update;
import com.lg.commons.util.wrapper.WrapMapper;
import com.lg.commons.util.wrapper.Wrapper;
import com.lg.content.mapper.TbContentCategoryMapper;
import com.lg.content.model.domain.TbContentCategory;
import com.lg.content.service.TbContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Validator;
import java.util.Arrays;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * <p>
 * 内容分类 服务实现类
 * </p>
 *
 * @author xuzilou
 * @since 2019-02-19
 */
@Service(version = "1.0.0",timeout = 6000) //让dubbo发现并注册服务 设置版本号 方便管理 设置超时时间
@Transactional //spring声明式事务
public class TbContentCategoryServiceImpl extends ServiceImpl<TbContentCategoryMapper, TbContentCategory> implements TbContentCategoryService {

    @Autowired
    private Validator validator;


    //显示
    @Override
    public Wrapper<List<TbContentCategory>> findAll() {
        List<TbContentCategory> tbContentCategories = baseMapper.selectList(new QueryWrapper<TbContentCategory>());
        return WrapMapper.ok(tbContentCategories);
    }


    //分页
    @Override
    public Wrapper<PageVO<TbContentCategory>> findPage(Integer pageNum, Integer pageSize) {
        if (pageNum == null || pageNum == 0){
            pageNum = 1;
        }
        if (pageSize == null || pageSize == 0){
            pageSize = 10;
        }
        IPage<TbContentCategory> contentCategoryIPage = this.baseMapper.selectPage(new Page<>(pageNum, pageSize),new QueryWrapper<TbContentCategory>());
        PageVO<TbContentCategory> pageVO = new PageVO<>();
        pageVO.setRows(contentCategoryIPage.getRecords());
        pageVO.setTotal(contentCategoryIPage.getTotal());
        return WrapMapper.ok(pageVO);
    }


    //模糊查询
    @Override
    public Wrapper<PageVO<TbContentCategory>> findPage(Integer pageNum, Integer pageSize, TbContentCategory contentCategory) {
        if (pageNum == null || pageNum == 0){
            pageNum = 1;
        }
        if (pageSize == null || pageSize == 0){
            pageSize = 10;
        }
        if (contentCategory == null){
            return findPage(pageNum, pageSize);
        }
        IPage<TbContentCategory> categoryIPage= this.baseMapper.selectPage(
                new Page<>(pageNum, pageSize),
                new QueryWrapper<TbContentCategory>().like(StrUtil.isNotBlank(contentCategory.getName()), "name", contentCategory.getName()));
        PageVO<TbContentCategory> pageVO = new PageVO<>();
        pageVO.setRows(categoryIPage.getRecords());
        pageVO.setTotal(categoryIPage.getTotal());
        return WrapMapper.ok(pageVO);
    }


    //ID查询
    @Override
    public Wrapper<TbContentCategory> findById(Integer id) {
        checkNotNull(id);
        TbContentCategory tbContentCategory = this.baseMapper.selectById(id);
        if (null ==  tbContentCategory){
            throw new BusinessException(ErrorCodeEnum.GL99990500, "没有该广告分类信息");
        }
        return WrapMapper.ok(tbContentCategory);
    }


    //新建
    @Override
    public Wrapper save(TbContentCategory contentCategory) {
        BeanValidators.validateWithException(validator, contentCategory);
        Integer index = this.baseMapper.insert(contentCategory);
        if (index != 1){
            throw new BusinessException(ErrorCodeEnum.GL99990500, "添加广告分类信息失败");
        }
        return WrapMapper.ok(contentCategory);
    }


    //修改
    @Override
    public Wrapper update(TbContentCategory contentCategory) {
        BeanValidators.validateWithException(validator, contentCategory, Update.class);
        Integer index = this.baseMapper.updateById(contentCategory);
        if (index != 1){
            throw new BusinessException(ErrorCodeEnum.GL99990500, "更新广告分类信息失败");
        }
        return WrapMapper.ok(contentCategory);
    }


    //删除
    @Override
    public Wrapper delete(Long[] ids) {
        checkNotNull(ids);
        List<Long> idlist=Arrays.asList(ids);
        Integer index = this.baseMapper.deleteBatchIds(idlist);
        if (index != idlist.size()){
            throw new BusinessException(ErrorCodeEnum.GL99990500, "删除广告分类信息失败");
        }
        return  WrapMapper.ok(ids);
    }


}
