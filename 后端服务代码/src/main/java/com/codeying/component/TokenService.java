package com.codeying.component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.codeying.component.utils.CacheUtil;
import com.codeying.component.utils.CommonUtils;
import com.codeying.entity.LoginUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;


/**
 * token处理类
 */
@Service
public class TokenService{
    Logger log = LoggerFactory.getLogger(getClass());

    private static final String USER_REDIS_KEY_PRE = "USER_CONTEXT_KEY:";
    private static final String USER_ID_KEY = "userId";
    private static final String USER_NAME_KEY = "userName";

    private Algorithm ALGORITHM = Algorithm.HMAC512("xmeport-mh");

    @Autowired
    private CacheUtil cacheUtil;

    public String createToken(LoginUser pmsUser) {
        String key = CommonUtils.newId();
        String token = JWT.create()
                .withClaim(USER_ID_KEY, pmsUser.getId())
                .withClaim(USER_NAME_KEY, pmsUser.getUsername())
                .withSubject(key)
                .sign(ALGORITHM);
        cacheUtil.set(USER_REDIS_KEY_PRE + key, pmsUser, 600);
        return token;
    }


    public void refreshToken(String token) {
        try{
            String key = getTokenKey(token);
            cacheUtil.expire(USER_REDIS_KEY_PRE + key, 60*30);
        }catch (Exception e) {
            log.error("刷新Token失败:无效token！", e);
        }
    }

    public String getUserId(String token) {
        Map<String, Claim> claimsMap = JWT.decode(token).getClaims();
        return claimsMap.get(USER_ID_KEY).asString();
    }

    public String getUserName(String token) {
        Map<String, Claim> claimsMap = JWT.decode(token).getClaims();
        return claimsMap.get(USER_NAME_KEY).asString();
    }



    public LoginUser parseToken(String token) {
        LoginUser pmsUser = null;
        try {
            String key = getTokenKey(token);
            pmsUser = cacheUtil.getV(USER_REDIS_KEY_PRE + key);
        } catch (Exception e) {
            log.error("无效token！", e);
        }
        return pmsUser;
    }

    public LoginUser getLoginInfo(String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        return parseToken(token);
    }

    public String getTokenKey(String token) {
        return JWT.decode(token).getSubject();
    }

    public void removeLoginInfo(String token) {
        if (StringUtils.hasText(token)) {
            try{
                String key = getTokenKey(token);
                cacheUtil.del(USER_REDIS_KEY_PRE + key);
            }catch (Exception e) {
                log.error("删除用户信息失败:无效token！", e);
            }
        }
    }

    public boolean validateToken(String token) {
        try{
            JWTVerifier verifier = JWT.require(ALGORITHM).build();
            DecodedJWT jwt = verifier.verify(token);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    public void removeToken(String token) {
        String key = getTokenKey(token);
        cacheUtil.del(USER_REDIS_KEY_PRE + key);
    }
}
