package com.tfh.blog.utils.jwt;

import cn.hutool.core.util.RandomUtil;
import com.tfh.blog.utils.redis.Redis;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * @author tang
 * @version 1.0
 * @since 2024/5/19 15:04
 * TODO: 实现 token 功能
 */
@Component
public class Token {
    @Autowired
    private Redis redis;

    /**
     * token前缀
     */
    private static final String TOKEN_PREFIX = "Bearer ";

    /**
     * redis前缀
     */
    private static final String REDIS_PREFIX = "TOKEN:PREFIX:";

    /**
     * 授权头
     */
    private static final String AUTHORIZATION = "Authorization";

    /**
     * 加密密钥
     */
    private static byte[] ENCRYPTION_KEYS;


    /**
     * 创建 token
     *
     * @param userId    用户id
     * @param authority 用户权限
     * @return 返回字符串类型的 token
     */
    public String obtainToken(long userId, String authority) {

        isENCRYPTION_KEYS();

        SecretKey secretKey = Keys.hmacShaKeyFor(ENCRYPTION_KEYS);
        String token = Jwts.builder()
                .claim("userId", String.valueOf(userId))
                .claim("authority", authority)
                .expiration(new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000))
                .signWith(secretKey)
                .compact();

        return TOKEN_PREFIX + token;
    }


    /**
     * 检测 token 是否有问题，没有则返回解析后的对象
     *
     * @param bearer 有前缀的token
     * @return 没有问题则返回该对象，否则返回 null
     */
    public Jws<Claims> parseToken(String bearer) {

        String token = analysisTokenPrefix(bearer);

        isENCRYPTION_KEYS();

        if (token == null) {
            return null;
        }

        SecretKey secretKey = Keys.hmacShaKeyFor(ENCRYPTION_KEYS);
        Jws<Claims> claimsJws = null;
        try {
            claimsJws = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token);
        } catch (JwtException e) {

        }

        return claimsJws;
    }


    /**
     * 去掉 token 的前缀
     *
     * @param bearer 有前缀的 token
     * @return 返回 token 字符串,如果去不掉则返回 null
     */
    private String analysisTokenPrefix(String bearer) {

        String[] s = null;
        try {
            s = bearer.split(" ");

            if (!s[0].equals("Bearer")) {
                return null;
            }
        } catch (RuntimeException e) {
            return null;
        }


        return s[1];
    }

    /**
     * 根据用户返回的 token 获取 id 值
     *
     * @param req HttpServletRequest对象
     * @return 返回id ,如果过期或者验证失败则返回 null
     */
    public Long getTokenById(HttpServletRequest req) {
        String bearer = req.getHeader("Authorization");
        Jws<Claims> claimsJws = parseToken(bearer);
        if (claimsJws == null) {
            return null;
        }

        return Long.parseLong((String) claimsJws.getPayload().get("userId"));
    }

    /**
     * 此方法用于判断当前 ENCRYPTION_KEYS 是否存在
     */
    private void isENCRYPTION_KEYS() {
        if (redis.get("ENCRYPTION_KEYS") == null || ENCRYPTION_KEYS == null) {
            byte[] bytes = RandomUtil.randomBytes(4096);
            redis.set("ENCRYPTION_KEYS", "1", 7 * 24 * 60 * 60 * 1000);
            ENCRYPTION_KEYS = bytes;
            System.out.println("激活成功");
        }
    }

    public static String getAUTHORIZATION() {
        return AUTHORIZATION;
    }

    public static String getREDIS_PREFIX() {
        return REDIS_PREFIX;
    }
}
