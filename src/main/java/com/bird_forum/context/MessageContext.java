package com.bird_forum.context;

public class MessageContext {
    public final static String ACCOUNT_PASSWORD_NULL = "账号或密码为空";
    public final static String USER_NOT_EXIST = "用户不存在";
    public final static String REGISTER_DATA_NULL = "用户名称、账号或密码为空";
    public final static String NAME_FORMAT_ERROR = "用户名称格式错误!长度4~10,有中文字母数字以及_组成,不能以'_'开头!";
    public final static String ACCOUNT_FORMAT_ERROR = "用户邮箱格式错误!";
    public final static String PASSWORD_FORMAT_ERROR = "密码格式错误!必须大于等于6位小于等于25位,且包含数字字母字符!";
    public final static String REGISTER_SUCCESS = "注册成功!";
    public final static String REGISTER_FAIL = "注册失败!用户已存在!";
    public static final String PASSWORD_NULL = "密码为空";
    public static final String PASSWORD_REPASSWORD_NOT_EQUALS = "两次密码不一致!";
    public static final String UPDATE_SUCCESS = "修改成功!";
    public static final String UPDATE_FAIL = "修改失败!";
}
