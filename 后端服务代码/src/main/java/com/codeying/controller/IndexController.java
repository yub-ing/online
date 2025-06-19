package com.codeying.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.codeying.component.ApiResult;
import com.codeying.component.TokenService;
import com.codeying.component.interceptor.LoginInterceptor;
import com.codeying.mapper.SqlMapper;
import com.codeying.mapper.SqlMapper.*;
import com.codeying.component.VerifiedCodeGenerator;
import com.codeying.component.utils.CacheUtil;
import com.codeying.component.utils.CommonUtils;
import com.codeying.entity.*;
import org.apache.commons.io.IOUtils;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.codeying.service.*;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.sql.*;
import java.util.*;
import java.util.Date;

/** 登陆、注册、登出 */
@Controller
public class IndexController extends BaseController {
  @Autowired private TokenService tokenService;
  @Autowired CacheUtil cacheUtil;
  @Autowired private SqlMapper sqlMapper;

  @GetMapping("/captcha")
  @ResponseBody
  public ApiResult<Map<String, Object>> captcha() {
    ByteArrayOutputStream outputStream = null;
    try {
      outputStream = new ByteArrayOutputStream();
      String code = VerifiedCodeGenerator.generateVerifyCode(4, VerifiedCodeGenerator.JUST_NUMBER);
      VerifiedCodeGenerator.outputImage(100, 50, outputStream, code);
      byte[] images = outputStream.toByteArray();
      String imageStr = Base64Utils.encodeToString(images);
      Map<String, Object> resMap = new HashMap<>();
      String captchaKey = "Captcha" + CommonUtils.newId();
      cacheUtil.set(captchaKey, code, 600);
      resMap.put("captchaKey", captchaKey);
      resMap.put("captcha", imageStr);
      return successData(resMap);
    } catch (Exception e) {
      return fail(e.getMessage());
    } finally {
      IOUtils.closeQuietly(outputStream);
    }
  }

  @RequestMapping("login")
  @ResponseBody
  public ApiResult<Map<String, Object>> login(
      String captcha, String captchaKey, String username, String password, String usertype)
      throws Exception {
    Map<String, Object> apiRes = new HashMap<>();
    // 校验验证码
    String captchaOrigin = cacheUtil.remove(captchaKey);
    if (captcha == null || !captcha.equalsIgnoreCase(captchaOrigin)) {
      return fail("验证码错误");
    }
    // 登陆的用户
    LoginUser loginUser;
    if (usertype.equals("admin")) {
      QueryWrapper<Admin> wrapper = new QueryWrapper<>();
      wrapper.eq("username", username);
      wrapper.eq("password", password);
      loginUser = adminService.getOne(wrapper);
      if (loginUser != null) {
        String token = tokenService.createToken(loginUser);
        apiRes.put("token", token);
        apiRes.put("user", loginUser);
        LoginInterceptor.userMap.put(token, loginUser);
        return successData(apiRes);
      }
    }
    if (usertype.equals("merchant")) {
      QueryWrapper<Merchant> wrapper = new QueryWrapper<>();
      wrapper.eq("username", username);
      wrapper.eq("password", password);
      loginUser = merchantService.getOne(wrapper);
      if (loginUser != null) {
        String token = tokenService.createToken(loginUser);
        apiRes.put("token", token);
        apiRes.put("user", loginUser);
        LoginInterceptor.userMap.put(token, loginUser);
        return successData(apiRes);
      }
    }
    if (usertype.equals("user")) {
      QueryWrapper<User> wrapper = new QueryWrapper<>();
      wrapper.eq("username", username);
      wrapper.eq("password", password);
      loginUser = userService.getOne(wrapper);
      if (loginUser != null) {
        String token = tokenService.createToken(loginUser);
        apiRes.put("token", token);
        apiRes.put("user", loginUser);
        LoginInterceptor.userMap.put(token, loginUser);
        return successData(apiRes);
      }
    }
    // 登陆失败，就重新登陆
    return fail("账号密码有误，登陆失败！");
  }

