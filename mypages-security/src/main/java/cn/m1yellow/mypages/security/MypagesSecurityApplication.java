package cn.m1yellow.mypages.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * TODO 跨模块依赖注入问题：
 * security 模块依赖 common 模块，两个模块的包名不一样，cn.m1yellow.mypages.security 与 cn.m1yellow.mypages.common
 * 这时候，使用 @Autowired 报错无法注入，@Resource 本地运行没问题，部署到 tomcat，报 NameNotFoundException 异常
 * 解决方式：在添加依赖的模块启动类，手动配置 scanBasePackages 包扫描，扩大到两个模块相同的目录即可
 */
@SpringBootApplication(scanBasePackages = {"cn.m1yellow.mypages"})
public class MypagesSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(MypagesSecurityApplication.class, args);
    }

}
