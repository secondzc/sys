package com.tongyuan.exception;

/**
 * Created by Y470 on 2017/7/10.
 */
public class SqlNumberException extends Exception{
    private Integer code;
    public SqlNumberException(String msg){
        super(msg);
		//xyx
    }
    public SqlNumberException(Integer code,String msg){
        super(msg);
        this.code =code;
    }
    public Integer getCode(){
        return code;
    }
    public String getMsg(){
        return super.getMessage();
    }
}
