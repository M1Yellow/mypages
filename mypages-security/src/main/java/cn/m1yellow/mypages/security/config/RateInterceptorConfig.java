package cn.m1yellow.mypages.security.config;

import cn.m1yellow.mypages.security.component.RateLimitInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * Interceptor 只能在 spring mvc 项目中使用？
 * 非 MVC 项目，报 404，No mapping for GET /swagger-ui.html
 */
@Configuration
public class RateInterceptorConfig extends WebMvcConfigurationSupport {

    @Autowired
    private RateLimitInterceptor rateLimitInterceptor;
    @Autowired
    private IgnoreUrlsConfig ignoreUrlsConfig;

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {

        //定义排除swagger访问的路径配置
        String[] swaggerExcludes = new String[]{
                "/swagger-ui.html",
                "/swagger-resources/**",
                "/**/v2/api-docs",
                "/swagger/**",
                "/webjars/**"
        };
        registry.addInterceptor(rateLimitInterceptor).addPathPatterns("/**").excludePathPatterns(swaggerExcludes);

        /*
        InterceptorRegistration interceptorRegistration = registry.addInterceptor(rateLimitInterceptor);
        interceptorRegistration.addPathPatterns("/**");
        // 白名单请求直接放行
        for (String path : ignoreUrlsConfig.getUrls()) {
            interceptorRegistration.excludePathPatterns(path);
        }
        super.addInterceptors(registry); // 不加也没问题
        */
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 必须添加
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");

        //super.addResourceHandlers(registry); // 不加也没问题
    }
}
