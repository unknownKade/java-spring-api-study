package com.project.springapistudy.common;

public abstract class BaseException extends RuntimeException{
    public String message;

    public BaseException(String message){
        super(message);
        this.message = message;
    }
}
