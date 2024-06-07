package cn.m1yellow.mypages.security.bo;

import cn.m1yellow.mypages.security.entity.SysRole;
import cn.m1yellow.mypages.user.entity.UserBase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 自定义 SecurityUser 实现 spring security 的 UserDetails 认证用户详情
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SecurityUser implements UserDetails {

    /**
     * 用户
     */
    private transient UserBase userBase;

    /**
     * 角色
     */
    private transient List<SysRole> roleList;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        if (!CollectionUtils.isEmpty(this.roleList)) {
            for (SysRole role : this.roleList) {
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.getCode()); // role.getCode() like admin
                authorities.add(authority);
            }
        }
        return authorities;
    }


    public Long getUserId() {
        return userBase.getId();
    }

    @Override
    public String getPassword() {
        return userBase.getPassword();
    }

    @Override
    public String getUsername() {
        return userBase.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // 锁定时间，null-未锁定；当前时间之前-锁定；当前时间之后-待锁定
        final LocalDateTime lockTime = userBase.getLockTime();
        final LocalDateTime currDate = LocalDateTime.now();
        if (null == lockTime) {
            return true;
        } else if (lockTime.isAfter(currDate)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
