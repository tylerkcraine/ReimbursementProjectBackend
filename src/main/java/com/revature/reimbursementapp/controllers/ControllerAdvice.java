package com.revature.reimbursementapp.controllers;

import com.revature.reimbursementapp.exceptions.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Optional;

@org.springframework.web.bind.annotation.ControllerAdvice("com.revature.reimbursementapp.Controller")
public class ControllerAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {UnauthorizedException.class})
    protected ResponseEntity<String> unauthorized(RuntimeException e, WebRequest r) {
        Optional<String> msg = Optional.of(e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(msg.orElse(""));
    }
}
