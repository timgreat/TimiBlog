package com.timi.handle;

import com.timi.exception.SystemException;
import com.timi.utils.AppHttpCodeEnum;
import com.timi.utils.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    //对SystemException异常的处理
    @ExceptionHandler(SystemException.class)
    public ResponseResult systemExceptionHandler(SystemException e){
        //打印日志
        log.error("出现了异常！{}",e.getMessage(),e);
        //返回提示信息
        return ResponseResult.errorResult(e.getCode(), e.getMsg());
    }
    //对其它异常的处理
    @ExceptionHandler(Exception.class)
    public ResponseResult exceptionHandler(Exception e){
        //打印日志
        log.error("出现了异常！{}",e.getMessage(),e);
        //返回提示信息
        return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(), e.getMessage());
    }
}
