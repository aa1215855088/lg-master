package com.lg.user.web.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.lg.commons.core.controller.BaseController;
import com.lg.commons.util.wrapper.WrapMapper;
import com.lg.commons.util.wrapper.Wrapper;
import com.lg.user.model.domain.TbUser;
import com.lg.user.model.dto.UserDTO;
import com.lg.user.service.TbUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

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
@Api(tags = "用户API")
public class TbUserController extends BaseController {


    @Reference(version = "1.0.0")
    private TbUserService tbUserService;

    @GetMapping("/info")
    @ApiOperation(value = "获取用户信息", httpMethod = "GET")
    public Principal user(Principal principal) {
        return principal;
    }

    @PostMapping("/register")
    @ApiOperation(value = "注册用户", httpMethod = "POST")
    public Wrapper register(@ApiParam @RequestBody UserDTO userDTO) {
        logger.info("注册用户:{}", userDTO);
        return this.tbUserService.register(userDTO);
    }


    @GetMapping("/{id}")
    @ApiOperation(httpMethod = "GET", value = "验证用户名是否存在")
    public Wrapper checkUsername(@ApiParam @PathVariable String username) {
        return this.tbUserService.checkUsername(username);
    }


}
