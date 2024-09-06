package cn.m1yellow.mypages.common.exception;

import cn.m1yellow.mypages.common.api.CommonResult;
import cn.m1yellow.mypages.common.api.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * <b>全局异常处理</b>
 * TODO @ControllerAdvice + @ExceptionHandler 不能处理 404 异常、Servlet Filter 中的异常
 * 如果要调整 404 错误返回数据，则使用 ErrorController，
 * 但是，ErrorController 也是全局异常捕获，优先级更高，且跟这个业务重复了
 * <br>
 * 实践方案：
 * ErrorController 捕获全局代码异常
 * ExceptionHandler 处理自定义异常
 * <br>
 * <b>使用@ExceptionHandler 为什么无法处理404错误/异常？</b>
 * 因为SpringMVC优先处理（Try Catch）掉了资源映射不存在的404类错误/异常，虽然在响应信息注入了404的HttpStatus通信信息，
 * 没有了异常，肯定不会进入@ExceptionHandler 的处理逻辑。
 * <br>
 * <b>使用@ExceptionHandler + 抛出异常 是否可取？</b>
 * 通过取消资源目录映射来解决无404问题是不可取的，属于越俎代庖的做法。
 * spring.mvc.throw-exception-if-no-handler-found=true
 * spring.resources.add-mappings=false
 * <br>
 * <b>为什么推荐ErrorController 替代 @ExceptionHandler ?</b>
 * 使用ErrorController可以处理 全部错误/异常 。
 * 使用ErrorController+ErrorInfoBuilder 在单个方法里面可以针对不同的Exception来添加详细的错误信息，
 * 具体做法：拓展ErrorInfoBuilder的getErrorInfo方法来添加错误信息（例如：ex instanceof NullPointerException Set xxx）。
 * <br>
 * ExceptionHandler 能对应每个异常处理业务
 * ErrorController 不太清楚是否能细分异常处理
 * <br>
 * 局部异常处理 @Controller + @ExceptionHandler
 * 全局异常处理 @ControllerAdvice + @ExceptionHandler
 * <br>
 * 在同一个 Handler 异常处理类中，ExceptionHandler 存在一个异常被匹配多次的问题
 * 定义不同的 Handler 类，并通过  @Order(n) 指定处理顺序，n 越小越先处理。【但一个异常还是会被处理多次】
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 404 未找到资源 【存在一个异常被匹配多次的问题】
     * 需要在 application 配置文件中设置：
     * # 当 HTTP 状态码为 4xx 时直接抛出异常，日志能看到 NoHandlerFoundException，不优雅
     * spring.mvc.throw-exception-if-no-handler-found=true
     * # 关闭默认的静态资源路径映射
     * web.resources.add-mappings=false
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CommonResult processValidException(NoHandlerFoundException e) {
        log.error("404 未找到资源", e);
        return CommonResult.failed(ResultCode.NOT_FOUND);
    }

    /**
     * 统一处理参数校验错误异常（controller 请求接口参数数据绑定异常）
     */
    @ExceptionHandler(BindException.class)
    public CommonResult processValidException(BindException e) {
        log.error("参数数据绑定异常", e);
        return CommonResult.failed(ResultCode.VALIDATE_FAILED, e.getMessage());
    }

    /**
     * 统一处理参数校验错误异常
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public CommonResult processValidException(IllegalArgumentException e) {
        log.error("参数校验错误异常", e);
        return CommonResult.failed(ResultCode.VALIDATE_FAILED, e.getMessage());
    }

    /**
     * 自定义API异常
     */
    @ExceptionHandler(ApiException.class)
    public CommonResult handle(ApiException e) {
        log.error("API接口异常", e);
        if (null != e.getErrorCode()) {
            return CommonResult.failed(e.getErrorCode());
        }
        return CommonResult.failed(e.getMessage());
    }

    /**
     * 自定义文件保存异常，文件保存失败，抛出此异常，可以让方法中的数据库操作回滚
     */
    @ExceptionHandler(FileSaveException.class)
    public CommonResult handle(FileSaveException e) {
        log.error("文件保存异常", e);
        if (null != e.getErrorCode()) {
            return CommonResult.failed(e.getErrorCode());
        }
        return CommonResult.failed(e.getMessage());
    }

    /**
     * 自定义方法原子性操作异常，整个方法中途操作失败，可手动抛出此异常，中断操作，保证原子性
     */
    @ExceptionHandler(AtomicityException.class)
    public CommonResult handle(AtomicityException e) {
        log.error("原子操作异常", e);
        if (null != e.getErrorCode()) {
            return CommonResult.failed(e.getErrorCode());
        }
        return CommonResult.failed(e.getMessage());
    }

    /**
     * 其他程序异常
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResult handle(Exception e) {
        log.error("程序出现异常", e);
        return CommonResult.failed(ResultCode.FAILED);
    }

    /**
     * 其他系统错误
     */
    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResult handle(Throwable e) {
        log.error("系统出现错误", e);
        return CommonResult.failed(ResultCode.FAILED);
    }
}
