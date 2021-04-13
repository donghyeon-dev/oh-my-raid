package com.ohmyraid.common.result;



public class CommonNoAuthenticationException extends CommonException{

    private static final long serialVersionUID = 1L;

    public CommonNoAuthenticationException() {
        super(ErrorResult.NO_URL_AUTH);
    }

}
