package cn.m1yellow.mypages.security.component;

import cn.m1yellow.mypages.common.constant.GlobalConstant;
import cn.m1yellow.mypages.common.util.JSONUtil;
import cn.m1yellow.mypages.common.util.ObjectUtil;
import cn.m1yellow.mypages.common.util.RedisUtil;
import cn.m1yellow.mypages.security.config.IgnoreUrlsConfig;
import cn.m1yellow.mypages.security.entity.SysPermission;
import cn.m1yellow.mypages.security.entity.SysRole;
import cn.m1yellow.mypages.security.entity.SysRolePermission;
import cn.m1yellow.mypages.security.service.SysPermissionService;
import cn.m1yellow.mypages.security.service.SysRolePermissionService;
import cn.m1yellow.mypages.security.service.SysRoleService;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.util.PathMatcher;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 动态权限数据源<br>
 * TODO 权限校验规则：
 * 比对角色
 * 从用户角色表 user_role 查询到用户拥有的角色列表1
 * 根据请求 url 在 permission 路径权限表中找到对应的 url
 * 拿 url 到 角色权限表 role_permission 中查询对应的角色列表2
 * 最后比对 请求 url 所需权限对应的角色，有没有在用户拥有的角色列表当中，如果在，则说明有权限，反之则没有权限
 * <p>
 * （当然，也可以直接先查出用户拥有的全部 url 路径权限，再判断请求的 url 路径有没有在用户路径权限列表当中。
 * 但是，这种方式，如果用户有很多角色，每个角色又对应很多路径权限，会导致重复查询。）
 */
@Component
public class DynamicSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private SysRoleService roleService;
    @Autowired
    private SysPermissionService permissionService;
    @Autowired
    private SysRolePermissionService rolePermissionService;
    @Autowired
    private IgnoreUrlsConfig ignoreUrlsConfig;
    @Autowired
    private RedisUtil redisUtil;


    /**
     * 资源路径对应的角色列表
     */
    private Map<String, List<String>> resourceRolesMap;


    @PostConstruct
    public void initData() {
        // 初始化资源路径对应的角色列表
        resourceRolesMap = this.getResourceRolesMap();
    }


    /**
     * 获取自定义校验参数
     *
     * @param object
     * @return
     * @throws IllegalArgumentException
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {

        HttpServletRequest request = ((FilterInvocation) object).getRequest();

        // OPTIONS 请求直接放行
        if (request.getMethod().equals(HttpMethod.OPTIONS.toString())) {
            return null;
        }

        // 白名单请求直接放行
        String uri = request.getRequestURI();
        PathMatcher pathMatcher = new AntPathMatcher();
        for (String path : ignoreUrlsConfig.getUrls()) {
            if (pathMatcher.match(path, uri)) {
                return null;
            }
        }

        // 判断用户认证是否正确
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (null == authentication || authentication instanceof AnonymousAuthenticationToken) {
            return SecurityConfig.createList(GlobalConstant.NEED_LOGIN);
        }

        // 获取访问路径对应的角色列表
        List<String> roleList = resourceRolesMap.get(uri);
        if (!CollectionUtils.isEmpty(roleList)) {
            // 保存该 url 对应角色权限信息
            return SecurityConfig.createList(roleList.toArray(new String[roleList.size()]));
        }

        // 如果数据库中没有配置 url 权限，或者访问的 url 不在权限列表中
        return SecurityConfig.createList(GlobalConstant.URI_NOT_FOUND);
    }


    /**
     * 获取资源路径对应的角色列表
     *
     * @return
     */
    private Map<String, List<String>> getResourceRolesMap() {
        Map<String, List<String>> resourceRolesMap = new TreeMap<>(); // 不能设置 null，下方反序列化需要使用 resourceRolesMap.getClass()
        // 先从缓存中获取
        String resourceRolesMapStr = ObjectUtil.getString(redisUtil.get(GlobalConstant.RESOURCE_ROLES_MAP_KEY));
        if (StringUtils.isNotBlank(resourceRolesMapStr)) {
            resourceRolesMap = JSONUtil.toBean(resourceRolesMapStr, new TypeReference<Map<String, List<String>>>() {
            });
        }
        if (!CollectionUtils.isEmpty(resourceRolesMap)) {
            return resourceRolesMap;
        }
        if (null == resourceRolesMap) resourceRolesMap = new TreeMap<>();
        // 获取数据库中所有 url
        List<SysPermission> permissionList = permissionService.queryAllPermission();
        for (SysPermission permission : permissionList) {
            // 获取该 url 所对应的角色权限 role_permission，可能有多个
            Map<String, Object> params = new HashMap<>();
            params.put("permission_id", permission.getId());
            List<SysRolePermission> rolePermissionList = rolePermissionService.listByMap(params);
            List<String> roles = new ArrayList<>(); // 直接 new 吧，别拆开判断 if null 了，容易出错
            if (!CollectionUtils.isEmpty(rolePermissionList)) {
                for (SysRolePermission rolePermission : rolePermissionList) {
                    // 由角色权限查角色
                    Long roleId = rolePermission.getRoleId();
                    if (null != roleId) {
                        SysRole role = roleService.getById(roleId);
                        if (null != role)
                            roles.add(role.getCode()); // code like admin
                    }
                }
            }
            if (!CollectionUtils.isEmpty(roles)) {
                resourceRolesMap.put(permission.getUrl(), roles);
            }
        }

        if (!CollectionUtils.isEmpty(resourceRolesMap)) {
            //redisTemplate.opsForHash().putAll(RedisConstant.RESOURCE_ROLES_MAP, resourceRolesMap);
            redisUtil.set(GlobalConstant.RESOURCE_ROLES_MAP_KEY, JSONUtil.toJSON(resourceRolesMap));
        }

        return resourceRolesMap;
    }


    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }

}
