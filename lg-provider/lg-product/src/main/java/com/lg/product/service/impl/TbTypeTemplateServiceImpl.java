package com.lg.product.service.impl;


import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lg.commons.base.enums.ErrorCodeEnum;
import com.lg.commons.base.exception.BusinessException;
import com.lg.commons.base.vo.PageVO;
import com.lg.commons.util.wrapper.WrapMapper;
import com.lg.commons.util.wrapper.Wrapper;
import com.lg.product.mapper.TbSpecificationMapper;
import com.lg.product.mapper.TbTypeTemplateMapper;
import com.lg.product.model.domain.TbItemCat;
import com.lg.product.model.domain.TbSpecificationOption;
import com.lg.product.model.domain.TbTypeTemplate;
import com.lg.product.service.TbSpecificationOptionService;
import com.lg.product.service.TbTypeTemplateService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Validator;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xuzilou
 * @since 2019-02-19
 */
@Service(version = "1.0.0", timeout = 6000)
@Transactional
public class TbTypeTemplateServiceImpl extends ServiceImpl<TbTypeTemplateMapper, TbTypeTemplate> implements TbTypeTemplateService {


    @Autowired
    private TbSpecificationOptionService tbSpecificationOptionService;


    @Autowired
    private Validator validator;


    @Override
    public Wrapper<TbTypeTemplate> findOne(Long id) {
        checkNotNull(id);
        TbTypeTemplate tbTypeTemplate = this.baseMapper.selectOne(new QueryWrapper<TbTypeTemplate>().eq("id", id));
        if (tbTypeTemplate==null){
            throw  new BusinessException(ErrorCodeEnum.GL99990500,"没有查询到商品品牌信息");
        }
        return WrapMapper.ok(tbTypeTemplate);
    }



    @Override
    public Wrapper<List<Map>> findSpecList(Long id) {

        checkNotNull(id);
        TbTypeTemplate tbTypeTemplate = this.baseMapper.selectOne(new QueryWrapper<TbTypeTemplate>().eq("id", id));
        if (tbTypeTemplate==null){
            throw  new BusinessException(ErrorCodeEnum.GL99990500,"没有查询到规格信息");
        }
        String specIds=tbTypeTemplate.getSpecIds();

        List<Map>list=JSON.parseArray(specIds,Map.class);

        for (Map map : list) {
            //查询规格选项列表
            List<TbSpecificationOption> tbSpecificationOptionList = this.tbSpecificationOptionService.selectList(new QueryWrapper<TbSpecificationOption>().eq("spec_id",new Long((Integer)map.get("id"))));

            map.put("options",tbSpecificationOptionList);
        }

        return WrapMapper.ok(list);
    }



    @Override
    public Wrapper<List<TbTypeTemplate>> list(QueryWrapper<TbTypeTemplate> queryWrapper) {
        List<TbTypeTemplate> list = this.baseMapper.selectList(queryWrapper);
//        PageVO<TbTypeTemplate> pageVO=new PageVO();
//        pageVO.setRows(list);
        return WrapMapper.ok(list);
    }

    @Override
    public Wrapper<PageVO<TbTypeTemplate>> findByPage(Integer pageNum, Integer pageSize) {
        if (pageNum == null || pageNum == 0) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize == 0) {
            pageSize = 10;
        }

        IPage<TbTypeTemplate> iPage = this.baseMapper.selectPage(new Page<>(pageNum, pageSize), new QueryWrapper<>());

        PageVO<TbTypeTemplate> pageVO = new PageVO();
        pageVO.setRows(iPage.getRecords());
        pageVO.setTotal(iPage.getTotal());
        return WrapMapper.ok(pageVO);
    }

    @Override
    public Wrapper InsertTypeTemplate(TbTypeTemplate tbTypeTemplate) {
        // BeanValidators.validateWithException(validator,tbItemCat,Insert.class);
        Integer index = this.baseMapper.insert(tbTypeTemplate);
        if (index != 1) {
            throw new BusinessException(ErrorCodeEnum.GL99990500, "添加商品类型失败");
        }
        return WrapMapper.ok();
    }

    @Override
    public Wrapper deleteByIds(Long[] ids) {
        checkNotNull(ids);
        List<Long> idList = Arrays.asList(ids);
        Integer index = this.baseMapper.deleteBatchIds(idList);
        if (index != idList.size()) {
            throw new BusinessException(ErrorCodeEnum.GL99990500, "删除商品类型失败");
        }
        return WrapMapper.ok();
    }

    @Override
    public Wrapper updateTypeTemplate(TbTypeTemplate tbTypeTemplate) {
        //BeanValidators.validateWithException(validator,tbItemCat,Insert.class);
        Integer index = this.baseMapper.updateById(tbTypeTemplate);
        if (index != 1) {
            throw new BusinessException(ErrorCodeEnum.GL99990500, "更新商品类失败");
        }
        return WrapMapper.ok();

    }

    @Override
    public Wrapper<PageVO<TbTypeTemplate>> findByName(String name, Integer pageNum, Integer pageSize) {
        if (pageNum == null || pageNum == 0) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize == 0) {
            pageSize = 10;
        }

        IPage<TbTypeTemplate> iPage = this.baseMapper.selectPage(
                new Page<>(pageNum, pageSize),
                new QueryWrapper<TbTypeTemplate>().like(StrUtil.isNotBlank(name), "name", name));

        PageVO<TbTypeTemplate> pageVO = new PageVO();
        pageVO.setRows(iPage.getRecords());
        pageVO.setTotal(iPage.getTotal());
        return WrapMapper.ok(pageVO);
    }
}
