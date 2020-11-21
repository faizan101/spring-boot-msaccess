package com.msaccess.app.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {

    /**
     * Handles illegal argument exceptions
     *
     * @param exception {@link ConstraintViolationException}
     * @return {@link Map}
     */
    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map handle(BindException exception) {
        return error(exception.getAllErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList()));
    }

    private Map error(List<String> message) {
        Map<String, Object> resp = new HashMap<>();
        resp.put("code", 400);
        resp.put("error", message.toString());
        return resp;
    }

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Map handle(HttpClientErrorException.Unauthorized exception) {

        Map<String, Object> resp = new HashMap<>();
        resp.put("code", exception.getStatusCode().value());
        resp.put("error", exception.getLocalizedMessage());
        return resp;
    }

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map handle(Exception exception) {

        Map<String, Object> resp = new HashMap<>();
        resp.put("code", 500);
        resp.put("error", exception.getLocalizedMessage());
        return resp;
    }

}
