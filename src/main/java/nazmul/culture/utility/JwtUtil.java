package nazmul.culture.utility;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    @Value("${culture.webapp.jwtValidity}")
    private long JWT_TOKEN_VALIDITY;

    @Value("${culture.webapp.jwtSecret}")
    private String jwtSecret;

    public String generateJwtToken(UserDetails userDetails){
        System.out.println("generateJwtToken() is been called");
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + JWT_TOKEN_VALIDITY))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public boolean validateJwtToken(String token){
        System.out.println("validateJwtToken() is been called");
        String username = getUsernameFromJwtToken(token);
        try {
            Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
        } catch (MalformedJwtException e) {
        } catch (ExpiredJwtException e) {
        } catch (UnsupportedJwtException e) {
        } catch (IllegalArgumentException e) {
        }
        return false;

    }

    public String getUsernameFromJwtToken(String token){
        System.out.println("getUsernameFromJwtToken() is been called");
        try {
            return Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (Exception e){
            System.out.println("failed to get username from jwt token");
            return null;
        }
    }



}
