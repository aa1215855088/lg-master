package com.lg.task.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lg.product.model.domain.TbGoods;
import com.lg.product.model.domain.TbGoodsDesc;
import com.lg.product.model.domain.TbItem;
import com.lg.product.model.domain.TbItemCat;
import com.lg.task.mapper.TbGoodsDescMapper;
import com.lg.task.mapper.TbGoodsMapper;
import com.lg.task.mapper.TbItemCatMapper;
import com.lg.task.mapper.TbItemMapper;
import com.lg.task.service.PageService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
 * @create: 2019-03-10 15:21
 **/
@Service
@Slf4j
public class PageServiceImpl implements PageService {
    @Autowired
    private Configuration configuration;

    @Autowired
    private TbGoodsMapper goodsMapper;
    @Autowired
    private TbItemCatMapper tbItemCatMapper;
    @Autowired
    private TbItemMapper tbItemMapper;
    @Autowired
    private TbGoodsDescMapper tbGoodsDescMapper;
    @Value("${page.path}")
    private String path;

    @Override
    public boolean createItemPageHtml(JSONArray objects) {

        try {
            Template template = configuration.getTemplate("item.ftl");
            for (Object id : objects) {
                long goodsId = Long.parseLong(id.toString());
                //创建数据模型
                Map<String, Object> dataModel = new HashMap<>();
                //1.商品主表数据
                TbGoods goods = this.goodsMapper.selectById(goodsId);
                dataModel.put("goods", goods);
                TbGoodsDesc tbGoodsDesc = this.tbGoodsDescMapper.selectById(goodsId);
                dataModel.put("goodsDesc", tbGoodsDesc);
                TbItemCat itemCat1 = this.tbItemCatMapper.selectById(goods.getCategory1Id());
                TbItemCat itemCat2 = this.tbItemCatMapper.selectById(goods.getCategory2Id());
                TbItemCat itemCat3 = this.tbItemCatMapper.selectById(goods.getCategory3Id());
                dataModel.put("itemCat1", itemCat1 == null ? null : itemCat1.getName());
                dataModel.put("itemCat2", itemCat2 == null ? null : itemCat2.getName());
                dataModel.put("itemCat3", itemCat3 == null ? null : itemCat3.getName());
                //4.读取SKU列表
                List<TbItem> itemList = this.tbItemMapper.selectList(new QueryWrapper<TbItem>().eq("goods_id",
                        goodsId));
                dataModel.put("itemList", itemList);
                Writer out = new FileWriter(path + goodsId + ".html");
                template.process(dataModel, out);
                out.close();
                return true;
            }
        } catch (Exception e) {
            log.error("页面静态化失败:{}", e);
            return false;
        }
        return true;
    }


}
