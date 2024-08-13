package com.revature.reimbursementapp.controllers;

import com.revature.reimbursementapp.exceptions.AccountExistsException;
import com.revature.reimbursementapp.exceptions.AccountNotFoundException;
import com.revature.reimbursementapp.exceptions.SingleAdminException;
import com.revature.reimbursementapp.exceptions.UnauthorizedException;
import io.jsonwebtoken.JwtException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.security.auth.login.AccountExpiredException;

@ControllerAdvice
@CrossOrigin(origins="http://localhost:5173", allowCredentials = "true")
public class GlobalAdviceHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({JwtException.class})
    public ResponseEntity<String> handleJwtException(JwtException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid JWT token");
    }

    @ExceptionHandler({NumberFormatException.class})
    public ResponseEntity<String> handleNumberFormatException(NumberFormatException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid ID");
    }

    @ExceptionHandler({AccountNotFoundException.class})
    public ResponseEntity<String> handleAccountNotFoundException(AccountNotFoundException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");
    }

    @ExceptionHandler({UnauthorizedException.class})
    public ResponseEntity<String> handleUnauthorizedException(UnauthorizedException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }

    @ExceptionHandler({SingleAdminException.class})
    public ResponseEntity<String> handleSingleAdminException(SingleAdminException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Can not change/remove the only admin account in the system");
    }

    @ExceptionHandler({AccountExistsException.class})
    public ResponseEntity<String> handleAccountExistsException(AccountExistsException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("That user name is already taken");
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
