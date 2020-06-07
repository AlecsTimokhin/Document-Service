package com.myservice.mainpack.exception.exceptionHandler;

import com.myservice.mainpack.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;


@ControllerAdvice(annotations = Controller.class)
public class ControllerExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(ControllerExceptionHandler.class);


    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = Exception.class)
    public ModelAndView internalServerError500(HttpServletRequest request, Exception ex) {
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        return doModelAndView(request, ex, httpStatus, "Ошибка сервера!");
    }


    public ModelAndView doModelAndView(HttpServletRequest request, Exception ex, HttpStatus httpStatus, String message){

        log.error("Ошибка в запросе" + " url:( " + request.getRequestURL() + " ). " + ex.toString());

        User currentUser = (User) request.getSession().getAttribute("currentUser");
        boolean canDoActions = (boolean) request.getSession().getAttribute("canDoActions");

        ModelAndView modelAndView = new ModelAndView("error/exception");
        modelAndView.getModelMap().addAttribute("exception", ex);
        modelAndView.getModelMap().addAttribute("messageError", message);
        modelAndView.getModelMap().addAttribute("status", httpStatus);
        modelAndView.getModelMap().addAttribute("currentUser", currentUser);
        modelAndView.getModelMap().addAttribute("canDoActions", canDoActions);

        modelAndView.setStatus(httpStatus);

        return modelAndView;
    }


}
