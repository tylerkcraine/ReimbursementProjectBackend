package com.revature.reimbursementapp.controllers;

import com.revature.reimbursementapp.models.Account;
import com.revature.reimbursementapp.models.dtos.AccountChangeDTO;
import com.revature.reimbursementapp.models.dtos.JwtDTO;
import com.revature.reimbursementapp.models.dtos.ReturnAccountDTO;
import com.revature.reimbursementapp.enums.RoleType;
import com.revature.reimbursementapp.services.AccountService;
import com.revature.reimbursementapp.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class AccountController {

    private final AccountService accountService;
    private final JwtService jwtService;

    @Autowired
    public AccountController(AccountService accountService, JwtService jwtService) {
        this.accountService = accountService;
        this.jwtService = jwtService;
    }

    @GetMapping("/accounts")
    public ResponseEntity<List<ReturnAccountDTO>> getAllAccounts(@RequestHeader(HttpHeaders.AUTHORIZATION) String bearer) {
        JwtDTO requestingUser = jwtService.parseJwtToken(bearer);
        //users are not allowed to query for all accounts
        if (requestingUser.getRoleType() == RoleType.USER) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(accountService.getAllAccounts().stream().map(ReturnAccountDTO::new).toList());
    }

    @GetMapping("/account/{id}")
    public ResponseEntity<ReturnAccountDTO> getAccount(@PathVariable String id, @RequestHeader(HttpHeaders.AUTHORIZATION) String bearer) {
        JwtDTO requestingUser = jwtService.parseJwtToken(bearer);

        //users aren't allowed to query for other accounts
        if (requestingUser.getRoleType() == RoleType.USER) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(new ReturnAccountDTO(accountService.getAccountById(Integer.parseInt(id))));
    }

    @PatchMapping("/account")
    public ResponseEntity<String> updateAccount(@RequestBody AccountChangeDTO accountChangeDTO, @RequestHeader(HttpHeaders.AUTHORIZATION) String bearer) {
        JwtDTO requestingUser = jwtService.parseJwtToken(bearer);
        accountService.updateAccount(accountChangeDTO, requestingUser);
        return ResponseEntity.ok().body("");
    }

    @DeleteMapping("/account/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable String id, @RequestHeader(HttpHeaders.AUTHORIZATION) String bearer) {
        JwtDTO requestingUser = jwtService.parseJwtToken(bearer);
        accountService.removeAccount(Integer.parseInt(id), requestingUser);
        return ResponseEntity.ok().body("");
    }

    @GetMapping("/me")
    public ResponseEntity<ReturnAccountDTO> getMe(@RequestHeader(HttpHeaders.AUTHORIZATION) String bearer) {
        JwtDTO requestingUser = jwtService.parseJwtToken(bearer);
        return ResponseEntity.ok().body(new ReturnAccountDTO(accountService.getAccountById(requestingUser.getAccountId())));
    }
}
