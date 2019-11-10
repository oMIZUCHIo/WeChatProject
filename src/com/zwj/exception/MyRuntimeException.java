package com.zwj.exception;

public class MyRuntimeException extends RuntimeException {

    // 错误码
    private String code;

    // 描述
    private String description;


    public ErrorCodeEnum errorCodeEnum;

    public MyRuntimeException(final ErrorCodeEnum errorCodeEnum) {
        super(errorCodeEnum.getDescription());
        this.code = errorCodeEnum.getCode();
        this.description = errorCodeEnum.getDescription();
        this.errorCodeEnum = errorCodeEnum;
    }

    public MyRuntimeException(final String detailedMessage) {
        super(detailedMessage);
        this.errorCodeEnum = ErrorCodeEnum.UNKNOWN_ERROR;
    }

    public MyRuntimeException(final String code, final String detailedMessage) {
        super(detailedMessage);
        this.code = code;
        this.description = detailedMessage;
    }

    public MyRuntimeException(final Throwable t) {
        super(t);
        this.errorCodeEnum = ErrorCodeEnum.UNKNOWN_ERROR;
    }

    public MyRuntimeException(final ErrorCodeEnum errorCodeEnum, final Throwable t) {
        super(errorCodeEnum.getDescription(), t);
        this.errorCodeEnum = errorCodeEnum;
    }

    public MyRuntimeException(final String detailedMessage, final Throwable t) {
        super(detailedMessage, t);
        this.errorCodeEnum = ErrorCodeEnum.UNKNOWN_ERROR;
    }

    public ErrorCodeEnum getErrorCode() {
        return errorCodeEnum;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setErrorCodeEnum(ErrorCodeEnum errorCodeEnum) {
        this.errorCodeEnum = errorCodeEnum;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public ErrorCodeEnum getErrorCodeEnum() {
        return errorCodeEnum;
    }
}
