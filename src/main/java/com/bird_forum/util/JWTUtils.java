package com.bird_forum.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class JWTUtils {

    // 创建 jwt
    public static String create(Long id) {
        Map<String, Object> header = new HashMap<>();
        header.put("alg", "HS256");
        header.put("typ", "JWT");

        Map<String, Object> payload = new HashMap<>();
        payload.put("id", id);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 30);

        String token = Jwts.builder()
                .setHeader(header)
                .setClaims(payload)
                .setExpiration(calendar.getTime())
                .signWith(SignatureAlgorithm.HS256, "secret")
                .compact();

        return token;
    }

    // 解析 jwt
    public static Long parse(String token) {
        Map<String, Object> body = (Map<String, Object>) Jwts.parser().parse(token).getBody();

        return (Long) body.get("id");
    }
}
