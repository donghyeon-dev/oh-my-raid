package com.ohmyraid.common.result;

public enum ErrorResult implements Result {

    FAIL("ESYS000", "정상적으로 처리되지 않았습니다. 관리자에게 문의하십시오."), // 정상적으로 처리되지 않았습니다. 관리자에게 문의하십시오.
    INVALID_INPUT("ESYS001", "입력값 검증에 실패하였습니다."), // 입력값 검증에 실패하였습니다.
    INTERNAL("ESYS002", "정상적으로 처리되지 않았습니다. 관리자에게 문의하십시오."), // 정상적으로 처리되지 않았습니다. 관리자에게 문의하십시오.
    PAGE_NOT_FOUND("ESYS004", "페이지를 찾을 수 없습니다."), // 페이지를 찾을 수 없습니다.
    BAD_REQUEST("ESYS005", "서버가 인식할 수 없는 요청 구문입니다."), // 서버가 인식할 수 없는 요청 구문입니다.
    NO_SESSION("ESYS006", "세션이 존재하지 않습니다."), // 세션이 존재하지 않습니다.
    NO_URL_AUTH("ESYS007", "접근 권한이 없습니다."), // 접근 권한이 없습니다.
    ;

    private String resultCode;
    private String resultMsg;
    private String[] resultMsgArgs;

    ErrorResult(String resultCode, String resultMsgId) {
        this.resultCode = resultCode;
        this.resultMsg = resultMsgId;
    }

    @Override
    public String getResultCode() {
        return resultCode;
    }

    @Override
    public String getResultMsg() {
        return resultMsg;
    }

    @Override
    public String[] getResultMsgArgs() {
        return resultMsgArgs;
    }

    @Override
    public Result setMsgArgs(String resultMsgArgs) {
        return this.setMsgArgs(resultMsgArgs);
    }

    @Override
    public Result setMsgArgs(String... resultMsgArgs) {
        this.resultMsgArgs = resultMsgArgs;
        return this;
    }

}
