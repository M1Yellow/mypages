package cn.m1yellow.mypages.common.exception;

import cn.m1yellow.mypages.common.api.CommonResult;
import cn.m1yellow.mypages.common.api.ResultCode;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

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
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 统一处理参数校验错误异常（controller 请求接口参数数据绑定异常）
     *
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(BindException.class)
    public CommonResult processValidException(BindException e) {
        return CommonResult.failed(ResultCode.VALIDATE_FAILED, e.getMessage());
    }

    /**
     * 统一处理参数校验错误异常
     *
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(IllegalArgumentException.class)
    public CommonResult processValidException(IllegalArgumentException e) {
        return CommonResult.failed(ResultCode.VALIDATE_FAILED, e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = ApiException.class)
    public CommonResult handle(ApiException e) {
        if (e.getErrorCode() != null) {
            return CommonResult.failed(e.getErrorCode());
        }
        return CommonResult.failed(e.getMessage());
    }

    /**
     * 自定义文件保存异常，文件保存失败，抛出此异常，可以让方法中的数据库操作回滚
     *
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = FileSaveException.class)
    public CommonResult handle(FileSaveException e) {
        if (e.getErrorCode() != null) {
            return CommonResult.failed(e.getErrorCode());
        }
        return CommonResult.failed(e.getMessage());
    }

    /**
     * 自定义方法原子性操作异常，整个方法中途操作失败，可手动抛出此异常，中断操作，保证原子性
     *
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = AtomicityException.class)
    public CommonResult handle(AtomicityException e) {
        if (e.getErrorCode() != null) {
            return CommonResult.failed(e.getErrorCode());
        }
        return CommonResult.failed(e.getMessage());
    }

}
