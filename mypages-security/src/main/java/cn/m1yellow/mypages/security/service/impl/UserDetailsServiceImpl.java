package cn.m1yellow.mypages.security.service.impl;

import cn.m1yellow.mypages.user.entity.UserBase;
import cn.m1yellow.mypages.user.service.UserBaseService;
import cn.m1yellow.mypages.security.bo.SecurityUser;
import cn.m1yellow.mypages.security.entity.SysRole;
import cn.m1yellow.mypages.security.service.SysUserRoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 自定义 UserDetailsServiceImpl 实现 spring security 的 UserDetailsService 接口
 */
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserBaseService userBaseService;
    @Autowired
    private SysUserRoleService userRoleService;


    @Override
    public SecurityUser loadUserByUsername(String username) throws UsernameNotFoundException {
        if (StringUtils.isBlank(username)) {
            return null;
        }
        UserBase userBase = userBaseService.getByUserName(username);
        if (null == userBase) {
            return null;
        }
        List<SysRole> roleList = userRoleService.queryRoleListByUserId(userBase.getId());

        return new SecurityUser(userBase, roleList);
    }
}
