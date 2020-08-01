package com.example.myforum_springboot.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@ControllerAdvice
@ResponseBody
public class ExceptionController {
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public int handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        return -3;
    }
}
