package com.hyy.server.config.security.component;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Description
 * JwtToken工具类
 *
 * @author helaxest
 * @date 2021/03/15  9:35
 * @since
 */
@Component
public class JwtTokenUtil {
    //jwt荷载里面用户名的key为sub
    private static final String CLAIM_KEY_USERNAME = "sub";
    //jwt荷载创建时间的key为created
    private static final String CLAIM_KEY_CREATED = "created";
    //jwt的秘钥以及失效时间从yml里swagger配置用value注解拿
    @Value("${jwt.secret}")
    private String secret;//秘钥
    @Value("${jwt.expiration}")
    private Long expiration;//失效时间


    //1.根据用户名去生成token
    //2. 生成token之后,可以从token里拿用户名
    // 判断token是否失效
    // token是否可以被刷新

    /**
     * 根据用户信息生成token
     *
     * @param userDetails 用户具体信息
     * @return
     */
    public String generateToken(UserDetails userDetails) {

        Map<String, Object> claims = new HashMap<>(); //准备荷载
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());//荷载里放用户名
        claims.put(CLAIM_KEY_CREATED, new Date());//荷载里放当前时间
        return generateToken(claims);
    }

    /**
     * 根据荷载生成JwT Token
     *
     * @param claims
     * @return
     */
    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)//荷载
                .setExpiration(generateExpirationDate())//失效时间
                .signWith(SignatureAlgorithm.HS512, secret)//签名,秘钥
                .compact();
    }

    /**
     * 生成失效时间
     *
     * @return
     */
    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 100000);
    }

    /**
     * 从token中获取用户名
     *
     * @param token
     * @return
     */
    public String getUserNameFromToken(String token) {
        String username;
        try {
            //先从token里面获取荷载,荷载里有用户名
            Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();//？？？？？？？待学习的地方
        } catch (Exception e) {
            username = null;
        }
        return username;

    }

    /**
     * 从token里获取荷载
     *
     * @param token
     * @return
     */
    private Claims getClaimsFromToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return claims;
    }

    /**
     * 判断token是否失效,依据有两个token是否过期,token里面荷载里的用户名是否一致
     *
     * @param token
     * @param userDetails
     * @return
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        String username = getUserNameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);

    }

    /**
     * 判断token是否失效
     *
     * @param token
     * @return
     */
    private boolean isTokenExpired(String token) {
        Date expireDate = getExpiredDateFromToken(token);
        return expireDate.before(new Date());
    }

    /**
     * 从token中获取过期时间
     *
     * @param token
     * @return
     */
    private Date getExpiredDateFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getExpiration();
    }

    /**
     * 判断token是否可以刷新
     *
     * @param token
     * @return
     */
    public boolean canBeRefresh(String token) {
        return !isTokenExpired(token);
    }

    /**
     * 刷新token
     *
     * @param token
     * @return
     */
    public String refreshToken(String token) {
        Claims claims = getClaimsFromToken(token);
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

}
