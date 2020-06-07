package com.myservice.mainpack.exception.exceptionHandler;

import com.myservice.mainpack.dto.RestResponce;
import com.myservice.mainpack.exception.BadValidateException;
import com.myservice.mainpack.exception.ConflictException;
import com.myservice.mainpack.exception.ProcessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import javax.servlet.http.HttpServletRequest;


@RestControllerAdvice(annotations = RestController.class)
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
public class RestControllerExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(RestControllerExceptionHandler.class);



    @ResponseStatus(value = HttpStatus.CONFLICT)
    @Order(Ordered.HIGHEST_PRECEDENCE + 8)
    @ExceptionHandler(value = ConflictException.class)
    public RestResponce conflict409(HttpServletRequest request, ConflictException ex){
        log.warn("Конфликт при записи данных в БД. " + "url:( " + request.getRequestURL() + " ). " +
                restResponseToString(ex.getRestResponse()) + ex.toString());
        return ex.getRestResponse();
    }


    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    @Order(Ordered.HIGHEST_PRECEDENCE + 9)
    @ExceptionHandler(value = BadValidateException.class)
    public RestResponce badValidate422(HttpServletRequest request, BadValidateException ex){
        log.warn("Ошибка валидации. " + "url:( " + request.getRequestURL() + " ). " +
                restResponseToString(ex.getRestResponse()) + ex.toString());
        return ex.getRestResponse();
    }


    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @Order(Ordered.HIGHEST_PRECEDENCE + 10)
    @ExceptionHandler(value = ProcessException.class)
    public RestResponce errorInProcess500(HttpServletRequest request, ProcessException ex){
        log.error("Ошибка сервера в процессе работы с БД. " + "url:( " + request.getRequestURL() + " ). " +
                restResponseToString(ex.getRestResponse()) + ex.toString());
        return ex.getRestResponse();
    }


    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @Order(Ordered.HIGHEST_PRECEDENCE + 11)
    @ExceptionHandler(value = Exception.class)
    public RestResponce internalServerError500(HttpServletRequest request, Exception ex){
        log.error("Ошибка сервера. " + "url:( " + request.getRequestURL() + " ). " + ex.toString());
        return new RestResponce("Ошибка сервера!", "error");
    }


    public String restResponseToString(RestResponce restResponse){

        String STR = "Ответ сервера: { ";
        if( restResponse.getMessage() != null ){ STR = STR + "{message: " + restResponse.getMessage() + "}; "; }
        if( restResponse.getStatus() != null ){ STR = STR + "{status: " + restResponse.getStatus() + ""; }
        STR = STR + " } ";

        return STR;

    }


}
