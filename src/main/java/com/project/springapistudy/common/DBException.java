package com.project.springapistudy.common;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DBException {
    public static class DataNotFound extends BaseException{
        public DataNotFound(String message){
            super(message);
        }
    }
}
