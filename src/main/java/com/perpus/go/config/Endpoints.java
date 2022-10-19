package com.perpus.go.config;

public class Endpoints {
    public static final String MAIN = "/api/v1";
    public static final String LOGIN = MAIN + "/login";
    public static final String REFRESH_TOKEN = MAIN + "/token/refresh";
    public static final String REGISTER = MAIN + "/register";
    public static final String RESET_CODE = MAIN + "/user/reset/get_code";
    public static final String RESET_PASSWORD_BY_EMAIL = MAIN + "/user/reset/password_by_email";
    public static final String REGISTER_KTP = MAIN + "/user/register-ktp";
}
