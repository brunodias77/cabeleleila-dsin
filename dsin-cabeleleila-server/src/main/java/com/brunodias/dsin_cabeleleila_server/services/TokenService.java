package com.brunodias.dsin_cabeleleila_server.services;

import com.brunodias.dsin_cabeleleila_server.configurations.security.users.ApplicationUserDetaillsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import java.security.Key;
import java.util.Date;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import java.util.List;


@Service
public class TokenService {

    @Value("${application.security.jwt.secret-key}") // Injeta o valor da propriedade
    private String secretKey;

    @Value("${application.security.jwt.expiration}") // Injeta o valor da propriedade
    private long jwtExpiration;

    private static final Logger logger = LoggerFactory.getLogger(TokenService.class);


    public String generateJwtTokenForUser(Authentication authentication){
        ApplicationUserDetaillsImpl userPrincipal = (ApplicationUserDetaillsImpl) authentication.getPrincipal();
        List<String> roles = userPrincipal.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpiration))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }

    public String getUserNameFromToken(String token){
        return Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token){
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parse(token);
            return true;  // O token é válido
        } catch (MalformedJwtException e) {
            logger.error("Token Jwt invalido : {} ", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("Token Expirado : {} ", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("Esse token nao e suportado : {} ", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("Nenhuma reivindicação encontrada : {} ", e.getMessage());
        }
        return false;
    }

}

