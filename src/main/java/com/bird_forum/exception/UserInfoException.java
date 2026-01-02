package com.bird_forum.exception;

public class UserInfoException extends RuntimeException{
    public UserInfoException(){}

    public UserInfoException(String msg) {
        super(msg);
    }
}
