package com.tmdrk.chat.common.utils;

import com.tmdrk.chat.common.entity.Result;

/**
 * @ClassName ResultUtil
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/7/18 16:26
 * @Version 1.0
 **/
public class ResultUtil {
    private static Integer errorCode = 0;
    private static Integer successCode = 1;
    private static String successMsg = "";
    private static String msg = "系统错误";

    public static Result success(){
        return success(null);
    }
    public static Result success(Object obj){
        return success(successCode,obj);
    }
    public static Result success(Integer code,Object obj){
        return new Result(code,successMsg,obj);
    }

    public static Result fail(){
        return fail(errorCode);
    }
    public static Result fail(Integer code){
        return fail(code,msg);
    }
    public static Result fail(String msg){
        return fail(errorCode,msg);
    }
    public static Result fail(Integer code,String msg){
        return fail(code,msg,null);
    }
    public static Result fail(Integer code,String msg,Object obj){
        return new Result(code,msg,obj);
    }
}
