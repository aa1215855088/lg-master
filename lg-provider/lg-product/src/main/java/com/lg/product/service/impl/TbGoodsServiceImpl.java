package com.lg.product.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
import java.time.LocalDateTime;
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
@Service(version = "1.0.0")
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


    /*@Override
    public Wrapper save(GoodDto goods) {
        TbItem tbItem = new TbItem();
        goods.getGoods().setAuditStatus("0");
        this.baseMapper.insert(goods.getGoods());
        goods.getGoodsDesc().setGoodsId(goods.getGoods().getId());
        this.tbGoodsDescService.insert(goods.getGoodsDesc());
        tbItem.setTitle(goods.getGoods().getGoodsName());
        tbItem.setSpec(goods.getItemList());
        tbItem.setPrice(goods.getGoods().getPrice());
        tbItem.setNum(999);
        tbItem.setStatus("0");
        tbItem.setIsDefault("1");
        tbItem.setCategoryId(goods.getGoods().getCategory3Id());
        tbItem.setCreateTime(LocalDateTime.now());
        tbItem.setUpdateTime(LocalDateTime.now());
        String title = goods.getGoods().getGoodsName();
        TbItemCat tbItemCat = tbItemCatService.selectOne(new QueryWrapper<TbItemCat>().eq("id", goods.getGoods().getCategory3Id()));
        tbItem.setCategory(tbItemCat.getName());
        TbBrand brand = tbBrandService.selectOne(new QueryWrapper<TbBrand>().eq("id", goods.getGoods().getBrandId()));
        tbItem.setBrand(brand.getName());
        List<Map> imageList = JSON.parseArray(goods.getGoodsDesc().getItemImages(), Map.class);
        if (imageList.size() > 0) {
            tbItem.setImage((String) imageList.get(0).get("url"));
        }
        tbItemService.insert(tbItem);
        return WrapMapper.ok(goods);



    }*/

   @Override
    public Wrapper save(GoodDto goods) {
        goods.getGoods().setAuditStatus("0");
        baseMapper.insert(goods.getGoods());
        goods.getGoodsDesc().setGoodsId(goods.getGoods().getId());
        tbGoodsDescService.insert(goods.getGoodsDesc());
        setItemList(goods);
        return WrapMapper.ok(goods);
    }

    private void setItemList(GoodDto goods) {

        try {
            if ("1".equals(goods.getGoods().getIsEnableSpec())) {

                List<TbItem> itemList=(List<TbItem>)JSONArray.parseArray(goods.getItemList(),TbItem.class);

                // 启用规格
                // 保存SKU列表的信息:
                for (TbItem item : itemList) {
                    // 设置SKU的数据：
                    // item.setTitle(title);
                    String title = goods.getGoods().getGoodsName();
                    Map<String, String> map = JSON.parseObject(item.getSpec(), Map.class);
                    for (String key : map.keySet()) {
                        title += " " + map.get(key);
                    }
                    item.setTitle(title);

                    setValue(goods,item);

                    tbItemService.insert(item);
                }

            } else {
                // 没有启用规格
                TbItem item = new TbItem();

                item.setTitle(goods.getGoods().getGoodsName());

                item.setPrice(goods.getGoods().getPrice());

                item.setNum(999);

                item.setStatus("0");

                item.setIsDefault("1");
                item.setSpec("{}");
                setValue(goods, item);
                tbItemService.insert(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setValue(GoodDto goods, TbItem item) {

        try {
            List<Map> imageList = JSON.parseArray(goods.getGoodsDesc().getItemImages(), Map.class);
            if (imageList.size() > 0) {
                item.setImage((String) imageList.get(0).get("url"));
            }

            // 保存三级分类的ID:
            item.setCategoryId(goods.getGoods().getCategory3Id());
            item.setCreateTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            // 设置商品ID
            item.setGoodsId(goods.getGoods().getId());
            item.setSellerId("lif");

            TbItemCat tbItemCat = tbItemCatService.selectOne(new QueryWrapper<TbItemCat>().eq("id", goods.getGoods().getCategory3Id()));
            item.setCategory(tbItemCat.getName());

            TbBrand brand = tbBrandService.selectOne(new QueryWrapper<TbBrand>().eq("id", goods.getGoods().getBrandId()));
            item.setBrand(brand.getName());
            item.setSeller("联想");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}


