package com.lg.biz.web.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.lg.biz.model.domain.TbSeller;
import com.lg.biz.service.TbSellerService;
import com.lg.commons.core.controller.BaseController;
import com.lg.commons.util.wrapper.WrapMapper;
import com.lg.commons.util.wrapper.Wrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author xuzilou
 * @since 2019-02-19
 */
@RestController
@RequestMapping(value = "/tbSellers", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "WEB - TbSellerController", tags = "商品品牌API", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TbSellerController extends BaseController {

    @Reference(version = "1.0.0")
    public TbSellerService tbSellerService;

    @PostMapping("/SellerInsert")
    @ApiOperation(value = "商家注册", httpMethod = "POST")
    public Wrapper add(@RequestBody @ApiParam TbSeller tbSeller) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode(tbSeller.getPassword());
        tbSeller.setPassword(password);
        logger.info("商家注册seller:{}", tbSeller);
        return this.tbSellerService.sellerInsert(tbSeller);

    }

    @GetMapping("/findSellerById")
    @ApiOperation(value = "根据id查找商家信息", httpMethod = "GET")
    private Wrapper<TbSeller> findById(@ApiParam Principal principal) {
        logger.info("根据ID查询商家信息:{}", principal.getName());
        return this.tbSellerService.findById(principal.getName());
    }


    @PostMapping("/updateSellerInfo")
    @ApiOperation(value = "商家注册修改", httpMethod = "POST")
    public Wrapper updateSellerInfo(@RequestBody @ApiParam TbSeller tbSeller) {
        logger.info("修改商家资料:{}", tbSeller);
        return this.tbSellerService.updateSellerInfo(tbSeller);
    }

    @PostMapping("/updatePasswordById")
    @ApiOperation(value = "修改密码", httpMethod = "POST")
    public Wrapper updatePasswordById(@RequestBody @ApiParam TbSeller tbSeller,Principal principal) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        logger.info("修改密码:{}", tbSeller);
        //将原始密码拿出来做匹配，调用方法判断是否相同
        boolean sign=passwordEncoder.matches(tbSeller.getName(),this.tbSellerService.findByLoginName(principal.getName()).getPassword());
        System.out.println(sign);
       if(sign){
            System.out.println("11111");
            //原密码和数据相同进行修改操作
            //将要修改的密码进行加密
            String password = passwordEncoder.encode(tbSeller.getPassword());
            tbSeller.setPassword(password);
            //设置得到sellId
            tbSeller.setSellerId(principal.getName());
            return this.tbSellerService.updatePasswordById(tbSeller);
        }
            //如果不同返回错误消息
            //return "原密码错误";
           System.out.println("222");
            return WrapMapper.error();
    }

}
