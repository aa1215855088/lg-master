package com.lg.user.web.auth;

import com.lg.commons.util.wrapper.Wrapper;
import com.lg.user.model.domain.TbUser;

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
 * @create: 2019-02-28 09:29
 **/
public interface AuthService {

    /**
     * 根据code获得Token
     *
     * @param code code
     * @return token
     */
    Wrapper<String> getAccessToken(String code);

    /**
     * 根据Token获得OpenId
     *
     * @param accessToken Token
     * @return openId
     */
    Wrapper<String> getOpenId(String accessToken);

    /**
     * 刷新Token
     *
     * @param code code
     * @return 新的token
     */
    Wrapper<String> refreshToken(String code);

    /**
     * 拼接授权URL
     *
     * @return URL
     */
    Wrapper<String> getAuthorizationUrl();

    /**
     * 根据Token和OpenId获得用户信息
     *
     * @param accessToken Token
     * @param openId      openId
     * @return 第三方应用给的用户信息
     */
    Wrapper<TbUser> getUserInfo(String accessToken, String openId);
}
