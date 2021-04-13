package com.ohmyraid.common.result;


public class CommonEventException extends CommonException {

    private static final long serialVersionUID = 1L;


    public CommonEventException() {
        super(ErrorResult.FAIL);
    }

    public CommonEventException(String msg) {
        super(ErrorResult.FAIL.getResultCode(), "system.fail.msg", msg);
    }
}

