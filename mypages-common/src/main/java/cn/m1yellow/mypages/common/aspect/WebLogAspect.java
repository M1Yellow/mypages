package cn.m1yellow.mypages.common.aspect;

import cn.m1yellow.mypages.common.util.HeaderUtil;
import cn.m1yellow.mypages.common.util.JSONUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;


/**
 * 切面日志
 * https://www.exception.site/springboot/spring-boot-aop-web-request
 */
@Slf4j
@Aspect
@Component
@Profile({"dev", "test"})
public class WebLogAspect {

    /**
     * 换行符
     */
    private static final String LINE_SEPARATOR = System.lineSeparator();


    /**
     * 以自定义 @WebLog 注解为切点
     */
    @Pointcut("@annotation(cn.m1yellow.mypages.common.aspect.WebLog)")
    public void webLog() {
    }


    // 切面方法执行顺序，@Around -> @Before -> 业务代码 -> @After -> @AfterThrowing -> @AfterReturning

    /**
     * 环绕
     *
     * @param proceedingJoinPoint
     * @return
     * @throws Throwable
     */
    @Around("webLog()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        // 打印出参
        log.info("Response Args  : {}", JSONUtil.toJSON(result));
        // 执行耗时
        log.info("Time-Consuming : {} ms", System.currentTimeMillis() - startTime);

        // 最后的结束标志
        log.info("==================== End ====================" + LINE_SEPARATOR);

        return result;
    }


    /**
     * 在切点之前织入
     *
     * @param joinPoint
     * @throws Throwable
     */
    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        // 开始打印请求日志
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 获取 @WebLog 注解的描述信息
        String methodDescription = getAspectLogDescription(joinPoint);

        // TODO 打印请求相关参数，PathVariable 存在问题？？
        log.info("==================== Start ====================");
        // 打印请求 url
        log.info("URL            : {}", request.getRequestURL().toString());
        // 打印描述信息
        log.info("Description    : {}", methodDescription);
        // 打印 Http method
        log.info("HTTP Method    : {}", request.getMethod());
        // 打印调用 controller 的全路径以及执行方法
        log.info("Class Method   : {}.{}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
        // 打印请求的 IP
        log.info("IP             : {}", HeaderUtil.getRequestIp(request));
        // 打印请求入参
        log.info("Request Args   : {}", JSONUtil.toJSON(joinPoint.getArgs()));
    }


    /**
     * 在切点之后织入
     *
     * @throws Throwable
     */
    @After("webLog()")
    public void doAfter() throws Throwable {
        // 接口结束后换行，方便分割查看
        //log.info("==================== End ====================" + LINE_SEPARATOR);
    }


    /**
     * 获取切面注解的描述
     *
     * @param joinPoint 切点
     * @return 描述信息
     * @throws Exception
     */
    public String getAspectLogDescription(JoinPoint joinPoint) throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        StringBuilder description = new StringBuilder("");
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    description.append(method.getAnnotation(WebLog.class).description());
                    if (StringUtils.isBlank(description.toString())) {
                        description.append(method.getAnnotation(ApiOperation.class).value());
                    }
                    break;
                }
            }
        }
        return description.toString();
    }

}
