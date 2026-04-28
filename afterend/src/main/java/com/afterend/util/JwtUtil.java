package com.afterend.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

public class JwtUtil {
    // 密钥（实际项目中应该放在配置文件中）
    private static final String SECRET = "xueyouquan-secret-key-2024";
    // token过期时间：7天
    private static final long EXPIRE_TIME = 7 * 24 * 60 * 60 * 1000;

    /**
     * 生成JWT Token
     * @param userId 用户ID
     * @param role   用户角色
     * @return token字符串
     */
    public static String createToken(Long userId, String role) {
        return JWT.create()
                .withClaim("userId", userId)
                .withClaim("role", role)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRE_TIME))
                .sign(Algorithm.HMAC256(SECRET));
    }

    /**
     * 从token中获取用户ID
     */
    public static Long getUserId(String token) {
        try {
            DecodedJWT jwt = JWT.require(Algorithm.HMAC256(SECRET)).build().verify(token);
            return jwt.getClaim("userId").asLong();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 从token中获取角色
     */
    public static String getRole(String token) {
        try {
            DecodedJWT jwt = JWT.require(Algorithm.HMAC256(SECRET)).build().verify(token);
            return jwt.getClaim("role").asString();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 验证token是否有效
     */
    public static boolean verify(String token) {
        try {
            JWT.require(Algorithm.HMAC256(SECRET)).build().verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
