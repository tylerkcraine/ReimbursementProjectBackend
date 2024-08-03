package com.revature.reimbursementapp.Service;

import com.revature.reimbursementapp.Model.Account;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    @Value("${spring.application.name}")
    private String issuerName;

    @Value("${security.jwt.expiration}")
    private Long expiration;

    private final SecretKey secretKey;

    @Autowired
    public JwtService(SecretKey secretKey) {
        this.secretKey = secretKey;
    }

    public String generateJwtToken(Account account) {
        Date issuedDate = new Date();
        Date expirationDate = new Date(issuedDate.getTime() + expiration);

        return Jwts
                .builder()
                .claim("account", account)
                .issuer(issuerName)
                .signWith(this.secretKey)
                .issuedAt(issuedDate)
                .expiration(expirationDate)
                .compact();
    }

    public String parseJwtToken(String token) {
        String s = Jwts.parser().verifyWith(this.secretKey).build().parse(token).toString();
        return s;
    }
}
