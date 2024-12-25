package com.SocialNet.SocialNetwork.Security;


import com.SocialNet.SocialNetwork.DTO.JwtAuthenticationDTO;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

//import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;


import javax.crypto.SecretKey;

@Component
public class JWTService {

    private static final Logger LOGGER = LogManager.getLogger(JWTService.class);

    @Value("c506558b364ad0dc9d45508b0e172ac5fa1d262e3d18363768c9222da1b925b42929864c6e663dec416db3c1e136f7c325f4adb51280e9ba6b71b0cd80104dd03153d43f86c47e4921d889755f7694158dc1c4825ff8324e35dbb1dccec7ad544ddf60057a1c92ae710d7277daab3a6adce875cdf0940710d105f0f7857dc78f50ff34783f780dd486b546dba9c77f4af25ea962d073167933e933028b97657953fe720ab501a649a2ebe6f3c2dc22f52ebfd1e3ae8d66c15394aabbd9a9fde77350b4ebce6c7c74d76a8aecc9aa0d32d2e1491a492b2e33d53a161677cc755556689bcaa5c43abd363eeebdaaba134d4f10b574c430e2533680549f746a5118")
    private String jwtsecret;

    public JwtAuthenticationDTO generateJwtAuthToken(String email) {
        JwtAuthenticationDTO jwtDTO = new JwtAuthenticationDTO();
        jwtDTO.setToken(generateJwtToken(email));
        jwtDTO.setRefreshToken(generateJwtToken(email));
        return jwtDTO;
    }

    public JwtAuthenticationDTO refreshBaseToken(String email, String refreshToken) {
            JwtAuthenticationDTO jwtDTO = new JwtAuthenticationDTO();
            jwtDTO.setToken(generateJwtToken(email));
            jwtDTO.setRefreshToken(refreshToken);
            return jwtDTO;
    }

    public boolean validateJwtToken(String token) {
        try{
            Jwts.parser().verifyWith(getSingInKey()).build().parseSignedClaims(token).getPayload();
            return true;
        }

        catch (ExpiredJwtException e){
            LOGGER.error("Expired JWT token", e);
        }
        catch (UnsupportedJwtException e){
            LOGGER.error("Unsupported JWT token", e);
        }
        catch (MalformedJwtException e){
            LOGGER.error("Malformed JWT token", e);
        }
        catch (SecurityException e){
            LOGGER.error("Security exception", e);
        }
        catch(Exception e){
            LOGGER.error("Exception", e);
        }
        return false;
    }

    public String getEmailFromToken(String token) {
        Claims claims = Jwts.parser().verifyWith(getSingInKey()).build().parseSignedClaims(token).getPayload();
        return claims.getSubject();
    }

    private String generateJwtToken(String email){
        Date date = Date.from(LocalDateTime.now().plusMinutes(1).atZone(ZoneId.systemDefault()).toInstant());
        return Jwts.builder().subject(email).expiration(date).signWith(getSingInKey()).compact();
    }

    private String generateRefreshToken(String email){
        Date date = Date.from(LocalDateTime.now().plusDays(1).atZone(ZoneId.systemDefault()).toInstant());
        return Jwts.builder().subject(email).expiration(date).signWith(getSingInKey()).compact();
    }

    private SecretKey getSingInKey() {
        byte[] KeyBytes = Decoders.BASE64.decode(jwtsecret);
        return Keys.hmacShaKeyFor(KeyBytes);
    }

}
