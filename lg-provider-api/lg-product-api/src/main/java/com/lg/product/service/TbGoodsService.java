package com.lg.product.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lg.commons.base.vo.PageVO;
import com.lg.commons.util.wrapper.WrapMapper;
import com.lg.commons.util.wrapper.Wrapper;
import com.lg.product.model.domain.TbGoods;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public interface TbGoodsService {

    public Wrapper<List<TbGoods>> findAll();

    public Wrapper<PageVO<TbGoods>> findPage(Integer page, Integer rows);

    public Wrapper updateStatus(Long[] ids, String status);

    public Wrapper deleteGoods(Long[] ids);

    public Wrapper<PageVO<TbGoods>> findPageAndName(String name, Integer page, Integer rows);
}
