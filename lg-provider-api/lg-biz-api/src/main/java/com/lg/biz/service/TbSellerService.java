package com.lg.biz.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lg.biz.model.domain.TbSeller;
import com.lg.biz.vo.TbSellerVo;
import com.lg.commons.base.vo.PageVO;
import com.lg.commons.util.wrapper.Wrapper;

import java.util.List;


/**
 * <p>
 * 服务类
 * </p>
 *
 * @author xuzilou
 * @since 2019-02-19
 */
public interface TbSellerService extends IService<TbSeller> {

    /**
     * 跟据商家登录名查询商家信息
     *
     * @param username
     * @return
     */
   public TbSeller findByLoginName(String username);
    /**
     * 查询
     * @param queryWrapper
     * @return
     */
    public Wrapper<List<TbSeller>> listSeller(QueryWrapper<TbSeller> queryWrapper);
    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @return
     */
    public Wrapper<PageVO<TbSeller>> listPage(Integer pageNum, Integer pageSize);
    /**
     * 根据ID获取实体
     * @param id
     * @return
     */
    public Wrapper<TbSeller> findOne(String id);

    /**
     * 分页显示品牌列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    public Wrapper<PageVO<TbSeller>> findPage(Integer pageNum, Integer pageSize,TbSellerVo SellerVo);

    /**
     *修改状态
     * @param
     * @param
     */
    public Wrapper updateStatus(String id,String status);

    /**
     * 添加
     */
    public Wrapper<Integer> save(TbSeller tbSeller);
}
