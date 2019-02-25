package com.lg.biz.web.security;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lg.biz.model.domain.TbSeller;
import com.lg.biz.service.TbSellerService;
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
 * @create: 2019-02-24 16:11
 **/
@Component("userDetailsService")
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    @Reference
    private TbSellerService sellerService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("商家:[{}]开始登录", username);
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        TbSeller seller = this.sellerService.findByLoginName(username);
        if (seller == null) {
            log.info("商家:[{}]不存在", username);
            throw new UsernameNotFoundException("用户名不存在或者密码错误");
        }


        return new SellerUserInfo(username, seller.getPassword(), seller.getName(),grantedAuthorities);
    }
}
