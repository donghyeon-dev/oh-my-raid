package com.ohmyraid.common.wrapper;

import com.ohmyraid.common.result.Result;

public class ResultView<T> extends AbstractResultView<T> {

    private static final long serialVersionUID = 7340454832698786439L;

    public ResultView() {
        super();
        init();
    }

    public ResultView(T data) {
        super(data);
        init();
    }

    private void init() {
    }

    @Override
    public ResultView<T> setResult(Result result) {
        super.setResult(result);
        return this;
    }


}
