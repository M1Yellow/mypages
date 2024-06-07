package cn.m1yellow.mypages.security.component;

import cn.m1yellow.mypages.common.api.CommonResult;
import cn.m1yellow.mypages.common.constant.Headers;
import cn.m1yellow.mypages.common.util.JSONUtil;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 无权限访问，自定义返回结果
 */
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException e) throws IOException, ServletException {

        response.setHeader(Headers.ACCESS_CONTROL_ALLOW_ORIGIN.getHeadName(), Headers.ACCESS_CONTROL_ALLOW_ORIGIN.getHeadValues());
        response.setHeader(Headers.CACHE_CONTROL_NOT_ALLOW_CACHED.getHeadName(), Headers.CACHE_CONTROL_NOT_ALLOW_CACHED.getHeadValues());
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().println(JSONUtil.toJSON(CommonResult.forbidden(e.getMessage())));
        response.getWriter().flush();
    }
}
