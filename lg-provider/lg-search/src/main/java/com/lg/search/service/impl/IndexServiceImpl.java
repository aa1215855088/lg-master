package com.lg.search.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.dubbo.config.annotation.Reference;
import com.lg.product.model.domain.TbItem;
import com.lg.product.service.TbItemService;
import com.lg.search.dto.ItemSearch;
import com.lg.search.repository.ItemSearchRepository;
import com.lg.search.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * ┏┓　　　┏┓
 * ┏┛┻━━━┛┻┓
 * ┃　　　　　　　┃
 * ┃　　　━　　　┃
 * ┃　┳┛　┗┳　┃
 * ┃　　　　　　　┃
 * ┃　　　┻　　　┃
 * ┃　　　　　　　┃
 * ┗━┓　　　┏━┛
 * 　　┃　　　┃神兽保佑
 * 　　┃　　　┃代码无BUG！
 * 　　┃　　　┗━━━┓
 * 　　┃　　　　　　　┣┓
 * 　　┃　　　　　　　┏┛
 * 　　┗┓┓┏━┳┓┏┛
 * 　　　┃┫┫　┃┫┫
 * 　　　┗┻┛　┗┻┛
 *
 * @program: lg-master
 * @description:
 * @author: 徐子楼
 * @create: 2019-03-12 15:51
 **/
@Service
public class IndexServiceImpl implements IndexService {
    @Autowired
    private ItemSearchRepository itemSearchRepository;
    @Reference(version = "1.0.0")
    private TbItemService tbItemService;

    @Override
    public void saveAll(List<Long> goodsIds) {
        List<TbItem> items = tbItemService.findByGoodIds(goodsIds);
        List<ItemSearch> itemSearches = new ArrayList<>();
        for (TbItem item : items) {
            ItemSearch itemSearch = new ItemSearch();
            BeanUtil.copyProperties(item, itemSearch);
            itemSearches.add(itemSearch);
        }
        this.itemSearchRepository.saveAll(itemSearches);
    }
}
