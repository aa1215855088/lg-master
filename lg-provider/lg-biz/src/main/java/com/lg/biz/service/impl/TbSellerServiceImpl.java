package com.lg.biz.service.impl;


import cn.hutool.core.util.StrUtil;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lg.biz.mapper.TbSellerMapper;
import com.lg.biz.model.domain.TbSeller;
import com.lg.biz.service.TbSellerService;
import com.lg.biz.vo.TbSellerVo;
import com.lg.commons.base.enums.ErrorCodeEnum;
import com.lg.commons.base.exception.BusinessException;
import com.lg.commons.base.vo.PageVO;
import com.lg.commons.util.validators.BeanValidators;
import com.lg.commons.util.validators.Insert;
import com.lg.commons.util.wrapper.WrapMapper;
import com.lg.commons.util.wrapper.Wrapper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import javax.validation.Validator;
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
@Service(version = "1.0.0",timeout = 6000)
@Transactional
public class TbSellerServiceImpl extends ServiceImpl<TbSellerMapper, TbSeller> implements TbSellerService {
    @Autowired
    private Validator validator;


    @Override
    public TbSeller findByLoginName(String username) {
        return this.baseMapper.selectOne(new QueryWrapper<TbSeller>()
                .eq(StrUtil.isNotBlank(username), "seller_id", username));
    }

    @Override
    public Wrapper sellerInsert(TbSeller tbSeller) {
        BeanValidators.validateWithException(validator, tbSeller);
        /*Integer  num=this.baseMapper.insert(tbSeller);*/
        tbSeller.setStatus("0");
        Integer insert = this.baseMapper.insert(tbSeller);
        if (insert != 1) {
            throw new BusinessException(ErrorCodeEnum.GL99990500, "商家注入失败");
        }
        return WrapMapper.ok(insert);
    }

    @Override
    public Wrapper<TbSeller> findById(String name) {
        if (StrUtil.isBlank(name)) {
            throw new BusinessException(500, "用户名为空");
        }
        TbSeller tbSeller = this.baseMapper.selectOne(new QueryWrapper<TbSeller>().eq("seller_id", name));
        return WrapMapper.ok(tbSeller);
    }

    @Override
    public Wrapper updateSellerInfo(TbSeller tbSeller) {
        /*BeanValidators.validateWithException(validator, tbSeller);*/
         Integer flag= this.baseMapper.updateById(tbSeller);
        if (flag != 1) {
            throw new BusinessException(ErrorCodeEnum.GL99990500, "商家修改资料失败");
        }
        return WrapMapper.ok();
    }

    @Override
    public Wrapper updatePasswordById(TbSeller tbSeller) {
        Integer flag=this.baseMapper.updatePassword(tbSeller);
        if (flag != 1) {
            throw new BusinessException(ErrorCodeEnum.GL99990500, "密码修改失败");
        }
        return WrapMapper.ok();
    }

  /*  @Override
    public TbSeller selectPassword(String  password) {
       return this.baseMapper.selectOne(new QueryWrapper<TbSeller>()
                .eq("password",password));
    }*/

    /**
     * 查询所有
     * queryWrapper
     * */
    @Override
    public Wrapper<List<TbSeller>> listSeller(QueryWrapper<TbSeller> queryWrapper) {
        return WrapMapper.ok(this.baseMapper.selectList(queryWrapper));
    }

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    @Override
    public Wrapper<TbSeller> findOne(String id) {
        checkNotNull(id);
        return WrapMapper.ok(this.baseMapper.selectById(id));
    }

    /**
     * 分页加查询
     * @param pageNum
     * @param pageSize
     * @param SellerVo
     * @return
     */

    @Override
    public Wrapper<PageVO<TbSeller>> findPage(Integer pageNum, Integer pageSize, TbSellerVo SellerVo) {

        if(pageNum==null||pageNum==0){
            pageNum=TbSeller.PAGE_NUM;
        }
        if(pageSize==null||pageSize==0){
            pageSize=TbSeller.PAGE_SIZE;
        }
        if (SellerVo == null){
            return listPage(pageNum,pageSize);
        }
        IPage<TbSeller> sellerIPage= this.baseMapper.selectPage(new Page<>(pageNum,pageSize),
                new QueryWrapper<TbSeller>().
                        like(StrUtil.isNotBlank(SellerVo.getName()),"name",SellerVo.getName())
                        .eq(StrUtil.isNotBlank(SellerVo.getNickName()),"nike_name",SellerVo.getNickName())
                        .eq(StrUtil.isNotBlank(SellerVo.getLinkmanName()),"link_manName",SellerVo.getLinkmanName())
                        .eq(StrUtil.isNotBlank(SellerVo.getTelephone()),"telephone",SellerVo.getTelephone())
                        .eq(StrUtil.isNotBlank(SellerVo.getStatus()),"status",SellerVo.getStatus()));
        PageVO<TbSeller> pageVo=new PageVO<>();
        pageVo.setRows(sellerIPage.getRecords());
        pageVo.setTotal(sellerIPage.getTotal());

        return WrapMapper.ok(pageVo);
    }

    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public Wrapper<PageVO<TbSeller>> listPage(Integer pageNum, Integer pageSize) {
        if(pageNum == null || pageNum == 0){
            pageNum = TbSeller.PAGE_NUM;
        }

        if(pageSize == null || pageSize == 0){
            pageSize = TbSeller.PAGE_SIZE;
        }

        IPage<TbSeller> iPage = this.baseMapper.selectPage(
                new Page<>(pageNum,pageSize),new QueryWrapper<TbSeller>());
        PageVO<TbSeller> pageVO = new PageVO<>();
        pageVO.setRows(iPage.getRecords());
        pageVO.setTotal(iPage.getTotal());
        return WrapMapper.ok(pageVO);
    }


    @Override
    public Wrapper updateStatus(String id, String status){
        TbSeller tbSeller =this.baseMapper.selectById(id);
        tbSeller.setStatus(status);
        return  WrapMapper.ok(this.baseMapper.updateById(tbSeller));
    }

    @Override
    public Wrapper<Integer> save(TbSeller tbSeller) {
        BeanValidators.validateWithException(validator,tbSeller, Insert.class);
        return WrapMapper.ok(this.baseMapper.insert(tbSeller));
    }


}
