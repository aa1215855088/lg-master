package com.lg.product.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lg.commons.util.validators.BeanValidators;
import com.lg.commons.util.validators.Insert;
import com.lg.commons.util.wrapper.WrapMapper;
import com.lg.commons.util.wrapper.Wrapper;
import com.lg.product.mapper.TbGoodsMapper;
import com.lg.product.model.domain.*;
import com.lg.product.model.vo.GoodDto;
import com.lg.product.service.*;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Validator;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Map;

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
    @Autowired
    private TbGoodsDescService tbGoodsDescService;

    @Autowired
    private TbItemService tbItemService;

    @Autowired
    private Validator validator;

    @Autowired
    private TbItemCatService tbItemCatService;
    @Autowired
    private TbBrandService tbBrandService;
    @Autowired
    private TbSellerService tbSellerService;

    @Override
    public Wrapper save(GoodDto goods) {
        goods.getGoods().setAuditStatus("0");
        this.baseMapper.insert(goods.getGoods());
        goods.getGoodsDesc().setGoodsId(goods.getGoods().getId());
        this.tbGoodsDescService.insert(goods.getGoodsDesc());
        for (TbItem item:goods.getTbItemList()){
            //构建标题
            String title=goods.getGoods().getGoodsName();//SPU名称
            Map<String,Object>map=JSON.parseObject(item.getSpec());
            for (String key:map.keySet()){
                title=""+map.get(key);
            }
            item.setTitle(title);
            //商品分类
            item.setCategoryId(goods.getGoods().getCategory3Id());//三级分类
            item.setCreateTime(LocalDateTime.now());//创建日期
            item.setUpdateTime(LocalDateTime.now());//更新日期
            item.setGoodsId(goods.getGoods().getId());//商品ID
            item.setSellerId(goods.getGoods().getSellerId());//商家ID

            //分类名称
            TbItemCat tbItemCat = tbItemCatService.selectOne(new QueryWrapper<TbItemCat>().eq("type_id", goods.getGoods().getCategory3Id()));
            item.setCategory(tbItemCat.getName());
            //品牌名称
            TbBrand brand = tbBrandService.selectOne(new QueryWrapper<TbBrand>().eq("id", goods.getGoods().getBrandId()));
            item.setBrand(brand.getName());
            //商家名称
            TbSeller seller = tbSellerService.selectOne(new QueryWrapper<TbSeller>().eq("seller_id", goods.getGoods().getSellerId()));
            item.setSeller(seller.getName());
            tbItemService.insert(item);

        }
        return WrapMapper.ok(goods);
    }
}
