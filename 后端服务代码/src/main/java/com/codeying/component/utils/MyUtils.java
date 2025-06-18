package com.codeying.component.utils;

import javax.servlet.http.HttpServletRequest;
import com.codeying.entity.LoginUser;
public class MyUtils {

    /**
     * 获取当前用户
     */
    public static LoginUser getUser(HttpServletRequest req){
        return (LoginUser) req.getSession().getAttribute("user");
    }

    /**
     * 判断字符串是否为空
     */
    public static boolean isEmpty(Object e){
        return !isNotEmpty(e);
    }
    public static boolean isNotEmpty(Object s){
        if(s == null){
            return false;
        }
        return isNotEmpty((String) s);
    }
    public static boolean isNotEmpty(String s){
        if(s==null || "".equals(s)){
            return false;
        }
        return true;
    }

    public static Integer strToInt(String s){
        if(isEmpty(s)){
            return null;
        }
        try {
            return Integer.parseInt(s);
        }catch (Exception e){
            return null;
        }
    }
    public static Double strToDouble(String s){
        if(isEmpty(s)){
            return null;
        }
        try {
            return Double.parseDouble(s);
        }catch (Exception e){
            return null;
        }
    }

}
