package com.ohmyraid.common.result;


public class CommonPageNotFoundException extends CommonException {

    private static final long serialVersionUID = 1L;

    public CommonPageNotFoundException() {
        super(ErrorResult.PAGE_NOT_FOUND);
    }
}
