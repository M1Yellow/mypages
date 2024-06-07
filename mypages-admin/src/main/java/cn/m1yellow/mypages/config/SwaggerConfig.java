package cn.m1yellow.mypages.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.spring.web.plugins.WebFluxRequestHandlerProvider;
import springfox.documentation.spring.web.plugins.WebMvcRequestHandlerProvider;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

//@EnableSwagger2 // 开启 Swagger2
@EnableOpenApi // 开启 Swagger3
@EnableWebMvc
@Configuration
public class SwaggerConfig implements WebMvcConfigurer {

    // Swagger2
    // http://localhost:8081/swagger-ui.html
    // Swagger3
    // http://localhost:8081/swagger-ui/index.html

    public static final String SWAGGER_SCAN_BASE_PACKAGE = "cn.m1yellow.mypages.controller";
    public static final String VERSION = "1.0.0";

    // TODO 如需配置多个分组，复制 ApiInfo 及 Docket，修改配置即可

    ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("MyPages API")
                .description("Swagger MyPages API 接口信息。")
                .license("Apache2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .termsOfServiceUrl("")
                .version(VERSION)
                .contact(new Contact("M1Yellow", "http://www.m1yellow.cn", "m1yellow@163.com"))
                .build();
    }

    @Bean
    public Docket docket(Environment environment) {
        // 判断只在开发和测试环境开启
        Profiles profiles = Profiles.of("dev", "test");
        boolean enableSwagger = environment.acceptsProfiles(profiles);

        //return new Docket(DocumentationType.SWAGGER_2)
        return new Docket(DocumentationType.OAS_30) // Swagger3
                .apiInfo(apiInfo())
                .enable(enableSwagger) // 是否开启 Swagger
                .groupName("mypages") // 分组名称
                .select()
                //.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .apis(RequestHandlerSelectors.basePackage(SWAGGER_SCAN_BASE_PACKAGE))
                .paths(PathSelectors.any()) // 正则匹配请求路径，并分配至当前分组，当前所有接口
                .build()
                // 添加登录认证
                .securityContexts(securityContexts())
                .securitySchemes(securitySchemes());
    }

    /**
     * 通过 securitySchemes 来配置全局参数
     * 这里配置是一个名为 Authorization 的请求头
     */
    private List<SecurityScheme> securitySchemes() {
        List<SecurityScheme> schemes = new ArrayList<>();
        ApiKey apiKey = new ApiKey("Authorization", "Authorization", "header");
        schemes.add(apiKey);
        return schemes;
    }

    /**
     * 配置哪些请求需要携带 Token
     */
    private List<SecurityContext> securityContexts() {
        List<SecurityContext> contexts = new ArrayList<>();
        contexts.add(getContextByPath("/*/.*"));
        return contexts;
    }

    private SecurityContext getContextByPath(String pathRegex) {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .operationSelector(oc -> oc.requestMappingPattern().matches(pathRegex))
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("Swagger", "Swagger-header");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("Authorization", authorizationScopes));
    }


    // 添加MVC配置，解决升级 Swagger3 /swagger-ui/index.html 报错 404
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/swagger-ui/").setViewName("/swagger-ui/index.html");
    }

    /**
     * 解决 springboot 2.7.7 security 报错问题
     */
    @Bean
    public static BeanPostProcessor springfoxHandlerProviderBeanPostProcessor() {
        return new BeanPostProcessor() {

            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                if (bean instanceof WebMvcRequestHandlerProvider || bean instanceof WebFluxRequestHandlerProvider) {
                    customizeSpringfoxHandlerMappings(getHandlerMappings(bean));
                }
                return bean;
            }

            private <T extends RequestMappingInfoHandlerMapping> void customizeSpringfoxHandlerMappings(List<T> mappings) {
                List<T> copy = mappings.stream()
                        .filter(mapping -> null == mapping.getPatternParser())
                        .collect(Collectors.toList());
                mappings.clear();
                mappings.addAll(copy);
            }

            @SuppressWarnings("unchecked")
            private List<RequestMappingInfoHandlerMapping> getHandlerMappings(Object bean) {
                try {
                    Field field = ReflectionUtils.findField(bean.getClass(), "handlerMappings");
                    field.setAccessible(true);
                    return (List<RequestMappingInfoHandlerMapping>) field.get(bean);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    throw new IllegalStateException(e);
                }
            }
        };
    }

}
