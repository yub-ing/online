package com.codeying.controller;

import com.codeying.component.interceptor.LoginInterceptor;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import org.springframework.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.codeying.component.*;
import com.codeying.component.utils.*;
import com.codeying.entity.*;
import com.codeying.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import java.io.InputStream;
import java.util.*;
import java.io.File;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.FileUtils;
import javax.servlet.ServletOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;

/** 用户控制器 关于用户的增删改查操作都在这 */
@Controller
@RequestMapping("user")
public class UserController extends BaseController {

  // 用户列表数据
  @RequestMapping("list")
  @ResponseBody
  public ApiResult list(Integer current, Integer size) {
    current = current == null ? 1 : current; // 默认第一页
    size = size == null ? 20 : size; // 默认每页条数
    // 获取列表数据
    QueryWrapper<User> queryWrapper = getQueryWrapper(); // 分页大小
    IPage<User> pageInfo = new Page<User>().setCurrent(current).setSize(size); // 分页大小
    pageInfo = userService.page(pageInfo, queryWrapper);

    PagerVO<User> pagerVO = new PagerVO<>(pageInfo); // 可以承载除了page的额外信息

    StringBuilder statisticInfo = new StringBuilder();
    {
      QueryWrapper<User> sumQueryWrapper = queryWrapper.clone();
      sumQueryWrapper.select("IFNULL(avg(age), 0) AS value"); // 设置聚合查询字段
      Map<String, Object> avgResult = userService.getMap(sumQueryWrapper); // 获取SUM结果
      BigDecimal totalSum =
          avgResult != null ? new BigDecimal(avgResult.get("value").toString()) : BigDecimal.ZERO;
      totalSum = totalSum.setScale(2, RoundingMode.HALF_UP); // 四舍五入
      statisticInfo.append("平均年龄：" + totalSum + "；");
    }
    pagerVO.setStatisticInfo(statisticInfo.toString());

    return successData(pagerVO);
  }

  private QueryWrapper<User> getQueryWrapper() {
    // 用于存储查询的条件
    QueryWrapper<User> paramMap = new QueryWrapper<>();
    String id = req.getParameter("id");
    paramMap.eq(!StringUtils.isEmpty(id), "id", id);
    String username = req.getParameter("username");
    if (!StringUtils.isEmpty(username)) {
      paramMap.like("username", username);
    }
    String name = req.getParameter("name");
    if (!StringUtils.isEmpty(name)) {
      paramMap.like("name", name);
    }
    String gender = req.getParameter("gender");
    if (!StringUtils.isEmpty(gender)) {
      paramMap.eq("gender", gender);
    }

    String orderByStr = "id desc"; // 默认根据id降序排序
    // 默认按照id排序
    paramMap.last("order by " + orderByStr);
    return paramMap;
  }

  // 用户详情
  @RequestMapping("detail")
  @ResponseBody
  public ApiResult detail(String id) {
    User entity = userService.getById(id);
    return successData(entity);
  }

  // 用户保存
  @RequestMapping("save")
  @ResponseBody
  public ApiResult save(@RequestBody User entityTemp) {
    String id = entityTemp.getId(); // 主键
    String username = entityTemp.getUsername(); // 用户名
    String password = entityTemp.getPassword(); // 密码
    String name = entityTemp.getName(); // 姓名
    String avatar = entityTemp.getAvatar(); // 头像
    String gender = entityTemp.getGender(); // 性别
    Integer age = entityTemp.getAge(); // 年龄
    String tele = entityTemp.getTele(); // 电话
    String youx = entityTemp.getYoux(); // 邮箱
    String place = entityTemp.getPlace(); // 地址

    // 新增或更新
    if (entityTemp.getId() == null || "".equals(entityTemp.getId())) { // 新增
      id = CommonUtils.newId();
      entityTemp.setId(id);
      // 唯一字段，则在此校验
      QueryWrapper<User> wrapperusername = new QueryWrapper();
      wrapperusername.eq("username", entityTemp.getUsername());
      if (userService.list(wrapperusername).size() > 0) {
        return fail("用户名 已存在！");
      }
      // 唯一字段，则在此校验
      QueryWrapper<User> wrappername = new QueryWrapper();
      wrappername.eq("name", entityTemp.getName());
      if (userService.list(wrappername).size() > 0) {
        return fail("姓名 已存在！");
      }
      // =========添加之前1

      // =========添加之前2
      userService.save(entityTemp);
    } else {
      // =========修改之前1

      // =========修改之前2
      userService.updateById(entityTemp);
      if (id.equals(getCurrentUser().getId())) { // 刷新用户session
        String token = (String) getSessionValue("token");
        LoginInterceptor.userMap.put(token, userService.getById(id));
      }
    }
    return ApiResult.successMsg("操作成功"); // 返回保存成功
  }

  // 用户删除
  @RequestMapping("delete")
  @ResponseBody
  public ApiResult delete(String id) {
    User delTemp = userService.getById(id);
    // =========删除之前1

    // =========删除之前2
    // 根据ID删除记录
    userService.removeById(id);
    return success();
  }
}

