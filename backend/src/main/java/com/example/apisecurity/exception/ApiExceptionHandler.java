package com.example.apisecurity.exception;

import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(ResponseStatusException.class)
    public Map<String,Object> responseStatus(ResponseStatusException ex){return Map.of("status",ex.getStatusCode().value(),"message",ex.getReason()==null?"Request failed":ex.getReason());}
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String,Object> unexpected(){return Map.of("status",500,"message","Unexpected server error");}
}
