package com.lg.content.service.impl;


import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lg.commons.base.enums.ErrorCodeEnum;
import com.lg.commons.base.exception.BusinessException;
import com.lg.commons.base.vo.PageVO;
import com.lg.commons.util.validators.BeanValidators;
import com.lg.commons.util.validators.Insert;
import com.lg.commons.util.validators.Update;
import com.lg.commons.util.wrapper.WrapMapper;
import com.lg.commons.util.wrapper.Wrapper;
import com.lg.content.mapper.TbContentMapper;
import com.lg.content.model.domain.TbContent;
import com.lg.content.model.vo.TbContentVO;
import com.lg.content.service.TbContentService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Validator;
import java.util.Arrays;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

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
public class TbContentServiceImpl extends ServiceImpl<TbContentMapper, TbContent> implements TbContentService {

    @Autowired
    private Validator validator;

    @Autowired
    private RedisTemplate   redisTemplate;


    @Override
    public Wrapper<List<TbContent>> findAll() {
        List<TbContent> contentList = this.baseMapper.selectList(new QueryWrapper<>());
        return WrapMapper.ok(contentList);
    }

    @Override
    public Wrapper save(TbContent tbContent) {
        BeanValidators.validateWithException(validator, tbContent,Insert.class);
        
        if (tbContent.getStatus().equals("true")) {
            tbContent.setStatus("1");
        } else {
            tbContent.setStatus("0");
        }
        Integer index = this.baseMapper.insert(tbContent);
        redisTemplate.boundHashOps("content").delete(tbContent.getCategoryId());

        if (index != 1) {
            throw new BusinessException(ErrorCodeEnum.GL99990500, "添加广告信息失败");
        }
        return WrapMapper.ok(tbContent);
    }

    @Override
    public Wrapper delByIds(Long[] ids) {
        checkNotNull(ids);
        List<Long> longList = Arrays.asList(ids);
        for (Long id : longList) {
            Long categoryId = baseMapper.selectById(id).getCategoryId();
            redisTemplate.boundHashOps("content").delete(categoryId);
        }

        Integer index = this.baseMapper.deleteBatchIds(longList);
        if (index != ids.length) {
            throw new BusinessException(ErrorCodeEnum.GL99990500, "删除广告信息失败");
        }
        return WrapMapper.ok();
    }

    @Override
    public Wrapper updateTbCentent(TbContent tbContent) {
        if (tbContent.getStatus().equals("true")) {
            tbContent.setStatus("1");
        } else {
            tbContent.setStatus("0");
        }
        BeanValidators.validateWithException(validator, tbContent, Update.class);
        Long categoryId=baseMapper.selectById(tbContent.getId()).getCategoryId();
        redisTemplate.boundHashOps("content").delete(categoryId);

        Integer index = this.baseMapper.updateById(tbContent);
        if (categoryId.longValue()!=tbContent.getCategoryId().longValue()){
            redisTemplate.boundHashOps("content").delete(tbContent.getCategoryId());
        }

        if (index != 1) {
            throw new BusinessException(ErrorCodeEnum.GL99990500, "更新广告信息失败");
        }
        return WrapMapper.ok(tbContent);
    }

    @Override
    public Wrapper<TbContent> findById(Long id) {
        checkNotNull(id);
        TbContent tbContent = this.baseMapper.selectById(id);
        if (tbContent == null) {
            throw new BusinessException(ErrorCodeEnum.GL99990500, "没有该广告的任何信息");
        }
        return WrapMapper.ok(tbContent);
    }

    @Override
    public Wrapper<List<TbContent>> findByCategoryId(Long categoryId) {
        List<TbContent> list= (List<TbContent>) redisTemplate.boundHashOps("content").get(categoryId);
        if (list==null){
            System.out.println("从数据库中查新并放到缓存");
            list = this.baseMapper.selectList(new QueryWrapper<TbContent>().eq("category_id", categoryId)
                    .eq("status","1"));
            redisTemplate.boundHashOps("content").put(categoryId,list);
        }else {
            System.out.println("从缓存中查询数据");
        }
        return WrapMapper.ok(list);
    }

}
