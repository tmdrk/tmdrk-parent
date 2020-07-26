package com.tmdrk.pushgateway.util;

/**
 * @Author zhoujie
 * @Description //TODO
 * @Date 14:54 2020/5/9
 * @Param 
 * @return 
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

    public static class Result {
        private Integer code;
        private String msg;
        private Object data;

        public Result(){

        }
        public Result(Integer code,String msg){
            this(code,msg,null);
        }

        public Result(Integer code,String msg,Object data){
            this.code = code;
            this.msg = msg;
            this.data = data;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }
    }
}
