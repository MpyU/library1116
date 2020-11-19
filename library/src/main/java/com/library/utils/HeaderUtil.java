package com.library.utils;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
@Component
public class HeaderUtil {
    @Autowired
    private static JwtUtils jwtUtils;
    //从请求中解析出use
    public static Map<String,Object> mapparseHeaderToUser(HttpServletRequest httpServletRequest){
        //token命名为Authization
        String header= httpServletRequest.getHeader("Authization");
        Claims claims=jwtUtils.parseJwt(header);
        Map<String,Object> user = (Map<String, Object>) claims.get("user");
        return user;
    }
}
