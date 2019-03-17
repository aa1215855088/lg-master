package com.lg.product.service.impl;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lg.commons.util.wrapper.WrapMapper;
import com.lg.commons.util.wrapper.Wrapper;
import com.lg.product.exceptions.ProductBizException;
import com.lg.product.filter.AuthorityFilter;
import com.lg.product.mapper.TbItemMapper;
import com.lg.product.model.domain.TbItem;
import com.lg.product.service.TbItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 商品表 服务实现类
 * </p>
 *
 * @author xuzilou
 * @since 2019-02-19
 */
@Service(version = "1.0.0", timeout = 6000)
@Transactional
public class TbItemServiceImpl extends ServiceImpl<TbItemMapper, TbItem> implements TbItemService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public Wrapper<List<TbItem>> findAll() {
        String itemList = this.redisTemplate.opsForValue().get("itemList");
        List<TbItem> tbItems = JSONArray.parseArray(itemList, TbItem.class);
        if (CollUtil.isEmpty(tbItems)) {
            tbItems = this.baseMapper.selectPage(new Page<>(0, 10), new QueryWrapper<>()).getRecords();
            this.redisTemplate.opsForValue().set("itemList", JSON.toJSONString(tbItems));
        }
        return WrapMapper.ok(tbItems);
    }

    @Override
    public String hello() {
        return "hello";
    }

    @Override
    public List<TbItem> findByGoodIds(List<Long> goodsIds) {
        return this.baseMapper.selectList(new QueryWrapper<TbItem>().in("goods_id", goodsIds));
    }

    @Override
    public Wrapper checkItemNumEnoughAndDecr(Long itemId, Integer num) {
        TbItem tbItem = this.baseMapper.selectById(itemId);
        int i = tbItem.getNum() - num;
        if (i >= 0) {
            return WrapMapper.ok();
        }
        return WrapMapper.error("[" + tbItem.getTitle() + "]商品库存不足");
    }
}
