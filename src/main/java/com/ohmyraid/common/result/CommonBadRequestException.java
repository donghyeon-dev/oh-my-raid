package com.ohmyraid.common.result;


public class CommonBadRequestException extends CommonException {

    private static final long serialVersionUID = 1L;

    public CommonBadRequestException() {
        super(ErrorResult.BAD_REQUEST);
    }

    public CommonBadRequestException(String msg) {
        super(ErrorResult.BAD_REQUEST.getResultCode(), "service.fail.badrq.msg", msg);
    }

}
