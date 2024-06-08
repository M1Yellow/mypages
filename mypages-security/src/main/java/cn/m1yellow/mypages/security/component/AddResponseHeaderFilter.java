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
 * <b>根据不同的请求类型，设置 header 参数</b><br>
 * 拦截器 (Interceptor) 与过滤器 (Filter) 的区别
 * https://segmentfault.com/a/1190000022833940
 * 过滤器 和 拦截器 均体现了AOP的编程思想，都可以实现诸如日志记录、登录鉴权等功能
 * <p>
 * - 实现原理不同
 * 过滤器和拦截器底层实现方式大不相同，过滤器是基于函数回调的，拦截器则是基于Java的反射机制（动态代理）实现的。
 * <p>
 * - 使用范围不同
 * 过滤器实现的是 javax.servlet.Filter 接口，而这个接口是在Servlet规范中定义的，也就是说过滤器Filter 的使用要依赖于Tomcat等容器，导致它只能在web程序中使用。
 * 拦截器(Interceptor) 它是一个Spring组件，并由Spring容器管理，并不依赖Tomcat等容器，是可以单独使用的。不仅能应用在web程序中，也可以用于Application、Swing等程序中。
 * <p>
 * - 触发时机不同
 * Tomcat - Filter - Servlet - Interceptor - Controller
 * 过滤器Filter是在请求进入容器后，但在进入servlet之前进行预处理，请求结束是在servlet处理完以后。
 * 拦截器 Interceptor 是在请求进入servlet后，在进入Controller之前进行预处理的，Controller 中渲染了对应的视图之后请求结束。
 * <p>
 * - 拦截的请求范围不同
 * 过滤器几乎可以对所有进入容器的请求起作用，而拦截器只会对Controller中请求或访问static目录下的资源请求起作用。
 * 典型例子：favicon.ico 会导致 Filter 执行两次
 * <p>
 * - 注入Bean情况不同
 * 在实际的业务场景中，应用到过滤器或拦截器，为处理业务逻辑难免会引入一些service服务。
 * Filter 直接 @Autowired 注入就能使用
 * Interceptor 注入使用拿到的是 null，因为加载顺序导致的问题，拦截器加载的时间点在 spring context 之前，而Bean又是由spring进行管理，需要提前手动注入 bean
 * <p>
 * - 控制执行顺序不同
 * 实际开发过程中，会出现多个过滤器或拦截器同时存在的情况，不过，有时希望某个过滤器或拦截器能优先执行，就涉及到它们的执行顺序。
 * 过滤器用@Order注解控制执行顺序，通过@Order控制过滤器的级别，值越小级别越高越先执行。
 * <p>
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
