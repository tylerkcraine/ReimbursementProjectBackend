package com.revature.reimbursementapp.controllers;

import com.revature.reimbursementapp.models.dtos.RegistrationDTO;
import com.revature.reimbursementapp.exceptions.AccountExistsException;
import com.revature.reimbursementapp.services.AccountService;
import com.revature.reimbursementapp.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")
@CrossOrigin
public class RegisterController {

    private final AccountService accountService;

    private final JwtService jwtService;

    @Autowired
    public RegisterController(AccountService accountService, JwtService jwtService) {
        this.accountService = accountService;
        this.jwtService = jwtService;
    }

    @PostMapping
    public ResponseEntity<String> RegisterAccount(@RequestBody RegistrationDTO registrationRequest) {
        try {
            this.accountService.saveAccount(registrationRequest);
        } catch (AccountExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.noContent().build();
    }
}
