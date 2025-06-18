package com.codeying.component.interceptor;

import com.codeying.component.ApiResult;
import com.codeying.component.TokenService;
import com.codeying.entity.LoginUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 登录拦截器
 * 防止没有登录而访问
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {
    private Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private TokenService tokenService;

    public static Map<String,LoginUser> userMap = new ConcurrentHashMap<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        log.debug(request.getRequestURL().toString());
        String token = request.getHeader("token");
        //无token
        if (StringUtils.isBlank(token)) {
            if(request.getRequestURI().contains("/welcome")){
                return true;
            }
            unauthorized(response);
            return false;
        }
        //token校验失败
        boolean validateToken = tokenService.validateToken(token);
        if (!validateToken){
            if(request.getRequestURI().contains("/welcome")){
                return true;
            }
            unauthorized(response);
            return false;
        }
        //登录信息为空
        LoginUser loginResult = userMap.get(token);//tokens不应该是不变的用户信息
        if (ObjectUtils.isEmpty(loginResult)) {
            if(request.getRequestURI().contains("/welcome")){
                return true;
            }
            unauthorized(response);
            return false;
        }
        request.getSession().setAttribute("token",token);
        request.getSession().setAttribute("user",loginResult);
        request.getSession().setAttribute("role",loginResult.getRole());
        //刷新token有效时间
        tokenService.refreshToken(token);
        return true;
    }

    ObjectMapper objectMapper = new ObjectMapper();
    private void unauthorized(HttpServletResponse response) throws IOException {
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = response.getWriter();
        ApiResult<String> apiResult = ApiResult.fail("token有误");
        apiResult.setCode(302);
        out.print(objectMapper.writeValueAsString(apiResult));
        out.close();
        out.flush();
    }

}
