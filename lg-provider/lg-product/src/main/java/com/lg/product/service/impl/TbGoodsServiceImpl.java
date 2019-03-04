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
import com.lg.product.service.TbGoodsDescService;
import com.lg.product.service.TbGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Validator;
import java.util.Arrays;
import java.util.List;

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
@Transactional
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


    @Autowired
    private TbGoodsDescService tbGoodsDescService;

    @Autowired
    private Validator validator;

    /**
     * 查询所有
     * @param queryWrapper
     * @return
     */

    @Override
    public Wrapper<List<TbGoods>> list(QueryWrapper<TbGoods> queryWrapper) {
        List<TbGoods> list = this.baseMapper.selectList(queryWrapper);
        return WrapMapper.ok(list);
    }

    /**
     * 分页
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public Wrapper<PageVO<TbGoods>> pageList(Integer pageNum,Integer pageSize){
        if(pageNum == 0 || pageNum == null){
            pageNum = 1;
        }
        if(pageSize == 0 || pageSize == null ){
            pageSize = 10;
        }


        IPage<TbGoods> iPage = this.baseMapper.selectPage(new Page<>(pageNum,pageSize),new QueryWrapper<>());
        PageVO<TbGoods> pageVO = new PageVO<TbGoods>();

        pageVO.setRows(iPage.getRecords());
        pageVO.setTotal(iPage.getTotal());
        return WrapMapper.ok(pageVO);
    }

    /**
     * 搜索
     * @param pageNum
     * @param pageSize
     * @param goodsVo
     * @return
     */

    public Wrapper<PageVO<TbGoods>> search(Integer pageNum,Integer pageSize, GoodsVo goodsVo) {
        if (pageNum == null || pageNum == 0) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize == 0) {
            pageSize = 10;
        }
        if(goodsVo == null){
            return pageList(pageNum,pageSize);
        }
       /* if(goodsVo.getAuditStatus().isEmpty()){
            goodsVo.setAuditStatus("0");
        }*/

        IPage<TbGoods> iPage = this.baseMapper.selectPage(
                new Page<>(pageNum, pageSize),
                    new QueryWrapper<TbGoods>().like(StrUtil.isNotBlank(goodsVo.getAuditStatus()),
                        "audit_status",goodsVo.getAuditStatus()).
                        eq(StrUtil.isNotBlank(goodsVo.getGoodsName()),
                                "goods_name",goodsVo.getGoodsName()));
        PageVO<TbGoods> pageVO = new PageVO<TbGoods>();


        pageVO.setRows(iPage.getRecords());
        pageVO.setTotal(iPage.getTotal());
        return WrapMapper.ok(pageVO);
    }

    /**
     * 删除商品
     * @param ids
     * @return
     */
    @Override
    public Wrapper delete(Long[] ids) {

       /* checkNotNull(ids);*/
        Integer index = this.baseMapper.deleteBatchIds(Arrays.asList(ids));
        if (index != ids.length) {
            throw new BusinessException(ErrorCodeEnum.GL99990500, "删除商品品牌信息失败");
        }
        return WrapMapper.ok(index);
    }

    /**
     * 查询实体
     * @param id
     * @return
     */
    @Override
    public Wrapper<TbGoods> findOne(Long id) {
        TbGoods tbGoods = this.baseMapper.selectById(id);
        if (null == tbGoods) {
            throw new BusinessException(ErrorCodeEnum.GL99990500, "没有该商品信息");
        }
        return WrapMapper.ok(tbGoods);
    }

    /**
     * 修改商品
     * @param tbGoods
     * @return
     */
    @Override
    public Wrapper update(TbGoods tbGoods) {
        Integer index = this.baseMapper.updateById(tbGoods);
        if (index != 1) {
            throw new BusinessException(ErrorCodeEnum.GL99990500, "更新商品品牌信息失败");
        }
        return WrapMapper.ok();
    }

    /**
     * 屏蔽
     * @param ids
     * @return
     */
    @Override
    public Wrapper shield(Long[] ids) {
        Integer index = 0;
        for(int i=0;i<=ids.length;i++){
            System.out.println("ids =---= "+ids[0]);
            TbGoods tbGoods = this.baseMapper.selectById(ids[0]);
            tbGoods.setAuditStatus("4");
            System.out.println("vo =---= "+tbGoods);
            index = this.baseMapper.updateById(tbGoods);
            System.out.println("AAAA---"+index);
        }
        if (index != 1) {
            throw new BusinessException(ErrorCodeEnum.GL99990500, "屏蔽商品信息失败");
        }
        return WrapMapper.ok();
    }


}
