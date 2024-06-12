package com.project.springapistudy.common;

public class DBException {
    public static class DataNotFound extends BaseException{
        public DataNotFound(String message){
            super(message);
        }
    }
}
