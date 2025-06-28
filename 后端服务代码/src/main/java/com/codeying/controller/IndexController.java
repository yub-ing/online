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

  /**
   * 首页访问
   *
   * @return
   */
  @RequestMapping({"welcome", "/"})
  @ResponseBody
  public ApiResult welcome() {
    Map<String, Object> res = new HashMap<>();
    LoginUser user = getCurrentUser();
    String action = req.getParameter("action"); // 操作实体
    String entityName = req.getParameter("entityName"); // 操作实体
    String id = req.getParameter("id");
    // 默认实体
    if (StringUtils.isEmpty(entityName)) {
      entityName = "goods";
    }
    res.put("entityName", entityName);
    // 我的收藏数
    if (user == null) {
      res.put("myStartCount", 0);
    } else {
      long starCount =
          starService.count(
              new LambdaQueryWrapper<Star>()
                  .eq(Star::getUserid, user.getId())
                  .eq(Star::getType, "收藏"));
      res.put("myStartCount", starCount);
    }

    if (StringUtils.isEmpty(action)) {
      // 商品信息首页和详情页
      if (entityName.equals("goods")) {
        // 热门
        List<Star> hotList = starService.topN("goods", 4);
        res.put("hotList", hotList);
        if (StringUtils.isEmpty(id)) { // 首页
          // 查询条件
          String title = req.getParameter("title");
          if (!StringUtils.isEmpty(title)) {
            res.put("title", title);
          }
          // 公告
          List<Notice> notices = noticeService.list();
          res.put("notices", notices);
          // 数据
          List<Goods> itemList =
              goodsService.list(
                  new LambdaQueryWrapper<Goods>()
                      .like(!StringUtils.isEmpty(title), Goods::getShowtitle, title));
          res.put("itemList", itemList);
          return successData(res);
        } else {
          // 详情页
          Goods detail = goodsService.getById(id);
          // 获取外键数据:商品类型
          detail.setLeixFrn(stypeService.getById(detail.getLeix()));
          // 获取外键数据:商家
          detail.setMidFrn(merchantService.getById(detail.getMid()));
          res.put("detail", detail);
          // 收藏数
          long starCount =
              starService.count(
                  new LambdaQueryWrapper<Star>()
                      .eq(Star::getItemid, detail.getId())
                      .eq(Star::getType, "收藏"));
          res.put("starCount", starCount);
          long praiseCount =
              starService.count(
                  new LambdaQueryWrapper<Star>()
                      .eq(Star::getItemid, detail.getId())
                      .eq(Star::getType, "点赞"));
          res.put("praiseCount", praiseCount);
          return successData(res);
        }
      }
    } else {
      if (user == null) {
        return fail("获取收藏失败，用户未登录");
      }
      if (action.equals("stars")) {
        List<Star> stars = starService.userStar(user.getId(), user.getRole());
        res.put("entityName", "star");
        res.put("stars", stars);
        return successData(res);
      }
    }
    return fail();
  }

  /**
   * 收藏查询
   *
   * @return
   */
  @RequestMapping("welcome2")
  @ResponseBody
  public ApiResult welcome2(String action, String entityName, String id, String op) {
    LoginUser user = getCurrentUser();

    if (action.equals("hasStar")) {
      if (user != null) {
        String starId = starService.hasStar(user.getId(), user.getRole(), id, entityName, op);
        return ApiResult.successData(starId); // 返回starId，前端判断不为空则是收藏了
      } else {
        return ApiResult.successData(null); // 标记成功
      }
    }

    if (user == null) {
      return fail("请先登录");
    }
    // 收藏
    if (action.equals("star")) {
      boolean res = starService.star(user.getId(), user.getRole(), id, entityName, op);
      if (res) {
        return (ApiResult.successMsg(op + "成功")); // 标记成功
      } else {
        return (ApiResult.fail("已" + op + "过了~"));
      }
    }
    // 取消收藏
    else if (action.equals("cancelStar")) {
      starService.remove(
          new LambdaQueryWrapper<Star>()
              .eq(Star::getItemid, id)
              .eq(Star::getType, op)
              .eq(Star::getUserid, user.getId()));
      return ApiResult.success(); // 标记成功
    }
    return ApiResult.success();
  }

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
    return successData(l);
  }
}

