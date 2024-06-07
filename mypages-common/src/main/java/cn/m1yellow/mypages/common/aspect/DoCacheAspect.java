package cn.m1yellow.mypages.common.aspect;

import cn.m1yellow.mypages.common.api.ResultCode;
import cn.m1yellow.mypages.common.constant.GlobalConstant;
import cn.m1yellow.mypages.common.util.ObjectUtil;
import cn.m1yellow.mypages.common.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


/**
 * 通过切面拦截，处理一些业务
 */
@Slf4j
@Aspect
@Component
//@Profile({"dev", "test"})
public class DoCacheAspect {

    /**
     * 换行符
     */
    private static final String LINE_SEPARATOR = System.lineSeparator();
    /**
     * 方法是否出现异常
     */
    private static ThreadLocal<Boolean> localExceptionFlag = new ThreadLocal();


    @Autowired
    private RedisUtil redisUtil;


    /**
     * 以自定义 @WebLog 注解为切点
     */
    @Pointcut("@annotation(cn.m1yellow.mypages.common.aspect.DoCache)")
    public void DoCache() {
    }


    // 切面方法执行顺序，@Around -> @Before -> 业务代码 -> @AfterThrowing -> @After -> @AfterReturning

    @AfterThrowing("DoCache()")
    public void doAfterThrowing(JoinPoint joinPoint) throws Throwable {
        localExceptionFlag.set(true);
    }

    /**
     * 方法执行返回，执行切面任务，注意，如果方法抛异常，是没有返回的，这个方法不会执行。不过正好“曲线救国”，达到出现异常不处理缓存的目的
     *
     * @param joinPoint
     * @param rtv
     */
    @AfterReturning(returning = "rtv", pointcut = "DoCache()")
    public void doAfterReturning(JoinPoint joinPoint, Object rtv) {

        Boolean isException = localExceptionFlag.get();
        localExceptionFlag.remove();
        if (null != isException && isException) {
            log.info(">>>> 方法出现异常，不进行缓存操作");
            return;
        }

        // 从返回结果中取 code
        Long code = null;
        try {
            Field field = rtv.getClass().getDeclaredField("code");
            if (null != field) {
                // 设置对象的访问权限，保证对 private 的属性的访问
                field.setAccessible(true);
                Object codeObj = field.get(rtv);
                field.setAccessible(false);
                if (null != codeObj) {
                    code = Long.parseLong(ObjectUtil.getString(codeObj));
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        if (null != code && !code.equals(ResultCode.SUCCESS.getCode())) {
            log.info(">>>> 方法未执行成功，不进行缓存操作");
            return;
        }

        // 清除默认首页内容缓存（未登录状态）
        String cacheKey = GlobalConstant.HOME_PLATFORM_LIST_DEFAULT_CACHE_KEY;
        redisUtil.del(cacheKey);
        log.info(">>>> DoCacheAspect 清除默认首页内容缓存（未登录状态），cache key: {}", cacheKey);

        // 从请求参数中获取 userId
        Long userId = null;
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String[] parameterNames = methodSignature.getParameterNames();
        //Class[] parameterTypes = methodSignature.getParameterTypes();
        Object[] args = joinPoint.getArgs();

        if (null == args || args.length < 1) {
            log.info(">>>> 参数列表为空");
            return;
        }

        // 获取 userId 的下标
        int userIdIndex = ArrayUtils.indexOf(parameterNames, "userId");
        if (userIdIndex < 0) {
            log.info(">>>> 请求参数 userId 未找到");
            //return;
        } else {
            userId = Long.parseLong(ObjectUtil.getString(args[userIdIndex]));
            if (null == userId) {
                log.info(">>>> 获取请求参数中的 userId 失败");
                //return;
            }
        }

        if (null == userId || userId < 1) {
            log.info(">>>> 进一步从请求对象中获取 userId");
            // 从参数封装对象中取 userId
            Object obj = null;
            Method method = null;
            for (int i = 0; i < args.length; i++) {
                try {
                    obj = args[i];
                    if (null == obj) continue;
                    // TODO 由于使用了 lombok，反射获取不到 get 方法
                    //method = obj.getClass().getDeclaredMethod("getUserId", Long.class); // java.lang.NoSuchMethodException
                    //method = obj.getClass().getMethod("getUserId", Long.class); // java.lang.NoSuchMethodException
                    /*
                    if (method) {
                        Object returnObj = method.invoke(obj);
                        if (null == returnObj) continue;
                        userId = Long.parseLong(ObjectUtil.getString(returnObj));
                    }
                    */
                    // 直接获取字段值
                    Field field = obj.getClass().getDeclaredField("userId");
                    if (null == field) continue;
                    // 设置对象的访问权限，保证对 private 的属性的访问
                    field.setAccessible(true);
                    Object returnObj = field.get(obj);
                    field.setAccessible(false);
                    if (null == returnObj) continue;
                    userId = Long.parseLong(ObjectUtil.getString(returnObj));
                    if (null != userId && userId > 0) {
                        break;
                    }
                } catch (Exception e) {
                    log.error(e.getMessage());
                }

            }
            if (null == userId || userId < 1) {
                log.info(">>>> 获取请求对象中的 userId 失败");
                return;
            }
        }

        // 清除登录用户首页内容缓存
        cacheKey = GlobalConstant.CACHE_USER_FOLLOWING + GlobalConstant.CACHE_NAME_KEY_SEPARATOR + GlobalConstant.HOME_PLATFORM_LIST_CACHE_KEY + userId;
        redisUtil.del(cacheKey);
        log.info(">>>> DoCacheAspect 清除登录用户首页内容缓存，cache key: {}", cacheKey);
    }

}
