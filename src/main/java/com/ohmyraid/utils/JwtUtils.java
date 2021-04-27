package com.ohmyraid.utils;

import com.ohmyraid.common.result.CommonNoAuthenticationException;
import com.ohmyraid.common.result.CommonNoSessionException;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
@Component
public class JwtUtils {

    @Value("${spring.jwt.issuer}")
    private String ISSUER;

    @Value("${spring.jwt.secret-key}")
    private String SECRET_KEY;

    /**
     * Access Token 생성한다.
     *
     * @param id
     * @param name
     * @return
     */
    public String createAccessToken(String id, String name) {
        // Access Token 생성
        String accessToken = Jwts.builder().setIssuer(ISSUER).setIssuedAt(new Date())
                .claim("LoginId", id).claim("UserName", name)
                .signWith(SignatureAlgorithm.HS512,SECRET_KEY)
                .setExpiration(new Date(System.currentTimeMillis()+3600000)) //1시간
                .compact();
        return accessToken;
    }

    /**
     * 토큰 만료시간 검사
     * @param token
     * @return
     */
    public Boolean isTokenValid(String token) {
        try {
            final Date expiration = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token)
                    .getBody().getExpiration();
            log.debug("JwtUtils :: isTokenExpired return is {}", expiration);
            return expiration.after(new Date());
        }catch(ExpiredJwtException e){
            log.error("Token is Expired");
            throw new CommonNoSessionException();
        }
    }
}
