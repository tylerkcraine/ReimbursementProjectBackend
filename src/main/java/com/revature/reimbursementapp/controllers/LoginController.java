package com.revature.reimbursementapp.controllers;

import com.revature.reimbursementapp.dtos.LoginRequestDTO;
import com.revature.reimbursementapp.exceptions.UnauthorizedException;
import com.revature.reimbursementapp.models.Account;
import com.revature.reimbursementapp.services.AccountService;
import com.revature.reimbursementapp.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {

    private final AccountService accountService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Autowired
    public LoginController(AccountService accountService, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.accountService = accountService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @PostMapping
    public ResponseEntity<String> postLogin(@RequestBody LoginRequestDTO loginRequest) {
        Account account = accountService.getAccountByUsername(loginRequest.getUsername());
        if (account == null) {
            throw new UnauthorizedException("Account not found");
        }

        if (passwordEncoder.matches(loginRequest.getRawPassword(), account.getPassword())) {
            String token = jwtService.generateJwtToken(account);
            return ResponseEntity.ok().body(token);
        } else {
            throw new UnauthorizedException("Account not found");
        }
    }
}
