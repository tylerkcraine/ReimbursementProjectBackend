package com.revature.reimbursementapp.services;

import com.revature.reimbursementapp.dtos.LoginRequestDTO;
import com.revature.reimbursementapp.models.Account;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    @Value("${spring.application.name}")
    private String issuerName;

    @Value("${security.jwt.expiration}")
    private Long expiration;

    private final SecretKey secretKey;

    private final AccountService accountService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public JwtService(SecretKey secretKey, AccountService accountService, PasswordEncoder passwordEncoder) {
        this.secretKey = secretKey;
        this.accountService = accountService;
        this.passwordEncoder = passwordEncoder;
    }

    public String generateJwtToken(LoginRequestDTO loginRequest) {
        Date issuedDate = new Date();
        Date expirationDate = new Date(issuedDate.getTime() + expiration);
        Account account = accountService.getAccountByUsername(loginRequest.getUsername());

        if (passwordEncoder.matches(loginRequest.getRawPassword(), account.getPassword())) {
            return Jwts
                    .builder()
                    .claim("account", account)
                    .issuer(issuerName)
                    .signWith(this.secretKey)
                    .issuedAt(issuedDate)
                    .expiration(expirationDate)
                    .compact();
        } else {
            return "";
        }
    }

    public String parseJwtToken(String token) {
        Jwt<?,?> jwt = Jwts.parser().verifyWith(this.secretKey).build().parse(token);
        String s = jwt.toString();
        return s;
    }
}
