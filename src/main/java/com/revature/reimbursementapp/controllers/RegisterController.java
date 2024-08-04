package com.revature.reimbursementapp.controllers;

import com.revature.reimbursementapp.dtos.RegistrationDTO;
import com.revature.reimbursementapp.exceptions.AccountExistsException;
import com.revature.reimbursementapp.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
public class RegisterController {

    private final AccountService accountService;

    @Autowired
    public RegisterController(AccountService accountService) {
        this.accountService = accountService;
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
