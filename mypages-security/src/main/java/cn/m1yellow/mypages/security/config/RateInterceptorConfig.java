package cn.m1yellow.mypages.security.config;

import cn.m1yellow.mypages.security.component.RateLimitInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class RateInterceptorConfig extends WebMvcConfigurationSupport {

    @Autowired
    private RateLimitInterceptor rateLimitInterceptor;
    @Autowired
    private IgnoreUrlsConfig ignoreUrlsConfig;

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {

        // 定义排除静态资源访问的路径配置
        String[] staticExcludes = new String[]{
                "/swagger-ui.html",
                "/swagger-resources/**",
                "/**/v2/api-docs",
                "/swagger/**",
                "/webjars/**",
                "/favicon.ico",
                "/images/**",
                "/css/**",
                "/js/**",
                "/fonts/**",
                "/druid/**"
        };

        registry.addInterceptor(rateLimitInterceptor).addPathPatterns("/**")
                .excludePathPatterns(staticExcludes);

        /*
        InterceptorRegistration interceptorRegistration = registry.addInterceptor(rateLimitInterceptor);
        interceptorRegistration.addPathPatterns("/**");
        // 白名单请求直接放行
        for (String path : ignoreUrlsConfig.getUrls()) {
            interceptorRegistration.excludePathPatterns(path);
        }
        super.addInterceptors(registry);
        */
    }

    /**
     * 静态资源访问
     * spring mvc yml 配置了，但是没有生效，使用代码配置生效
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(
                "swagger-ui.html",
                "/webjars/**",
                "/images/**",
                "/css/**",
                "/js/**",
                "/fonts/**"
        ).addResourceLocations(
                "classpath:/META-INF/resources/",
                "classpath:/META-INF/resources/webjars/",
                // 指定 /public/ 无法访问
                "classpath:/public/images/"
        );

        super.addResourceHandlers(registry);
    }

    /**
     * 解决跨域问题
     * 源（origin）就是协议、域名和端口号。
     * URL由协议、域名、端口和路径组成，如果两个URL的协议、域名和端口全部相同，
     * 则表示他们同源。否则，只要协议、域名、端口有任何一个不同，就是跨域
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/*")
                .allowedOrigins("*")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "DELETE", "PUT", "PATCH")
                .maxAge(3600);
    }

}
