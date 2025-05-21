package com.ohmyraid.common.result;

public enum ErrorResult implements Result {

    FAIL("ESYS000", "service.fail.msg"), // 정상적으로 처리되지 않았습니다. 관리자에게 문의하십시오.
    INVALID_INPUT("ESYS001", "service.fail.invalid.msg"), // 입력값 검증에 실패하였습니다.
    INTERNAL("ESYS002", "service.fail.internal.msg"), // 정상적으로 처리되지 않았습니다. 관리자에게 문의하십시오.
    PAGE_NOT_FOUND("ESYS003", "service.fail.page.msg"), // 페이지를 찾을 수 없습니다.
    BAD_REQUEST("ESYS004", "service.fail.badrq.msg"), // 서버가 인식할 수 없는 요청 구문입니다.
    NO_SESSION("ESYS005", "service.fail.session.msg"), // 세션이 존재하지 않습니다.
    NO_URL_AUTH("ESYS006", "service.fail.auth.msg"), // 접근 권한이 없습니다.
    DUP_EMAIL("ESVC007", "signup.fail.dup.email.msg"), // 이메일이 중복됩니다
    DUP_NN("ESVC008", "signup.fail.dup.nn.msg"), // 닉네임이 중복됩니다.
    LOGIN_FAIL_INVALID_PW("ESVC009", "login.fail.invalid.pw.msg"), // 아이디 혹은 비밀번호를 확인해주세요.
    LOGIN_FAIL_NO_ID("ESVC010", "login.fail.no.id.msg"), // 존재하지 않는 이메일입니다
    NO_BZ_TOKEN("ESVC011", "login.fail.no.bztoken.msg"), // 블리자드API 토큰이 존재하지 않습니다.
    INVALID_PASSWORD("ESVC012", "login.fail.invalid.pw"), // 비밀번호가 일치하지 않습니다.
    TOO_MANY_REQUEST("ESVC013", "service.fail.toomany.msg"), // 요청이 너무 많습니다.
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
        this.resultMsgArgs = new String[] { resultMsgArgs };
        return this;
    }

    @Override
    public Result setMsgArgs(String... resultMsgArgs) {
        this.resultMsgArgs = resultMsgArgs;
        return this;
    }

}
