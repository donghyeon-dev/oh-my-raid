package com.ohmyraid.common.result;

public interface Result {
    public String getResultCode();

    public String getResultMsg();

    public String[] getResultMsgArgs();

    public Result setMsgArgs(String resultMsgArgs);

    public Result setMsgArgs(String... resultMsgArgs);
}
