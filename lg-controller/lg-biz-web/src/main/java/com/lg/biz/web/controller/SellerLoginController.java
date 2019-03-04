package com.lg.biz.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lg.biz.model.domain.TbSeller;
import com.lg.biz.service.TbSellerService;
import com.lg.biz.web.security.SellerUserInfo;
import com.lg.commons.core.controller.BaseController;
import com.lg.commons.util.wrapper.WrapMapper;
import com.lg.commons.util.wrapper.Wrapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

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
 * @create: 2019-02-24 16:06
 **/
@RestController
@RequestMapping("/tbSeller")
public class SellerLoginController extends BaseController {


    @Reference(version = "1.0.0")
    public TbSellerService tbSellerService;


    @GetMapping("/sellerInfo")
    public Wrapper<String> sellerInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SellerUserInfo info = (SellerUserInfo) authentication.getPrincipal();
        TbSeller byLoginName = this.tbSellerService.findByLoginName(info.getUsername());
        logger.info("获取商家信息:{}", byLoginName);
        return WrapMapper.ok(byLoginName.getName());
    }


}
