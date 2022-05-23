package uz.pdp.task1.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class GenerateToken {
    static long expireTime=36_000_000;
    static String secret="Spring";
    public String generateToken(String username){
        Date expDate=new Date(System.currentTimeMillis()+expireTime);
        return Jwts
                .builder()
                .setSubject(username)
                .setExpiration(expDate)
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public String getUsernameFromToken(String token){
        return Jwts
                .parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts
                    .parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token);
            return true;
        }
        catch (Exception e){
            return false;
        }

    }
}
