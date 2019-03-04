package com.lg.user.web.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.lg.commons.core.controller.BaseController;
import com.lg.commons.util.wrapper.WrapMapper;
import com.lg.commons.util.wrapper.Wrapper;
import com.lg.user.model.domain.TbUser;
import com.lg.user.service.TbUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author xuzilou
 * @since 2019-02-19
 */
@RestController
@RequestMapping("/user")
public class TbUserController extends BaseController {
    //    @Reference(version = "1.0.0")
//    private TbUserService tbUserService;
//
//    @GetMapping("")
//    public Wrapper<List<TbUser>> findAll() {
//        List<TbUser> tbUsers = this.tbUserService.findAll();
//        return WrapMapper.ok(tbUsers);
//    }
    @GetMapping("")
    public Principal user(Principal principal) {
        return principal;
    }

}
