package cn.m1yellow.mypages.common.exception;


import cn.m1yellow.mypages.common.api.IErrorCode;

/**
 * 自定义文件保存异常
 */
public class FileSaveException extends RuntimeException {

    private IErrorCode errorCode;

    public FileSaveException(IErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public FileSaveException(String message) {
        super(message);
    }

    public FileSaveException(Throwable cause) {
        super(cause);
    }

    public FileSaveException(String message, Throwable cause) {
        super(message, cause);
    }

    public IErrorCode getErrorCode() {
        return errorCode;
    }
}
