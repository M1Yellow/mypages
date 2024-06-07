package cn.m1yellow.mypages.security.component;

import cn.m1yellow.mypages.security.bo.SecurityUser;
import cn.m1yellow.mypages.security.config.IgnoreUrlsConfig;
import cn.m1yellow.mypages.security.config.JwtSecurityProperties;
import cn.m1yellow.mypages.security.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JWT 登录验证过滤器
 */
@Slf4j
@Order(1) // 数值越小，越先执行
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private JwtSecurityProperties jwtSecurityProperties;
    @Autowired
    private IgnoreUrlsConfig ignoreUrlsConfig;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {

        /**
         * HttpMethod: GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS, TRACE
         * OPTIONS请求方法的主要用途有两个：
         * 1、获取服务器支持的HTTP请求方法，所以可以用来探测。
         * 2、用来验证接口功能，也就是提前验证一下。
         */
        // OPTIONS 请求直接放行
        if (request.getMethod().equals(HttpMethod.OPTIONS.toString())) {
            chain.doFilter(request, response);
            return;
        }

        // 白名单请求直接放行
        PathMatcher pathMatcher = new AntPathMatcher();
        String uri = request.getRequestURI();
        for (String path : ignoreUrlsConfig.getUrls()) {
            if (pathMatcher.match(path, uri)) {
                chain.doFilter(request, response);
                return;
            }
        }

        // 获取 auth 标识：Authorization
        String authHeader = request.getHeader(jwtSecurityProperties.getTokenHeader());
        if (StringUtils.isBlank(authHeader) || !authHeader.startsWith(jwtSecurityProperties.getTokenStart())) {
            chain.doFilter(request, response);
            return;
        }

        // 获取 "Bearer " 之后的真正 token
        String authToken = authHeader.substring(jwtSecurityProperties.getTokenStart().length());
        if (StringUtils.isBlank(authToken)) {
            chain.doFilter(request, response);
            return;
        }

        // 从 token 中获取用户名
        String username = jwtTokenUtil.getUserNameFromToken(authToken);
        log.info(">>>> check token username: {}", username);
        if (StringUtils.isBlank(username) || null != SecurityContextHolder.getContext().getAuthentication()) {
            chain.doFilter(request, response);
            return;
        }

        // TODO 获取请求参数中的 userId（个别方法可能没有这个参数），用于校验 token 用户身份
        //  避免拿别人的 token 自己用，或者拿自己的 token 操作别人的数据。
        //  也可以把 userId 存 jwt 参数，过滤器从 token 中取 userId，放入 request，后续接口统一从 request 中取 userId，这样能保证用户身份统一
        String userIdStr = request.getParameter("userId");
        Long userId = null;
        if (StringUtils.isNotBlank(userIdStr)) {
            try {
                userId = Long.parseLong(userIdStr);
            } catch (NumberFormatException e) {
                log.error(">>>> get userId from request params exception: {}", e.getMessage());
            }
        }

        // spring security 用户详情对象封装
        SecurityUser userDetails = (SecurityUser) userDetailsService.loadUserByUsername(username);
        if (null == userDetails || null == userDetails.getUserBase()) {
            chain.doFilter(request, response);
            return;
        }
        // 设置要校验的 userId
        if (null != userId) {
            userDetails.getUserBase().setId(userId);
        }

        // 验证用户 token
        if (jwtTokenUtil.validateUserToken(authToken, userDetails)) {
            // spring security 用户名和密码校验规则
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            log.info(">>>> authenticated user: {}", username);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // 放行，继续往下执行
        chain.doFilter(request, response);
    }
}
