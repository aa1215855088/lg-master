package com.lg.product.service.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lg.commons.base.constant.Constants;
import com.lg.commons.base.enums.ItemStautsEnum;
import com.lg.commons.base.enums.MSGStatusEnum;
import com.lg.commons.base.enums.TypeEnum;
import com.lg.commons.base.enums.ErrorCodeEnum;
import com.lg.commons.base.exception.BusinessException;
import com.lg.commons.base.vo.PageVO;
import com.lg.commons.util.id.SnowflakeIdWorker;
import com.lg.commons.util.wrapper.WrapMapper;
import com.lg.commons.util.wrapper.Wrapper;
import com.lg.product.mapper.TMqMessageLogMapper;
import com.lg.product.mapper.TbGoodsMapper;
import com.lg.product.model.domain.*;
import com.lg.product.model.dto.GoodDTD;
import com.lg.product.model.dto.Goods;
import com.lg.product.model.vo.GoodsVO;
import com.lg.product.producer.RabbitPageSender;
import com.lg.product.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Validator;
import java.time.LocalDateTime;
import java.util.*;

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
@Slf4j
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

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private RabbitPageSender rabbitPageSender;

    private SnowflakeIdWorker snowflakeIdWorker = new SnowflakeIdWorker(0, 0);

    @Autowired
    private TMqMessageLogMapper tMqMessageLogMapper;

    @Override
    public Wrapper save(GoodDTD goods) {
        goods.getGoods().setAuditStatus("0");
        baseMapper.insert(goods.getGoods());
        goods.getGoodsDesc().setGoodsId(goods.getGoods().getId());
        tbGoodsDescService.insert(goods.getGoodsDesc());
        setItemList(goods);
        return WrapMapper.ok(goods);
    }

    @Override
    public Wrapper<List<TbGoods>> findAll() {
        return null;
    }


    @Override
    public Wrapper updateStatus(Long[] ids, String status) {

        for (Long id : ids) {

            TbGoods tbGoods = baseMapper.selectById(id);

            tbGoods.setAuditStatus(status);

            baseMapper.updateById(tbGoods);
        }
        if (ItemStautsEnum.EXAMINATION_PASSE.getCode().equals(status)) {
            long msgId = snowflakeIdWorker.nextId();
            TMqMessageLog mqMessageLog = new TMqMessageLog(msgId, TypeEnum.CREATE_PAGE.getCode(),
                    JSON.toJSONString(ids), 0,
                    MSGStatusEnum.SENDING.getCode(), LocalDateTime.now().plusMinutes(Constants.TRY_TIMEOUT));
            Integer row = tMqMessageLogMapper.insert(mqMessageLog);
            if (row == 0) {
                throw new BusinessException(500, "消息入库失败");
            }

            try {
                rabbitPageSender.sendOrder(mqMessageLog);
            } catch (Exception e) {
                log.error("sendPage mq msg error: ", e);
                tMqMessageLogMapper.updataNextRetryTimeForNow(mqMessageLog.getMessageId());
            }
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
    public Wrapper<PageVO<Goods>> findPageAndName(String name, Integer page, Integer rows) {
        if (page == null || page == 0) {
            page = 1;
        }

        if (rows == null || rows == 0) {
            rows = 10;
        }
        IPage<TbGoods> iPage = this.baseMapper.selectPage(
                new Page<>(page, rows),
                new QueryWrapper<TbGoods>()
                        .like(StrUtil.isNotBlank(name), "goods_name", name)
                        .eq("audit_status", "1"));
        List<TbGoods> records = iPage.getRecords();
        if (records == null) {
            return WrapMapper.ok();
        }
        List<Goods> goodsList = new ArrayList<>();
        for (TbGoods record : records) {
            Goods goods = new Goods();
            goods.setCategory1Name(tbItemCatService.findOne(record.getCategory1Id()).getResult().getName());
            goods.setCategory2Name(tbItemCatService.findOne(record.getCategory2Id()).getResult().getName());
            goods.setCategory3Name(tbItemCatService.findOne(record.getCategory3Id()).getResult().getName());
            BeanUtil.copyProperties(record, goods);
            goodsList.add(goods);
        }
//        PageInfo<Goods> pageInfo = new PageInfo<>(list);
        PageVO<Goods> pageVO = new PageVO<Goods>();
        pageVO.setRows(goodsList);
        pageVO.setTotal(iPage.getTotal());
        return WrapMapper.ok(pageVO);

    }


    /**
     * 查询所有
     *
     * @param queryWrapper
     * @return
     */

    @Override
    public Wrapper<List<TbGoods>> list(QueryWrapper<TbGoods> queryWrapper) {
        List<TbGoods> list = this.baseMapper.selectList(queryWrapper);
        return WrapMapper.ok(list);
    }

    @Override
    public Wrapper<PageVO<TbGoods>> pageList(Integer pageNum, Integer pageSize) {
        return null;
    }


    /**
     * 搜索
     *
     * @param pageNum
     * @param pageSize
     * @param goodsVo
     * @return
     */
    @Override
    public Wrapper<PageVO<Goods>> search(Integer pageNum, Integer pageSize, GoodsVO goodsVo) {
        if (pageNum == null || pageNum == 0) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize == 0) {
            pageSize = 10;
        }
//        PageHelper.startPage(pageNum, pageSize);
//        List<Goods> list = this.baseMapper.find(goodsVo);
        IPage<TbGoods> iPage = this.baseMapper.selectPage(
                new Page<>(pageNum, pageSize),
                new QueryWrapper<TbGoods>()
                        .eq(StrUtil.isNotBlank(goodsVo.getAuditStatus()), "audit_status", goodsVo.getAuditStatus())
                        .like(StrUtil.isNotBlank(goodsVo.getGoodsName()), "goods_name", goodsVo.getGoodsName()));
        List<TbGoods> records = iPage.getRecords();
        if (records == null) {
            return WrapMapper.ok();
        }

        List<Goods> goodsList = new ArrayList<>();
        for (TbGoods record : records) {
            Goods goods = new Goods();
            goods.setCategory1Name(tbItemCatService.findOne(record.getCategory1Id()).getResult().getName());
            goods.setCategory2Name(tbItemCatService.findOne(record.getCategory2Id()).getResult().getName());
            goods.setCategory3Name(tbItemCatService.findOne(record.getCategory3Id()).getResult().getName());
            BeanUtil.copyProperties(record, goods);
            goodsList.add(goods);
        }
//        PageInfo<Goods> pageInfo = new PageInfo<>(list);
        PageVO<Goods> pageVO = new PageVO<Goods>();
        pageVO.setRows(goodsList);
        pageVO.setTotal(iPage.getTotal());
        return WrapMapper.ok(pageVO);
    }

    /**
     * 删除商品
     *
     * @param ids
     * @return
     */
    @Override
    public Wrapper delete(Long[] ids) {
        checkNotNull(ids);
        List<Long> idArray = Arrays.asList(ids);
        Integer index = this.baseMapper.deleteBatchIds(idArray);
        this.tbGoodsDescService.deleteBatchIds(idArray);
        this.tbItemService.deleteBatchIds(idArray);
        if (index != ids.length) {
            throw new BusinessException(ErrorCodeEnum.GL99990500, "删除商品品牌信息失败");
        }
        return WrapMapper.ok(index);
    }

    /**
     * 查询实体
     *
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
     *
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
     *
     * @param ids
     * @return
     */
    @Override
    public Wrapper shield(Long[] ids) {
        Integer index = 0;
        for (int i = 0; i <= ids.length; i++) {
            TbGoods tbGoods = this.baseMapper.selectById(ids[0]);
            tbGoods.setAuditStatus("4");
            index = this.baseMapper.updateById(tbGoods);
        }
        if (index != 1) {
            throw new BusinessException(ErrorCodeEnum.GL99990500, "屏蔽商品信息失败");
        }
        return WrapMapper.ok();
    }


    @Override
    public Wrapper submitAudit(Long[] ids) {
        checkNotNull(ids);
        for (Long id : ids) {
            TbGoods tbGoods = this.baseMapper.selectById(id);
            if (tbGoods == null) {
                throw new BusinessException(500, "操作失败!请刷新后重试");
            }
            switch (tbGoods.getAuditStatus()) {
                case "1":
                    return WrapMapper.error("[" + tbGoods.getGoodsName() + "]该商品正在审核中");
                case "2":
                    return WrapMapper.error("[" + tbGoods.getGoodsName() + "]该商品审核通过");
            }
            tbGoods.setAuditStatus("1");
            this.baseMapper.updateById(tbGoods);
        }
        return WrapMapper.ok();
    }

    private void setItemList(GoodDTD goods) {

        try {
            if ("1".equals(goods.getGoods().getIsEnableSpec())) {

                List<TbItem> itemList = (List<TbItem>) JSONArray.parseArray(goods.getItemList(), TbItem.class);

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

                    setValue(goods, item);

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

    private void setValue(GoodDTD goods, TbItem item) {

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
            item.setSellerId(goods.getGoods().getSellerId());

            TbItemCat tbItemCat = tbItemCatService.selectOne(new QueryWrapper<TbItemCat>().eq("id",
                    goods.getGoods().getCategory3Id()));
            item.setCategory(tbItemCat.getName());

            TbBrand brand = tbBrandService.selectOne(new QueryWrapper<TbBrand>().eq("id",
                    goods.getGoods().getBrandId()));
            item.setBrand(brand.getName());
            item.setSeller("联想");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
