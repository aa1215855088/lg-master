package com.lg.seckill.web.controller;


import com.lg.commons.core.controller.BaseController;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xuzilou
 * @since 2019-02-19
 */
@RestController
@RequestMapping("/tbSeckillGoods")
public class TbSeckillGoodsController extends BaseController {

    @GetMapping("")
    public String hello(Authentication authentication) {
        logger.info("用户信息:{}", authentication);
        return "hello";
    }
}
