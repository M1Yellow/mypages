package cn.m1yellow.mypages.common.aspect;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RateLimit {

	/** 周期，单位秒 */
	int cycle() default 5;

	/** 请求次数 */
	int number() default 1;

	/** 默认提示信息 */
	String msg() default "请求过于频繁";
}
