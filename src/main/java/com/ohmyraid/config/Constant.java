package com.ohmyraid.config;

public final class Constant {
    //Todo 하드코딩 => 상수화 시킬게 너무너무 많다. 보일때마다 처리할것
    public static final String THREAD_INF = "ThreadInf";
    public static final String STRING_SPACE = " ";


    public static final class Auth{
        public static final String AUTHORIZATION_HEADER_BASIC = "Basic";
        public static final String BLIZZARD_TOKEN_KEY = "blizzardAccessToken";
        public static final String NORMAL_GRANT_TYPE = "client_credentials";
        public static final String SUMMARY_GRANT_TYPE = "authorization_code";
        public static final String SCOPE = "wow.profile";
        public static final String NAMESPACE = "profile-kr";
        public static final String LOCALE = "ko_KR";
        public static final String REGION = "kr";
    }
}
