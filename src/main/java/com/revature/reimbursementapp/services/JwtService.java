package com.revature.reimbursementapp.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.reimbursementapp.exceptions.AccountNotFoundException;
import com.revature.reimbursementapp.exceptions.UnauthorizedException;
import com.revature.reimbursementapp.models.dtos.JwtDTO;
import com.revature.reimbursementapp.models.dtos.LoginRequestDTO;
import com.revature.reimbursementapp.models.Account;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Optional;

@Service
public class JwtService {

    @Value("${spring.application.name}")
    private String issuerName;

    @Value("${security.jwt.expiration}")
    private Long expiration;

    private final SecretKey secretKey;

    private final AccountService accountService;

    private final PasswordEncoder passwordEncoder;

    private final ObjectMapper objectMapper;

    @Autowired
    public JwtService(SecretKey secretKey, AccountService accountService, PasswordEncoder passwordEncoder, ObjectMapper objectMapper) {
        this.secretKey = secretKey;
        this.accountService = accountService;
        this.passwordEncoder = passwordEncoder;
        this.objectMapper = objectMapper;
    }

    public String generateJwtToken(LoginRequestDTO loginRequest) {
        Date issuedDate = new Date();
        Date expirationDate = new Date(issuedDate.getTime() + expiration);
        Account account = accountService.getAccountByUsername(loginRequest.getUsername());
        JwtDTO jwtDTO = new JwtDTO(account);

        if (passwordEncoder.matches(loginRequest.getRawPassword(), account.getPassword())) {
            return Jwts
                    .builder()
                    .claim("JwtDTO", jwtDTO)
                    .issuer(issuerName)
                    .signWith(this.secretKey)
                    .issuedAt(issuedDate)
                    .expiration(expirationDate)
                    .compact();
        } else {
            throw new UnauthorizedException();
        }
    }

    public JwtDTO parseJwtToken(String bearer) {
        if (!bearer.startsWith("Bearer ")) { throw new JwtException("invalid jwt header");}
        String token = bearer.split(" ")[1];

        JwtParser jwt = Jwts.parser().verifyWith(this.secretKey).build();
        Jws<Claims> claims = jwt.parseSignedClaims(token);
        Claims c = claims.getPayload();
        LinkedHashMap<?,?> jwtDTO = (LinkedHashMap<?,?>) c.get("JwtDTO", Object.class);
        JwtDTO returnJwt = objectMapper.convertValue(jwtDTO, JwtDTO.class);
        try {
            Account account = accountService.getAccountById(returnJwt.getAccountId());
            if (account.getRole().getRoleType() != returnJwt.getRoleType() ||
                !account.getUsername().equals(returnJwt.getUsername())) {
                throw new JwtException("Account changed generate a new JWT");
            }
        } catch (AccountNotFoundException e) {
            throw new JwtException("Account deleted");
        }
        return returnJwt;
    }
}