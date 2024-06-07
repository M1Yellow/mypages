package cn.m1yellow.mypages.security.config;

import cn.m1yellow.mypages.security.component.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/**
 * Spring Security 配置类
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DynamicAccessDecisionManager dynamicAccessDecisionManager;
    @Autowired
    private DynamicSecurityMetadataSource dynamicSecurityMetadataSource;
    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
    @Autowired
    private JwtAccessDeniedHandler jwtAccessDeniedHandler;
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Autowired
    private IgnoreUrlsConfig ignoreUrlsConfig;


    @Bean
    public PasswordEncoder passwordEncoder() {
        //return new BCryptPasswordEncoder();
        //return PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return new MessageDigestPasswordEncoder("MD5");
    }


    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = httpSecurity.authorizeRequests();

        // 不需要保护的资源路径允许访问
        for (String url : ignoreUrlsConfig.getUrls()) {
            registry.antMatchers(url).permitAll();
        }

        // 允许跨域请求的 OPTIONS 请求
        registry.antMatchers(HttpMethod.OPTIONS).permitAll();
        // 允许 GET 请求，否则控制台会报错
        registry.antMatchers(HttpMethod.GET).permitAll();

        // 任何请求需要身份认证
        registry.and()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                //.and()
                //.formLogin() // 开启表单登录
                //.loginPage("/login.html") // 自定义登录页面
                //.loginProcessingUrl("xxx") // formLogin 中对应的 action 登录接口
                // 关闭跨站请求防护及不使用 session
                .and()
                .csrf()
                .disable()
                // JWT 不使用 HttpSession 会话
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                // 防止 iframe 造成跨域
                .and()
                .headers()
                .frameOptions()
                .disable() // 相当于 header 参数 "X-Frame-Options", "DENY"
                // 开启后端跨域请求支持
                .and()
                .cors()
                .and()
                .headers().cacheControl().disable() // 关掉默认缓存配置
                //.headers().defaultsDisabled() // 关掉默认缓存配置，这种方式启动会报错
                // 自定义认证失败处理类
                .and()
                .exceptionHandling()
                .accessDeniedHandler(jwtAccessDeniedHandler)
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                // 自定义认证过滤器（JWT 用户名和密码校验过滤器）
                .and()
                .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);


        /**
         * TODO 添加自定义的 headers 设置，但是如果 spring security 默认配置存在，不会覆盖，先把默认配置禁用
         * 注意，addHeaderWriter 这种方式会把所有请求的响应都加上 header 配置，导致普通接口请求响应也会被浏览器缓存，一个接口的结果也缓存？就离谱
         * 缓存使用自定义 Filter 过滤器的形式，根据不同请求，添加 header 参数，自定义过滤器 AddResponseHeaderFilter
         */

        /*
        httpSecurity.headers().addHeaderWriter(new StaticHeadersWriter(
                Arrays.asList(
                        //new Header("Access-Control-Allow-Origin", "http://192.168.10.118:8070"),
                        new Header(Headers.ACCESS_CONTROL_ALLOW_ORIGIN.getHeadName(), Headers.ACCESS_CONTROL_ALLOW_ORIGIN.getHeadValues()),
                        new Header(Headers.ACCESS_CONTROL_ALLOW_CREDENTIALS.getHeadName(), Headers.ACCESS_CONTROL_ALLOW_CREDENTIALS.getHeadValues()),
                        new Header(Headers.ACCESS_CONTROL_ALLOW_METHODS.getHeadName(), Headers.ACCESS_CONTROL_ALLOW_METHODS.getHeadValues()),
                        new Header(Headers.ACCESS_CONTROL_MAX_AGE.getHeadName(), Headers.ACCESS_CONTROL_MAX_AGE.getHeadValues()),
                        new Header(Headers.CACHE_CONTROL_ALLOW_CACHED.getHeadName(), Headers.CACHE_CONTROL_ALLOW_CACHED.getHeadValues())
                )
        ));
        */

        /*
        // 有动态权限配置时添加动态权限校验过滤器
        if (dynamicSecurityService) {
            // TODO 自定义 filter 会调用两次，并且不是 ico 图标的请求。换下方 withObjectPostProcessor 方案
            registry.and().addFilterBefore(dynamicSecurityFilter(), FilterSecurityInterceptor.class);
        }
        */

        // url 动态权限认证处理
        registry.withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
            @Override
            public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                o.setSecurityMetadataSource(dynamicSecurityMetadataSource);
                o.setAccessDecisionManager(dynamicAccessDecisionManager);
                return o;
            }
        });

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
        /*
        // TODO 自定义过滤器在登录认证界面使用的用户名和密码，也可以使用配置文件配置
        auth.inMemoryAuthentication()
                .withUser("admin")
                .password("123456").roles("admin");
        */
    }

}
