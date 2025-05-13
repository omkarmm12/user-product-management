package com.omkar.user_product_management.JwtUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {

    @Value("${spring.security.secrete.key}")
    private String SECRETE_KEY;

    @Value("${spring.security.expire.time}")
    private int EXPIRE_TIME;

    public String generateToken(UserDetails userDetails){
        String email = userDetails.getUsername();
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime()+EXPIRE_TIME))
                .signWith(key())
                .compact();
    }

    public String getTokenFromRequest(HttpServletRequest request){
        String header = request.getHeader("Authorization");
        if(header != null && header.startsWith("Bearer")){
            return header.substring(7);
        }
        return null;
    }

    public String extractUsername(String token){
        return Jwts.parserBuilder()
                .setSigningKey(key())
                .build().parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token){
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parseClaimsJws(token);
            Date expireDate = claims.getBody().getExpiration();
            return expireDate != null && expireDate.after(new Date());
    }

    public Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRETE_KEY));
    }
}
