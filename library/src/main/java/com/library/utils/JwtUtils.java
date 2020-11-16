package com.library.utils;

import com.library.pojo.User;
import io.jsonwebtoken.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

@ConfigurationProperties("jwt.configuration")
@Getter
@Setter
public class JwtUtils {

    //秘钥
    private String secretKey;

    //过期时间
    private long ttl;

    /**
     * 生成token
     * @param user
     * @return
     */
    public String generateToken(User user){
        long currentTime = System.currentTimeMillis();
        long end = currentTime + ttl;
        return Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .claim("user",user)
                .setIssuedAt(new Date(currentTime))  //签发时间
                         .signWith(SignatureAlgorithm.HS256, secretKey) //加密方式
                .setExpiration(new Date(end))
                 .compact();

    }

    /**
     * 解析token
     * @param token
     * @return
     */
    public Claims parseJwt(String token){
        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        return claims;
    }

}
