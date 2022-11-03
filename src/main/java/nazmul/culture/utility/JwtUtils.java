package nazmul.culture.utility;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class JwtUtils {
    public static final long TOKEN_VALIDITY = 1 * 60 * 1000; // 2 min

    //cultureServiceSecretKey
    @Value("${secret}") // aha: this is the server's private key. Which is used to generate new tokens.
    private String jwtSecret;

    public String generateJwtToken(UserDetails userDetails) {
        System.out.println("JwtUtils generateJwtToken(UserDetails) call: 7");
        Map<String, Object> claims = new HashMap<>();
        //return Jwts.builder().setClaims(claims)
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY ))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    } //generateJwtToken() ends here

    public Boolean validateJwtToken(String token, UserDetails userDetails) {
        System.out.println("JwtUtils validateJwtToken(String token, UserDetails) With token: Call: B");
        String username = getUsernameFromToken(token);
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        Boolean isTokenExpired = claims.getExpiration().before(new Date());
        return (username.equals(userDetails.getUsername()) && !isTokenExpired);
    }

    public String getUsernameFromToken(String token) {
        System.out.println("JwtUtils getUsernameFromToken(String token) With token: Call: A");
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();

     /*   Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        try{
            claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        }catch (Exception e){
            System.out.println("could not parse JWT token for claims");
        }
        return claims.getSubject();

      */
    } // getUsernameFromToken() ends here




}
