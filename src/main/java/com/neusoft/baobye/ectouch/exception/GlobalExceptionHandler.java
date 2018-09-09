package com.neusoft.baobye.ectouch.exception;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = EcException.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest request, HttpServletResponse response, Object handler, EcException ex){
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        System.out.println("=====================全局异常信息捕获=======================");
        ModelAndView modelAndView = new ModelAndView();
        String msg;
        if(ex instanceof EcException){
            msg = ex.getMessage();
        } else{
            msg = "操作异常!";
        }
        ex.printStackTrace();
        try {
            modelAndView.addObject("message",msg);
            modelAndView.addObject("buttonValue",ex.getButtonValue());
            modelAndView.addObject("contentUrl",ex.getContentUrl());
            modelAndView.setViewName("/error");
        } catch(Exception e){
            modelAndView.addObject("message",msg);
            modelAndView.addObject("buttonValue","系统异常");
            modelAndView.addObject("contentUrl","/");
            e.printStackTrace();
        } finally {
        }

        return modelAndView;
    }
    @ExceptionHandler(value = Exception.class)
    public ModelAndView defaultHandler(HttpServletRequest request,HttpServletResponse response,Object handler,Exception ex){
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("message","系统异常请稍后重试");
        modelAndView.addObject("buttonValue","系统异常请稍后重试");
        modelAndView.addObject("contentUrl","/");
        modelAndView.setViewName("/error");
        return modelAndView;
    }
}