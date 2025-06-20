package com.jwvdp.books.utils;


import com.jwvdp.xinma.enums.ResponseCode;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ResultUtil {

    private Integer code ;//1 成功 , 0 失败

    private String msg; //提示信息

    private Object data; //数据 date

    public ResultUtil() {
    }

    public ResultUtil(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static ResultUtil success(){
        return new ResultUtil(ResponseCode.OK.getCode(), "success", null);
    }

    public static ResultUtil success(Object data){
        return new ResultUtil(ResponseCode.OK.getCode(), "success", data);
    }

    public static ResultUtil success(String msg, Object data){
        return new ResultUtil(ResponseCode.OK.getCode(), msg, data);
    }

    /*逻辑异常*/
    public static ResultUtil fail(String msg){
        return new ResultUtil(ResponseCode.FAIL.getCode(), msg, null);
    }

    /*逻辑异常*/
    public static ResultUtil fail(int code, String msg){
        return new ResultUtil(code, msg, null);
    }

    /*程序异常*/
    public static ResultUtil error(String msg){
        return new ResultUtil(ResponseCode.ERROR.getCode(), "msg", null);
    }

    /*程序异常*/
    public static ResultUtil validFail(String error){
        return new ResultUtil(ResponseCode.FAIL.getCode(), error, null);
    }
}