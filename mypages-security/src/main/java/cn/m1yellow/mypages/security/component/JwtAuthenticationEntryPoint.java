package cn.m1yellow.mypages.security.component;

import cn.m1yellow.mypages.common.api.CommonResult;
import cn.m1yellow.mypages.common.constant.Headers;
import cn.m1yellow.mypages.common.util.JSONUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证失败，自定义返回结果
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException e) throws IOException, ServletException {

        response.setHeader(Headers.ACCESS_CONTROL_ALLOW_ORIGIN.getHeadName(), Headers.ACCESS_CONTROL_ALLOW_ORIGIN.getHeadValues());
        response.setHeader(Headers.CACHE_CONTROL_NOT_ALLOW_CACHED.getHeadName(), Headers.CACHE_CONTROL_NOT_ALLOW_CACHED.getHeadValues());
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().println(JSONUtil.toJSON(CommonResult.unauthorized(e.getMessage())));
        response.getWriter().flush();
    }
}
