package base.advice;

import base.api.CommonResult;
import base.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AdviceController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = Exception.class)
    public CommonResult exceptionHandler(Exception ex){
        if(ex instanceof BusinessException){
           return CommonResult.failed("请稍后再试:"+ex.getMessage());
        }else if(ex instanceof MethodArgumentNotValidException){
            MethodArgumentNotValidException e = (MethodArgumentNotValidException)ex;
            return CommonResult.failed("校验错误:"+e.getBindingResult().getFieldError().getDefaultMessage());
        }
        return CommonResult.failed();
    }

}
