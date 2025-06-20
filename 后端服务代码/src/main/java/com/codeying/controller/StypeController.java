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

/** 商品类型控制器 关于商品类型的增删改查操作都在这 */
@Controller
@RequestMapping("stype")
public class StypeController extends BaseController {

  // 商品类型列表数据
  @RequestMapping("list")
  @ResponseBody
  public ApiResult list(Integer current, Integer size) {
    current = current == null ? 1 : current; // 默认第一页
    size = size == null ? 20 : size; // 默认每页条数
    // 获取列表数据
    QueryWrapper<Stype> queryWrapper = getQueryWrapper(); // 分页大小
    IPage<Stype> pageInfo = new Page<Stype>().setCurrent(current).setSize(size); // 分页大小
    pageInfo = stypeService.page(pageInfo, queryWrapper);

    PagerVO<Stype> pagerVO = new PagerVO<>(pageInfo); // 可以承载除了page的额外信息

    return successData(pagerVO);
  }

  private QueryWrapper<Stype> getQueryWrapper() {
    // 用于存储查询的条件
    QueryWrapper<Stype> paramMap = new QueryWrapper<>();
    String id = req.getParameter("id");
    paramMap.eq(!StringUtils.isEmpty(id), "id", id);
    String name = req.getParameter("name");
    if (!StringUtils.isEmpty(name)) {
      paramMap.like("name", name);
    }

    String orderByStr = "id desc"; // 默认根据id降序排序
    // 默认按照id排序
    paramMap.last("order by " + orderByStr);
    return paramMap;
  }

  // 商品类型详情
  @RequestMapping("detail")
  @ResponseBody
  public ApiResult detail(String id) {
    Stype entity = stypeService.getById(id);
    return successData(entity);
  }

  // 商品类型保存
  @RequestMapping("save")
  @ResponseBody
  public ApiResult save(@RequestBody Stype entityTemp) {
    String id = entityTemp.getId(); // 主键
    String name = entityTemp.getName(); // 类型名
    String describtion = entityTemp.getDescribtion(); // 说明

    // 新增或更新
    if (entityTemp.getId() == null || "".equals(entityTemp.getId())) { // 新增
      id = CommonUtils.newId();
      entityTemp.setId(id);
      // 唯一字段，则在此校验
      QueryWrapper<Stype> wrappername = new QueryWrapper();
      wrappername.eq("name", entityTemp.getName());
      if (stypeService.list(wrappername).size() > 0) {
        return fail("类型名 已存在！");
      }
      // =========添加之前1

      // =========添加之前2
      stypeService.save(entityTemp);
    } else {
      // =========修改之前1

      // =========修改之前2
      stypeService.updateById(entityTemp);
    }
    return ApiResult.successMsg("操作成功"); // 返回保存成功
  }

  // 商品类型删除
  @RequestMapping("delete")
  @ResponseBody
  public ApiResult delete(String id) {
    Stype delTemp = stypeService.getById(id);
    // =========删除之前1

    // =========删除之前2
    // 根据ID删除记录
    stypeService.removeById(id);
    return success();
  }
}

