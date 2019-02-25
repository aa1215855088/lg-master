package com.lg.biz.web.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lg.commons.util.wrapper.WrapMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
 * @create: 2019-02-24 16:43
 **/
@Slf4j
@Component("authenticationAccessDeniedHandler")
public class AuthenticationAccessDeniedHandler implements AccessDeniedHandler {
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        log.info("身份验证失败");
        //判断是json 格式返回 还是 view 格式返回
        //将 authention 信息打包成json格式返回
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write((objectMapper.writeValueAsString(WrapMapper.error("身份验证失败,请联系管理员"))));
    }
}
