package com.lg.biz.web.controller;


import com.lg.commons.core.controller.BaseController;
import io.swagger.annotations.Api;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author xuzilou
 * @since 2019-02-19
 */
@RestController
@RequestMapping(value = "/tbGoods", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "WEB - TbGoodsController", tags = "商品录入Api", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TbGoodsController extends BaseController {

//
//    @Reference(version = "1.0.0")
//    public TbGoodsService tbGoodsService;
//
//    @PostMapping("/add")
//    @ApiOperation(httpMethod = "POST", value = "商家添加商品")
//    public Wrapper save(@RequestBody @ApiParam("商品基本信息") GoodDto goods, Principal principal) {
//        goods.setSellerId(principal.getName());
//        logger.info("添加商品基本信息，{}", goods);
//        return this.tbGoodsService.save(goods);
//    }

}
