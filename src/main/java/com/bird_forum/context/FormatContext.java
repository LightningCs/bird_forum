package com.bird_forum.context;

public class FormatContext {
    // 用户名称格式，4~10，由中文字母数字以及_组成，首字符不能是_
    public static final String USERNAME_FORMAT = "(?=^.{4,10}$)(?=^[^_])(?=.*[A-Z]|[\\u2E80-\\u9FFF]|[a-z]|_|\\d).*$";

    // 用户账号(邮箱)验证
    public static final String ACCOUNT_FORMAT = "^[a-z\\d]+(\\.[a-z\\d]+)*@([\\da-z](-[\\da-z])?)+(\\.{1,2}[a-z]+)+$";

    // 密码长度大于等于6小于等于25，且由数字字母和字符组成
    public static final String PASSWORD_FORMAT = "(?=^.{6,25}$)(?=.*\\d)(?=.*\\W+)(?=.*[A-Z])(?=.*[a-z])(?!.*\\n).*$";
}
