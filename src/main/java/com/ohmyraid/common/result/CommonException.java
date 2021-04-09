package com.ohmyraid.common.result;

import com.ohmyraid.utils.MessageUtils;
import org.springframework.core.NestedRuntimeException;

import java.io.PrintStream;
import java.io.PrintWriter;

public abstract class CommonException extends NestedRuntimeException {
    private static final long serialVersionUID = 1L;

    private String errorCode;
    private String[] errorMsgArgs;
    private Object resultData;
    private Throwable cause;

    public CommonException(Result result) {
        this(result.getResultCode(), result.getResultMsg(), result.getResultMsgArgs());
    }

    public CommonException(String errorCode, String errorBundleCode) {
        super(MessageUtils.getMessage(errorBundleCode));
        this.errorCode = errorCode;
    }

    public CommonException(String errorCode, String errorMsgId, String errorMsgArg) {
        this(errorCode, errorMsgId, new String[] {errorMsgArg});
    }

    public CommonException(String errorCode, String errorMsgId, String[] errorMsgArgs) {
        super(MessageUtils.getMessage(errorMsgId, errorMsgArgs));
        this.errorCode = errorCode;
        this.errorMsgArgs = errorMsgArgs;
    }

    public CommonException setResultData(Object resultData) {
        this.resultData = resultData;
        return this;
    }

    public CommonException setCause(Throwable cause) {
        this.cause = cause;
        return this;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return super.getMessage();
    }

    public String[] getErrorArguments() {
        return errorMsgArgs;
    }

    public Object getResultData() {
        return resultData;
    }

    public Throwable getCause() {
        return cause;
    }

    private boolean hasCause() {
        return null != getCause() && this != getCause();
    }

    public void printStackTrace() {
        super.printStackTrace();
        if (hasCause()) {
            getRootCause().printStackTrace();
        }
    }

    public void printStackTrace(PrintStream stream) {
        super.printStackTrace(stream);
        if (hasCause()) {
            getRootCause().printStackTrace(stream);
        }
    }

    public void printStackTrace(PrintWriter writer) {
        super.printStackTrace(writer);
        if (hasCause()) {
            getRootCause().printStackTrace(writer);
        }
    }
}
