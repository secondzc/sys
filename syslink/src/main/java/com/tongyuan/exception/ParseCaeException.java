package com.tongyuan.exception;

public class ParseCaeException extends  Exception{
    public ParseCaeException(String s){
        super(s);
    }
    //默认的msg如下
    public ParseCaeException(){
        super("cae的xml格式不规范");
    }

}
