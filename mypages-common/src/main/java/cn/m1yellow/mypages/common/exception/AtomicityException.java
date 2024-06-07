package cn.m1yellow.mypages.common.exception;


import cn.m1yellow.mypages.common.api.IErrorCode;

/**
 * 自定义方法原子性异常
 */
public class AtomicityException extends RuntimeException {

    private IErrorCode errorCode;

    public AtomicityException(IErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public AtomicityException(String message) {
        super(message);
    }

    public AtomicityException(Throwable cause) {
        super(cause);
    }

    public AtomicityException(String message, Throwable cause) {
        super(message, cause);
    }

    public IErrorCode getErrorCode() {
        return errorCode;
    }
}
