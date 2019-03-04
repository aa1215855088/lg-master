package com.lg.user.web.controller;


import com.lg.commons.core.controller.BaseController;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
public class TbAddressController extends BaseController {
    @RequestMapping("/")
    public String index() {
        return "访问了首页哦";
    }

    @RequestMapping("/hello")
    public Authentication hello() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication;
    }

}
