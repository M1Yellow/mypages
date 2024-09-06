package cn.m1yellow.mypages.security.component;

import cn.m1yellow.mypages.common.api.CommonResult;
import cn.m1yellow.mypages.common.aspect.RateLimit;
import cn.m1yellow.mypages.common.util.HeaderUtil;
import cn.m1yellow.mypages.common.util.JSONUtil;
import cn.m1yellow.mypages.security.service.RateLimitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class RateLimitInterceptor implements HandlerInterceptor {

    @Autowired
    private RateLimitService rateLimitService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 判断请求是否属于方法的请求
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            // 获取方法中的注解,看是否有该注解
            RateLimit rateLimit = handlerMethod.getMethodAnnotation(RateLimit.class);
            if (null == rateLimit) {
                return true;
            }
            // 请求IP地址 -> 172.17.0.1
            String ip = HeaderUtil.getRequestIp(request);
            // 请求url路径 -> /user/login
            String uri = request.getRequestURI();
            if (!rateLimitService.limit(ip, uri, rateLimit)) {
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write(JSONUtil.toJSON(CommonResult.failed(rateLimit.msg())));
                response.setStatus(HttpStatus.OK.value());
                return false;
            }
        }
        return true;
    }
}
