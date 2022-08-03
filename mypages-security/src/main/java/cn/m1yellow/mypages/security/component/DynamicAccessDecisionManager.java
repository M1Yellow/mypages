package cn.m1yellow.mypages.security.component;

import cn.m1yellow.mypages.common.api.ResultCode;
import cn.m1yellow.mypages.common.constant.GlobalConstant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Iterator;

/**
 * 动态权限决策管理器，用于判断用户是否有访问权限
 */
@Component
public class DynamicAccessDecisionManager implements AccessDecisionManager {

    /**
     * @param authentication   当前登录用户的角色信息
     * @param object           请求url信息
     * @param configAttributes `DynamicSecurityMetadataSource`中的 getAttributes 方法传来的，表示当前请求需要的角色（可能有多个）
     * @throws AccessDeniedException
     * @throws InsufficientAuthenticationException
     */
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
            throws AccessDeniedException, InsufficientAuthenticationException {

        // TODO 注意，当 authentication 为 null，或 configAttributes 为 null 或 size 小于 1 的时候，decide 方法不执行，即不进入这个方法
        // 当接口未被配置路径权限时直接放行
        if (null == configAttributes || configAttributes.size() < 1) { // configAttributes 是 DynamicSecurityMetadataSource getAttributes 传过来的角色标识列表
            return;
        }
        Iterator<ConfigAttribute> iterator = configAttributes.iterator();
        while (iterator.hasNext()) {
            ConfigAttribute configAttribute = iterator.next();
            // 将访问路径权限对应的角色，与用户拥有的角色进行比对
            String needAuthority = configAttribute.getAttribute();
            if (StringUtils.isBlank(needAuthority)) {
                continue;
            }
            if (GlobalConstant.NEED_LOGIN.equals(needAuthority)) { // 没有登录
                throw new InsufficientAuthenticationException(ResultCode.UNAUTHORIZED.getMessage());
            } else if (GlobalConstant.URI_NOT_FOUND.equals(needAuthority)) { // 路径权限未找到
                throw new AccessDeniedException(ResultCode.FORBIDDEN.getMessage());
            }

            // 遍历用户所具有的角色，逐个比对
            for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
                if (needAuthority.trim().equals(grantedAuthority.getAuthority())) {
                    // 命中一个即返回
                    return;
                }
            }
        }
        throw new AccessDeniedException(ResultCode.FORBIDDEN.getMessage());
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }

}
