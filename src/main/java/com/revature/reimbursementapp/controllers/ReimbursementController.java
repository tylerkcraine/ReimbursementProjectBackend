package com.revature.reimbursementapp.controllers;

import com.revature.reimbursementapp.daos.ReimbursementDAO;
import com.revature.reimbursementapp.models.Reimbursement;
import com.revature.reimbursementapp.models.dtos.JwtDTO;
import com.revature.reimbursementapp.models.dtos.NewReimbursementDTO;
import com.revature.reimbursementapp.services.JwtService;
import com.revature.reimbursementapp.services.ReimbursementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReimbursementController {

    private final JwtService jwtService;

    private final ReimbursementService reimbursementService;

    @Autowired
    public ReimbursementController(JwtService jwtService, ReimbursementService reimbursementService) {
        this.jwtService = jwtService;
        this.reimbursementService = reimbursementService;
    }

    @PostMapping("/reimbursement")
    public ResponseEntity<String> postReimbursement(@RequestBody NewReimbursementDTO reimbursement, @RequestHeader(HttpHeaders.AUTHORIZATION) String bearer) {
        JwtDTO jwt = jwtService.parseJwtToken(bearer);
        reimbursementService.saveReimbursement(reimbursement, jwt);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
