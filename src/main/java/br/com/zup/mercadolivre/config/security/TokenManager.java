package br.com.zup.mercadolivre.config.security;

import java.util.Base64;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class TokenManager {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expirationInMillis;

    public String generateToken(Authentication authentication) {

        UserDetails user = (UserDetails) authentication.getPrincipal();

        final Date now = new Date();
        final Date expiration = new Date(now.getTime() + this.expirationInMillis);

        return Jwts.builder()
                .setIssuer("Desafio MercadoLivre ZUP Orange Talents")
                .setSubject(user.getUsername())
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, Base64.getEncoder().encodeToString(this.secret.getBytes()))
                .compact();
    }

    public boolean isValid(String jwt) {
        try {
            Jwts.parser().setSigningKey(Base64.getEncoder().encodeToString(this.secret.getBytes())).parseClaimsJws(jwt.split(" ")[1]);
            return true;

        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String getUserName(String jwt) {
        Claims claims = Jwts.parser().setSigningKey(Base64.getEncoder().encodeToString(this.secret.getBytes()))
                .parseClaimsJws(jwt).getBody();

        return claims.getSubject();
    }
}
