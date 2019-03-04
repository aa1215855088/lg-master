package com.lg.product.service.impl;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lg.commons.base.vo.PageVO;
import com.lg.commons.util.wrapper.WrapMapper;
import com.lg.commons.util.wrapper.Wrapper;
import com.lg.product.mapper.TbGoodsMapper;
import com.lg.product.model.domain.TbGoods;
import com.lg.product.service.TbGoodsService;
import com.alibaba.dubbo.config.annotation.Service;

import java.util.List;

import static com.alibaba.nacos.client.config.impl.CacheData.log;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xuzilou
 * @since 2019-02-19
 */
@Service(version = "1.0.0",timeout = 6000)
public class TbGoodsServiceImpl extends ServiceImpl<TbGoodsMapper, TbGoods> implements TbGoodsService {

    @Override
    public Wrapper<List<TbGoods>> findAll() {


        return WrapMapper.ok(this.baseMapper.selectList(new QueryWrapper<TbGoods>()));
    }

    @Override
    public Wrapper<PageVO<TbGoods>> findPage(Integer page, Integer rows) {


        if(page == null || page == 0){
            page = 1;
        }

        if(rows == null || rows == 0){
            rows = 10;
        }

        IPage<TbGoods> iPage = this.baseMapper.selectPage(new Page<TbGoods>(page,rows),new QueryWrapper<TbGoods>());

        PageVO<TbGoods> pageVO = new PageVO<>();
        pageVO.setRows(iPage.getRecords());
        pageVO.setTotal(iPage.getTotal());

        return WrapMapper.ok(pageVO);
    }

    @Override
    public Wrapper updateStatus(Long[] ids, String status) {

        for (Long id : ids) {

            TbGoods tbGoods = baseMapper.selectById(id);

            tbGoods.setAuditStatus(status);

            baseMapper.updateById(tbGoods);
        }

        return WrapMapper.ok();
    }

    @Override
    public Wrapper deleteGoods(Long[] ids) {

        for (Long id : ids) {
            baseMapper.deleteById(id);
        }

        return WrapMapper.ok();
    }

    @Override
    public Wrapper<PageVO<TbGoods>> findPageAndName(String name, Integer page, Integer rows) {

        if(page == null || page == 0){
            page = 1;
        }

        if(rows == null || rows == 0){
            rows = 10;
        }
        IPage<TbGoods> iPage = this.baseMapper.selectPage(new Page<TbGoods>(page,rows),new QueryWrapper<TbGoods>()
                .like(StrUtil.isNotBlank(name),"goods_name",name));

        PageVO<TbGoods> pageVO = new PageVO<>();
        pageVO.setRows(iPage.getRecords());
        pageVO.setTotal(iPage.getTotal());

        return WrapMapper.ok(pageVO);

    }


}
