package cn.m1yellow.mypages.common.api;

/**
 * 枚举了一些常用API操作码
 * Created by macro on 2019/4/19.
 */
public enum ResultCode implements IErrorCode {

    SUCCESS(200, "操作成功"),
    FAILED(500, "服务器响应失败，请稍后重试"),
    VALIDATE_FAILED(400, "参数检验失败"),
    UNAUTHORIZED(401, "暂未登录/token已过期/token校验未通过"),
    FORBIDDEN(403, "没有访问权限"),
    NOT_FOUND(404, "未找到资源");


    private long code;
    private String message;

    private ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }

    public long getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static ResultCode getResultCodeByCode(long code) {
        for (ResultCode r : ResultCode.values()) {
            if (r.getCode() == code) {
                return r;
            }
        }
        return null;
    }
}