  @PostMapping("register")
  @ResponseBody
  public ApiResult<String> register(@RequestBody Map<String, String> map) throws Exception {
    String username = map.get("username");
    String password = map.get("password");
    String usertype = map.get("usertype");
    if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
      return fail("账号密码不可为空！");
    }
    if (usertype.equals("admin")) {
      QueryWrapper<Admin> wrapper = new QueryWrapper<>();
      wrapper.eq("username", username);
      Admin temp = adminService.getOne(wrapper);
      if (temp != null) {
        return fail("账号已存在！");
      }
      Admin admin = new Admin();
      admin.setId(CommonUtils.newId());
      admin.setUsername(username);
      admin.setPassword(password);
      String id = ""; // 主键
      admin.setId(CommonUtils.newId());
      String name = ""; // 姓名
      String tele = ""; // 电话
      adminService.save(admin);
      return successMsg("注册成功，请登陆");
    }
    if (usertype.equals("merchant")) {
      QueryWrapper<Merchant> wrapper = new QueryWrapper<>();
      wrapper.eq("username", username);
      Merchant temp = merchantService.getOne(wrapper);
      if (temp != null) {
        return fail("账号已存在！");
      }
      Merchant merchant = new Merchant();
      merchant.setId(CommonUtils.newId());
      merchant.setUsername(username);
      merchant.setPassword(password);
      String id = ""; // 主键
      merchant.setId(CommonUtils.newId());
      String name = ""; // 商家名
      String avatar = ""; // 头像
      String tele = ""; // 电话
      String xiaodjj = ""; // 小店简介
      String zhuynr = ""; // 主营内容
      merchantService.save(merchant);
      return successMsg("注册成功，请登陆");
    }
    if (usertype.equals("user")) {
      QueryWrapper<User> wrapper = new QueryWrapper<>();
      wrapper.eq("username", username);
      User temp = userService.getOne(wrapper);
      if (temp != null) {
        return fail("账号已存在！");
      }
      User user = new User();
      user.setId(CommonUtils.newId());
      user.setUsername(username);
      user.setPassword(password);
      String id = ""; // 主键
      user.setId(CommonUtils.newId());
      String name = ""; // 姓名
      String avatar = ""; // 头像
      String gender = ""; // 性别
      Integer age = 0; // 年龄
      String tele = ""; // 电话
      String youx = ""; // 邮箱
      String place = ""; // 地址
      userService.save(user);
      return successMsg("注册成功，请登陆");
    }
    // 注册失败
    return successMsg("请选择角色类型");
  }

  /**
   * 退出登录
   *
   * @return
   */
  @PostMapping("/logout")
  @ResponseBody
  public ApiResult<String> loginOut(HttpServletRequest request) {
    try {
      tokenService.removeLoginInfo(request.getHeader("token"));
      return success();
    } catch (Exception e) {
      e.printStackTrace();
      return fail(e.getMessage());
    }
  }

  @GetMapping("/userinfo")
  @ResponseBody
  public ApiResult<LoginUser> getUser() {
    return successData(getCurrentUser());
  }

  // echarts数据
  @PostMapping("hello")
  @ResponseBody
  public ApiResult<List<Echart>> helloData() throws SQLException {
    // 数据
    List<Echart> l = new ArrayList<>();
    {
      // 图表
      Echart echart = new Echart();
      echart.setName("数");
      echart.setType("n"); // line pie
      // echart.setValue(studentService.count()+"");
      // l.add(echart.init());
    }
    {
      // 图表
      Echart echart = new Echart();
      echart.setName("统计");
      echart.setType("bar"); // line pie
      // echart.setDtos(sqlMapper.exec("select id as name,score as value from tb_score"));
      // l.add(echart.init());
    }
    return successData(l);
  }
}

