package cn.m1yellow.mypages.common.aspect;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
@Order(Ordered.LOWEST_PRECEDENCE)
public @interface DoCache {

    /**
     * 描述信息
     *
     * @return
     */
    String description() default "";

}
