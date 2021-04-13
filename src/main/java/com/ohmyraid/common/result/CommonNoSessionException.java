package com.ohmyraid.common.result;


public class CommonNoSessionException extends CommonException {

    private static final long serialVersionUID = 1L;

    public CommonNoSessionException() {
        super(ErrorResult.NO_SESSION);
    }

}
