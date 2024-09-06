package cn.m1yellow.mypages.security.component;

import cn.m1yellow.mypages.common.constant.GlobalConstant;
import cn.m1yellow.mypages.common.constant.Headers;
import cn.m1yellow.mypages.common.util.ObjectUtil;
import cn.m1yellow.mypages.security.service.SysConfigService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.util.PathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;


/**
 * <b>根据不同的请求类型，设置 header 参数</b>
 * <br>
 * 拦截器 (Interceptor) 与过滤器 (Filter) 的区别
 * https://www.cnblogs.com/leizia/p/18079242
 * <br>
 * 过滤器适用于对请求和响应的全局处理，拦截器适用于对特定请求的处理。<br>
 * 拦截器默认的执行顺序，就是它的注册顺序，也可以通过Order手动设置控制，值越小越先执行。
 */
@Slf4j
@Order(Ordered.LOWEST_PRECEDENCE)
@Component
public class AddResponseHeaderFilter extends OncePerRequestFilter {

    @Autowired
    private SysConfigService sysConfigService;


    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {

        // 如果是静态资源请求，设置缓存，普通接口请求，不缓存
        boolean isResourceRequest = false;
        PathMatcher pathMatcher = new AntPathMatcher();
        String uri = httpServletRequest.getRequestURI();
        for (String path : GlobalConstant.STATIC_RESOURCES) {
            if (pathMatcher.match(path, uri)) {
                isResourceRequest = true;
                break;
            }
        }

        // 获取数据库配置的过期时间
        String maxAge = Headers.CACHE_CONTROL_MAX_AGE.getHeadValues();
        Map<String, Object> sysConfigMap = sysConfigService.getSysConfigs();
        if (!CollectionUtils.isEmpty(sysConfigMap)) {
            maxAge = ObjectUtil.getString(sysConfigMap.get("cache_control_max_age"));
            if (StringUtils.isBlank(maxAge)) {
                maxAge = Headers.CACHE_CONTROL_MAX_AGE.getHeadValues();
            }
        }
        // TODO 准备 header Cache-Control 的值，格式："public, max-age=600"
        String cacheControlValues = Headers.CACHE_CONTROL_PUBLIC.getHeadValues() + ", max-age=" + maxAge;

        if (isResourceRequest) { // 静态资源，设置缓存
            httpServletResponse.addHeader(Headers.ACCESS_CONTROL_ALLOW_ORIGIN.getHeadName(), Headers.ACCESS_CONTROL_ALLOW_ORIGIN.getHeadValues());
            httpServletResponse.addHeader(Headers.ACCESS_CONTROL_ALLOW_CREDENTIALS.getHeadName(), Headers.ACCESS_CONTROL_ALLOW_CREDENTIALS.getHeadValues());
            httpServletResponse.addHeader(Headers.ACCESS_CONTROL_ALLOW_METHODS.getHeadName(), Headers.ACCESS_CONTROL_ALLOW_METHODS.getHeadValues());
            httpServletResponse.addHeader(Headers.ACCESS_CONTROL_MAX_AGE.getHeadName(), Headers.ACCESS_CONTROL_MAX_AGE.getHeadValues());
            httpServletResponse.addHeader(Headers.CACHE_CONTROL_ALLOW_CACHED.getHeadName(), cacheControlValues);
        } else { // 接口请求，禁用缓存
            httpServletResponse.addHeader(Headers.CACHE_CONTROL_NOT_ALLOW_CACHED.getHeadName(), Headers.CACHE_CONTROL_NOT_ALLOW_CACHED.getHeadValues());
            httpServletResponse.addHeader(Headers.PRAGMA.getHeadName(), Headers.PRAGMA.getHeadValues());
        }

        // 程序往下继续执行
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

}
