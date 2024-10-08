package com.revature.reimbursementapp.controllers;

import com.revature.reimbursementapp.models.dtos.LoginRequestDTO;
import com.revature.reimbursementapp.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@CrossOrigin(origins="http://localhost:5173", allowCredentials = "true")
public class LoginController {

    private final JwtService jwtService;

    @Autowired
    public LoginController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping
    public ResponseEntity<String> postLogin(@RequestBody LoginRequestDTO loginRequest) {
        return ResponseEntity.ok(jwtService.generateJwtToken(loginRequest));
    }
}
