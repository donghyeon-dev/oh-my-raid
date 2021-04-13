package com.ohmyraid.common.result;


public class CommonInvalidInputException extends CommonException {

    private static final long serialVersionUID = 1L;

    public CommonInvalidInputException() {
        super(ErrorResult.INVALID_INPUT);
    }
}
