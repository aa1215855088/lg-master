package com.lg.user.web.controller;

import com.lg.commons.core.controller.BaseController;
import com.lg.commons.util.wrapper.WrapMapper;
import com.lg.commons.util.wrapper.Wrapper;
import com.lg.user.web.auth.domain.GitUser;
import com.lg.user.web.auth.service.GithubAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
 * @create: 2019-02-27 14:19
 **/
@Controller
public class AuthController extends BaseController {


    @Autowired
    private GithubAuthService githubAuthService;


    /**
     * 第三方授权后会回调此方法，并将code传过来
     *
     * @param code code
     * @return
     */
    @GetMapping("/oauth/github/callback")
    public String oauthByGitHub(@RequestParam(value = "code", required = false) String code,
                                HttpServletRequest request, HttpServletResponse response) {
        Wrapper<String> tokenWrapper = githubAuthService.getAccessToken(code);
        if (tokenWrapper.success()) {
            Wrapper<GitUser> gitUserInfo = githubAuthService.getGitUserInfo(tokenWrapper.getResult());
            if (gitUserInfo.success()) {
                //根据openId去找关联的用户
                logger.info("GitHub用户信息:{}", gitUserInfo.getResult());
                return "redirect:/login";
            }
        }
        return "redirect:http://localhost/giterror.html";
    }
}
