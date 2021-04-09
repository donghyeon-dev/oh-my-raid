package com.ohmyraid.common.result;

public class CommonServiceException extends CommonException {
    private static final long serialVersionUID = 1L;

    public CommonServiceException(Result result) {
        super(result);
    }

}

