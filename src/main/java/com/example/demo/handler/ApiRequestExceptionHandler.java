package com.example.demo.handler;

import com.example.demo.Exceptions.InvalidInputException;
import com.example.demo.Exceptions.ResourceNotFoundException;
import com.example.demo.model.ErrorRes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiRequestExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorRes> handleException(ResourceNotFoundException exception){
        ErrorRes error = new ErrorRes(
                HttpStatus.NOT_FOUND.value(),
                exception.getMessage(),
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorRes> handleException(InvalidInputException exception){
        ErrorRes error = new ErrorRes(
                HttpStatus.CONFLICT.value(),
                exception.getMessage(),
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
