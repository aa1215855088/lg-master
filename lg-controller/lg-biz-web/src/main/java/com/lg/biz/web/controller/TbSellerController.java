package com.lg.biz.web.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.lg.biz.model.domain.TbSeller;
import com.lg.biz.service.TbSellerService;
import com.lg.commons.core.controller.BaseController;
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
    private Wrapper<TbSeller> findById(Principal principal) {
        logger.info("根据商家ID查询商家ID:{}", principal.getName());
        return this.tbSellerService.findById(principal.getName());
    }

}
