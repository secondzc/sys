package com.tongyuan.model.Exception;

import com.tongyuan.exception.SqlNumberException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionsHandler {
    @ExceptionHandler(SqlNumberException.class)
    public String SqlNumberExceptionHandler(SqlNumberException e){
        return e.getMsg();
    }
}
