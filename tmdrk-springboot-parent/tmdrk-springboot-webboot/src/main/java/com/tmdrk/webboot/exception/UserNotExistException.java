package com.tmdrk.webboot.exception;

/**
 * @ClassName UserNotExistException
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/5/7 1:27
 * @Version 1.0
 **/
public class UserNotExistException extends RuntimeException{
    public UserNotExistException(){
        super("用户不存在");
    }
}
