package com.lg.seckill.web.config;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lg.user.model.domain.TbUser;
import com.lg.user.service.TbUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

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
 * @create: 2019-02-28 16:26
 **/
@Component
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    @Reference(version = "1.0.0")
    private TbUserService tbUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("用户:[{}]开始登录", username);
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        TbUser user = this.tbUserService.findByLoginName(username);
        if (user == null) {
            log.info("用户:[{}]不存在", username);
            throw new UsernameNotFoundException("用户名不存在或者密码错误");
        }
        return new User(username, user.getPassword(), grantedAuthorities);
    }
}
