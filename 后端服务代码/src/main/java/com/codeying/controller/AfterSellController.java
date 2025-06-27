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

/** 售后申请控制器 关于售后申请的增删改查操作都在这 */
@Controller
@RequestMapping("afterSell")
public class AfterSellController extends BaseController {

  // 售后申请列表数据
  @RequestMapping("list")
  @ResponseBody
  public ApiResult list(Integer current, Integer size) {
    current = current == null ? 1 : current; // 默认第一页
    size = size == null ? 20 : size; // 默认每页条数
    // 获取列表数据
    QueryWrapper<AfterSell> queryWrapper = getQueryWrapper(); // 分页大小
    IPage<AfterSell> pageInfo = new Page<AfterSell>().setCurrent(current).setSize(size); // 分页大小
    pageInfo = afterSellService.page(pageInfo, queryWrapper);

    // 循环遍历list数据获取外键数据
    for (AfterSell stAv : pageInfo.getRecords()) {
      // 获取外键数据:用户
      stAv.setUseridFrn(userService.getById(stAv.getUserid()));
      // 获取外键数据:用户订单
      stAv.setOidFrn(orderService.getById(stAv.getOid()));
    }

    PagerVO<AfterSell> pagerVO = new PagerVO<>(pageInfo); // 可以承载除了page的额外信息

    return successData(pagerVO);
  }

  private QueryWrapper<AfterSell> getQueryWrapper() {
    // 用于存储查询的条件
    QueryWrapper<AfterSell> paramMap = new QueryWrapper<>();
    String id = req.getParameter("id");
    paramMap.eq(!StringUtils.isEmpty(id), "id", id);
    String userid = req.getParameter("userid");
    if (!StringUtils.isEmpty(userid)) {
      paramMap.eq("userid", userid);
    }
    String oid = req.getParameter("oid");
    if (!StringUtils.isEmpty(oid)) {
      paramMap.eq("oid", oid);
    }
    String typer = req.getParameter("typer");
    if (!StringUtils.isEmpty(typer)) {
      paramMap.like("typer", typer);
    }
    String content = req.getParameter("content");
    if (!StringUtils.isEmpty(content)) {
      paramMap.like("content", content);
    }
    String createtimeL = req.getParameter("createtimeL");
    String createtimeR = req.getParameter("createtimeR");
    if (!StringUtils.isEmpty(createtimeL)) {
      paramMap.ge("createtime", DateUtil.strToDate(createtimeL));
    }
    if (!StringUtils.isEmpty(createtimeR)) {
      paramMap.le("createtime", DateUtil.strToDate(createtimeR));
    }
    String state = req.getParameter("state");
    if (!StringUtils.isEmpty(state)) {
      paramMap.eq("state", state);
    }
    // User  只能查看 和自己相关的信息！
    if (req.getSession().getAttribute("role").equals("user")) {
      paramMap.eq("userid", ((User) req.getSession().getAttribute("user")).getId()); // 只能查看和自己相关的内容
    }

    String orderByStr = "id desc"; // 默认根据id降序排序
    // 默认按照id排序
    paramMap.last("order by " + orderByStr);
    return paramMap;
  }

  // 售后申请详情
  @RequestMapping("detail")
  @ResponseBody
  public ApiResult detail(String id) {
    AfterSell entity = afterSellService.getById(id);
    // 获取外键数据：用户
    entity.setUseridFrn(userService.getById(entity.getUserid()));
    // 获取外键数据：用户订单
    entity.setOidFrn(orderService.getById(entity.getOid()));
    return successData(entity);
  }

  // 售后申请保存
  @RequestMapping("save")
  @ResponseBody
  public ApiResult save(@RequestBody AfterSell entityTemp) {
    String id = entityTemp.getId(); // 主键
    String userid = entityTemp.getUserid(); // 用户
    String oid = entityTemp.getOid(); // 订单号
    String typer = entityTemp.getTyper(); // 售后类型
    String content = entityTemp.getContent(); // 要求售后内容
    String pic = entityTemp.getPic(); // 产品问题拍照上传
    Date createtime = entityTemp.getCreatetime(); // 售后申请时间
    String state = entityTemp.getState(); // 处理状态
    String chuljg = entityTemp.getChuljg(); // 处理结果

    // 新增或更新
    if (entityTemp.getId() == null || "".equals(entityTemp.getId())) { // 新增
      id = CommonUtils.newId();
      entityTemp.setId(id);
      createtime = new Date();
      entityTemp.setCreatetime(createtime);
      state = "待处理";
      entityTemp.setState(state);
      chuljg = "";
      entityTemp.setChuljg(chuljg);
      // 唯一字段，则在此校验
      QueryWrapper<AfterSell> wrapperoid = new QueryWrapper();
      wrapperoid.eq("oid", entityTemp.getOid());
      if (afterSellService.list(wrapperoid).size() > 0) {
        return fail("订单号 已存在！");
      }
      // =========添加之前1
      Order oo = orderService.getById(oid);
      oo.setState("售后中");
      orderService.updateById(oo);
      // =========添加之前2
      afterSellService.save(entityTemp);
    } else {
      // =========修改之前1
      AfterSell old = afterSellService.getById(id);
      if (old.getState().equals("已处理")) {
        return fail("已处理的售后无需再次处理！");
      }
      if ("已处理".equals(state)) {
        Order oo = orderService.getById(oid);
        oo.setState("售后完成");
        orderService.updateById(oo);
      }
      // =========修改之前2
      afterSellService.updateById(entityTemp);
    }
    return ApiResult.successMsg("操作成功"); // 返回保存成功
  }

  // 售后申请删除
  @RequestMapping("delete")
  @ResponseBody
  public ApiResult delete(String id) {
    AfterSell delTemp = afterSellService.getById(id);
    // =========删除之前1

    // =========删除之前2
    // 根据ID删除记录
    afterSellService.removeById(id);
    return success();
  }
}

