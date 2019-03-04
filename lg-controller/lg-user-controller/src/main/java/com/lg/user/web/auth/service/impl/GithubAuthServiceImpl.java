package com.lg.user.web.auth.service.impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.lg.commons.util.wrapper.WrapMapper;
import com.lg.commons.util.wrapper.Wrapper;
import com.lg.user.model.domain.TbUser;
import com.lg.user.web.auth.DefaultAuthServiceImpl;
import com.lg.user.web.auth.domain.GitUser;
import com.lg.user.web.auth.service.GithubAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

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
 * @create: 2019-02-28 09:32
 **/
@Component
@Slf4j
public class GithubAuthServiceImpl extends DefaultAuthServiceImpl implements GithubAuthService {

    private static final String AUTHORIZE_URL = "https://github.com/login/oauth/authorize?client_id=%s&redirect_uri" +
            "=%s&state=%s";
    private static final String ACCESS_TOKEN_URL = "https://github.com/login/oauth/access_token?client_id=%s" +
            "&client_secret=%s&code=%s&redirect_uri=%s&state=%s";
    private static final String USER_INFO_URL = "https://api.github.com/user?access_token=%s";
    // 下面的属性可以通过配置读取
    //回调地址
    private static final String API_KEY = "15d3f8ca720469198603";
    //Client ID
    private static final String API_SECRET = "774829461eeeaed127a4a2521b4c963204bf1b4a";
    //Client Secret
    private static final String CALLBACK_URL = "http://localhost:9090/auth/oauth/github/callback";
    //state，随便填，会返回原值给你
    private static final String GITHUB_STATE = "use-login";

    //此处是获取key-value类型的参数
    private Map<String, String> getParam(String string) {
        Map<String, String> map = new HashMap();
        String[] kvArray = string.split("&");
        for (int i = 0; i < kvArray.length; i++) {
            String[] kv = kvArray[i].split("=");
            if (kv.length == 2) {
                map.put(kv[0], kv[1]);
            } else if (kv.length == 1) {
                map.put(kv[0], "");
            }
        }
        return map;
    }


    @Override
    public Wrapper<String> getAccessToken(String code) {
        String url = String.format(ACCESS_TOKEN_URL, API_KEY, API_SECRET, code, CALLBACK_URL, GITHUB_STATE);
        String resp;
        try {
            resp = HttpUtil.post(url, "");
        } catch (Exception e) {
            log.error("Github获得access_token失败，code不正确或者已过期, cause:{}", e);
            return WrapMapper.error("code无效");
        }
        if (resp != null && resp.contains("access_token")) {
            Map<String, String> map = getParam(resp);
            String accessToken = map.get("access_token");
            return WrapMapper.ok(accessToken);
        }
        log.error("GitHub登录失败，code不正确或者已过期：");
        if (resp != null && resp.contains("<!DOCTYPE html>")) {
            return WrapMapper.error(resp);
        }
        return WrapMapper.error("code无效");
    }

    @Override
    public Wrapper<String> getOpenId(String accessToken) {
        String url = String.format(USER_INFO_URL, accessToken);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        URI uri = builder.build().encode().toUri();
        String resp;
        try {
            resp = getRestTemplate().getForObject(uri, String.class);
        } catch (Exception e) {
            log.error("GitHub获得OpenId失败，access_token无效, cause:{}", e);
            return WrapMapper.error("access_token无效！");
        }
        if (resp != null && resp.contains("id")) {
            JSONObject data = JSONObject.parseObject(resp);
            String openid = data.getString("id");
            return WrapMapper.ok(openid);
        }
        return WrapMapper.error("access_token无效！");
    }

    @Override
    public Wrapper<String> refreshToken(String code) {
        return null;
    }

    @Override
    public Wrapper<String> getAuthorizationUrl() {
        String url = String.format(AUTHORIZE_URL, API_KEY, CALLBACK_URL, GITHUB_STATE);
        return WrapMapper.ok(url);
    }

    @Override
    public Wrapper<TbUser> getUserInfo(String accessToken, String openId) {
        return null;
    }

    @Override
    public Wrapper<GitUser> getGitUserInfo(String accessToken) {
        String url = String.format(USER_INFO_URL, accessToken);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        URI uri = builder.build().encode().toUri();
        String resp;
        try {
            resp = getRestTemplate().getForObject(uri, String.class);
        } catch (Exception e) {
            log.error("GitHub获得用户信息失败,access_token无效, cause:{}", e);
            return WrapMapper.error("access_token无效！");
        }
        if (resp != null && resp.contains("login")) {
            JSONObject data = JSONObject.parseObject(resp);
            GitUser gitUser = new GitUser();
            gitUser.setName(data.getString("name"));
            gitUser.setUserName(data.getString("login"));
            gitUser.setAvatarUrl(data.getString("avatar_url"));
            return WrapMapper.ok(gitUser);
        }
        return WrapMapper.error("access_token无效！");
    }
}
