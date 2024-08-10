package com.revature.reimbursementapp.controllers;

import com.revature.reimbursementapp.models.Reimbursement;
import com.revature.reimbursementapp.models.dtos.JwtDTO;
import com.revature.reimbursementapp.models.dtos.ReimbursementDTO;
import com.revature.reimbursementapp.services.JwtService;
import com.revature.reimbursementapp.services.ReimbursementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class ReimbursementController {

    private final JwtService jwtService;

    private final ReimbursementService reimbursementService;

    @Autowired
    public ReimbursementController(JwtService jwtService, ReimbursementService reimbursementService) {
        this.jwtService = jwtService;
        this.reimbursementService = reimbursementService;
    }

    @PostMapping("/reimbursement")
    public ResponseEntity<String> postReimbursement(@RequestBody ReimbursementDTO reimbursement, @RequestHeader(HttpHeaders.AUTHORIZATION) String bearer) {
        JwtDTO jwt = jwtService.parseJwtToken(bearer);
        reimbursementService.saveReimbursement(reimbursement, jwt);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/reimbursement")
    public ResponseEntity<String> patchReimbursement(@RequestBody ReimbursementDTO reimbursement, @RequestHeader(HttpHeaders.AUTHORIZATION) String bearer) {
        JwtDTO jwt = jwtService.parseJwtToken(bearer);
        reimbursementService.updateReimbursement(reimbursement, jwt);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/reimbursement/{id}")
    public ResponseEntity<String> deleteReimbursement(@PathVariable String id, @RequestHeader(HttpHeaders.AUTHORIZATION) String bearer) {
        JwtDTO jwt = jwtService.parseJwtToken(bearer);
        reimbursementService.deleteReimbursement(id, jwt);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/reimbursements")
    public ResponseEntity<List<Reimbursement>> getReimbursements(@RequestHeader(HttpHeaders.AUTHORIZATION) String bearer) {
        JwtDTO jwt = jwtService.parseJwtToken(bearer);
        List<Reimbursement> reimbursements = reimbursementService.getReimbursements(jwt);
        return ResponseEntity.ok(reimbursements);
    }

    @GetMapping("/reimbursement/{id}")
    public ResponseEntity<Reimbursement> getReimbursement(@PathVariable int id, @RequestHeader(HttpHeaders.AUTHORIZATION) String bearer) {
        JwtDTO jwt = jwtService.parseJwtToken(bearer);
        Reimbursement reimbursement = reimbursementService.getReimbursement(id, jwt);
        return ResponseEntity.ok(reimbursement);
    }
}
