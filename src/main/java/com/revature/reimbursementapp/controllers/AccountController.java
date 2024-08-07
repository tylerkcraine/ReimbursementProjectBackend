package com.revature.reimbursementapp.controllers;

import com.revature.reimbursementapp.models.dtos.JwtDTO;
import com.revature.reimbursementapp.models.dtos.ReturnAccountDTO;
import com.revature.reimbursementapp.enums.RoleType;
import com.revature.reimbursementapp.services.AccountService;
import com.revature.reimbursementapp.services.JwtService;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AccountController {

    private AccountService accountService;
    private JwtService jwtService;

    @Autowired
    public AccountController(AccountService accountService, JwtService jwtService) {
        this.accountService = accountService;
        this.jwtService = jwtService;
    }

    @GetMapping("/accounts")
    public ResponseEntity<List<ReturnAccountDTO>> getAllAccounts(@RequestHeader(HttpHeaders.AUTHORIZATION) String bearer) {
        // checking to see if the JWT is valid to see if a use is logged in
        JwtDTO requestingUser;
        try {
            requestingUser = jwtService.parseJwtToken(bearer);
        } catch (JwtException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        // checking to see if account is a user and denys if so
        if (requestingUser.getRoleType() == RoleType.USER) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(accountService.getAllAccounts().stream().map(ReturnAccountDTO::new).toList());
    }

    @GetMapping("/account/{id}")
    public ResponseEntity<ReturnAccountDTO> getAccount(@PathVariable String id, @RequestHeader(HttpHeaders.AUTHORIZATION) String bearer) {
        return ResponseEntity.ok().build();
    }
}
