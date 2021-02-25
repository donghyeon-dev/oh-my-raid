package com.ohmyraid.common.result;

public enum SuccessResult implements Result {

    SUCCESS("SUCCESS", "요청이 정상적으로 처리되었습니다."); // 요청이 정상적으로 처리되었습니다.

    private String resultCode;
    private String resultMsg;
    private String[] resultMsgArgs;

    SuccessResult(String resultCode, String resultMsgId) {
        this.resultCode = resultCode;
        this.resultMsg = resultMsgId;
    }

    public String getResultCode() {
        return resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public String[] getResultMsgArgs() {
        return resultMsgArgs;
    }

    public Result setMsgArgs(String resultMsgArgs) {
        return this.setMsgArgs(resultMsgArgs);
    }

    public Result setMsgArgs(String... resultMsgArgs) {
        this.resultMsgArgs = resultMsgArgs;
        return this;
    }
}
