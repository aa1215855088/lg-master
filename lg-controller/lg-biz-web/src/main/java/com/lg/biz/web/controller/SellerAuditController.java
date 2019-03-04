package com.lg.biz.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lg.biz.model.domain.TbSeller;
import com.lg.biz.service.TbSellerService;
import com.lg.biz.vo.TbSellerVo;
import com.lg.commons.base.vo.PageVO;
import com.lg.commons.core.controller.BaseController;
import com.lg.commons.util.wrapper.WrapMapper;
import com.lg.commons.util.wrapper.Wrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author xuzilou
 * @since 2019-02-19
 */
@RestController
@RequestMapping("/sellerAudit")
@Api(value = "WEB - ProductBrandController",tags = "商家审核Api",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class SellerAuditController extends BaseController {

    @Reference(version = "1.0.0")
    public TbSellerService tbSellerService;

    /**
     * 查看所有
     * */
    @GetMapping("/findAll")
    @ApiOperation(httpMethod = "GET",value="获取商家管理列表")
    public Wrapper<List<TbSeller>> finAll() {
        logger.info("获取商家管理列表");
        return WrapMapper.ok(this.tbSellerService.selectList(new QueryWrapper<>()));
    }

    @GetMapping("/findOne")
    @ApiOperation(httpMethod = "GET",value="获取ID")
    public Wrapper<TbSeller> findOne(String id){
        logger.info("根据ID查询,id={}",id);
        return this.tbSellerService.findOne(id);
    }
    /**
     * 分页
     * */
    @GetMapping("/query")
    @ApiOperation(httpMethod = "GET" , value = "分页显示商品品牌列表")
    public Wrapper<PageVO<TbSeller>> listPage(Integer page, Integer rows){
        logger.info("分页查询 品牌列表");
        return  tbSellerService.listPage(page,rows);
    }

    /**
     * 分页＋查询
     * @param page
     * @param rows
     * @param tbSellerVo
     * @return
     */
    @PostMapping("/findPage")
    @ApiOperation(httpMethod = "POST",value = "列表搜索")
    public Wrapper<PageVO<TbSeller>> findPage(Integer page, Integer rows,
                                            @RequestBody(required = false) @ApiParam( name="searchEntity", value="条件") TbSellerVo tbSellerVo) {
        logger.info("列表搜索");
        System.out.println(tbSellerService.findPage(page, rows,tbSellerVo));
        return tbSellerService.findPage(page, rows,tbSellerVo);
    }

    /**
     * 修改状态
     * @param id
     * @param status
     * @return
     */
    @PostMapping("/UpdateSeller")
    @ApiOperation(httpMethod = "POST",value="修改状态")
    public Wrapper UpdateSeller(String id,String status){
        logger.info("修改状态");
        return WrapMapper.ok( this.tbSellerService.updateStatus(id,status));
    }
    /*
    *//**
     * 根据ID
     * *//*
    @GetMapping("/")
    @ApiOperation(httpMethod = "GET",value ="根据ID搜索")
    public Wrapper<TbSeller> findById(@ApiParam @PathVariable Integer id){
    logger.info("根据ID查询,id={}",id);
    TbSeller tbSeller=this.tbSellerService.findByIdSeller(id);
    return WrapMapper.ok(tbSeller);
    }

    /*
    *//**
     * 删除
     * *//*
    @DeleteMapping("/{id}")
    @ApiOperation(httpMethod = "PUT",value="删除")
    public Wrapper DeleteSeller(@ApiParam @PathVariable("id") Long[] ids){
        logger.info("删除,id={}", Arrays.toString(ids));
        this.tbSellerService.delBySeller(ids);
        return WrapMapper.ok();
    }*/

    /**
     * 添加
     */
    @PostMapping("/")
    @ApiOperation(httpMethod = "POST",value="增加")
    public Wrapper save(TbSeller tbSeller){
        logger.info("增加");
        return this.tbSellerService.save(tbSeller);
    }
}

