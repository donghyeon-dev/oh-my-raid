package com.ohmyraid.utils;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class JwtUtils {

    private String issuer = "ohmyraid";

    /**
     * Access Token 생성한다.
     *
     * @param id
     * @param userNm
     * @return
     */
    public String createAccessToken(String id, String userNm) {
        // Access Token 생성
        String accessToken = Jwts.builder().setIssuer(issuer).setIssuedAt(new Date())
                .claim("LoginId", id).claim("UserName", userNm)
                .signWith(SignatureAlgorithm.HS512,id)
                .setExpiration(new Date(System.currentTimeMillis()+3600000)) //1시간
                .compact();
        return accessToken;
    }
}
