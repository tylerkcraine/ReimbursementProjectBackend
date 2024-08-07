package com.revature.reimbursementapp.controllers;

import com.revature.reimbursementapp.models.dtos.LoginRequestDTO;
import com.revature.reimbursementapp.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@CrossOrigin
public class LoginController {

    private final JwtService jwtService;

    @Autowired
    public LoginController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping
    public ResponseEntity<String> postLogin(@RequestBody LoginRequestDTO loginRequest) {
        String token = jwtService.generateJwtToken(loginRequest);
        
        if (token.isBlank()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else {
            return ResponseEntity.ok(token);
        }
    }
}
