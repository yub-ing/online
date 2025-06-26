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

/** 订单详情控制器 关于订单详情的增删改查操作都在这 */
@Controller
@RequestMapping("orderdetail")
public class OrderdetailController extends BaseController {

  // 订单详情列表数据
  @RequestMapping("list")
  @ResponseBody
  public ApiResult list(Integer current, Integer size) {
    current = current == null ? 1 : current; // 默认第一页
    size = size == null ? 20 : size; // 默认每页条数
    // 获取列表数据
    QueryWrapper<Orderdetail> queryWrapper = getQueryWrapper(); // 分页大小
    IPage<Orderdetail> pageInfo = new Page<Orderdetail>().setCurrent(current).setSize(size); // 分页大小
    pageInfo = orderdetailService.page(pageInfo, queryWrapper);

    // 循环遍历list数据获取外键数据
    for (Orderdetail stAv : pageInfo.getRecords()) {
      // 获取外键数据:用户
      stAv.setUseridFrn(userService.getById(stAv.getUserid()));
      // 获取外键数据:商品信息
      stAv.setGoodsidFrn(goodsService.getById(stAv.getGoodsid()));
      // 获取外键数据:商家
      stAv.setMidFrn(merchantService.getById(stAv.getMid()));
    }

    PagerVO<Orderdetail> pagerVO = new PagerVO<>(pageInfo); // 可以承载除了page的额外信息

    return successData(pagerVO);
  }

  private QueryWrapper<Orderdetail> getQueryWrapper() {
    // 用于存储查询的条件
    QueryWrapper<Orderdetail> paramMap = new QueryWrapper<>();
    String id = req.getParameter("id");
    paramMap.eq(!StringUtils.isEmpty(id), "id", id);
    String userid = req.getParameter("userid");
    if (!StringUtils.isEmpty(userid)) {
      paramMap.eq("userid", userid);
    }
    String goodsid = req.getParameter("goodsid");
    if (!StringUtils.isEmpty(goodsid)) {
      paramMap.eq("goodsid", goodsid);
    }
    String mid = req.getParameter("mid");
    if (!StringUtils.isEmpty(mid)) {
      paramMap.eq("mid", mid);
    }
    // User  只能查看 和自己相关的信息！
    if (req.getSession().getAttribute("role").equals("user")) {
      paramMap.eq("userid", ((User) req.getSession().getAttribute("user")).getId()); // 只能查看和自己相关的内容
    }
    // Merchant  只能查看 和自己相关的信息！
    if (req.getSession().getAttribute("role").equals("merchant")) {
      paramMap.eq(
          "mid", ((Merchant) req.getSession().getAttribute("user")).getId()); // 只能查看和自己相关的内容
    }

    String orderByStr = "id desc"; // 默认根据id降序排序
    // 默认按照id排序
    paramMap.last("order by " + orderByStr);
    return paramMap;
  }

  // 订单详情详情
  @RequestMapping("detail")
  @ResponseBody
  public ApiResult detail(String id) {
    Orderdetail entity = orderdetailService.getById(id);
    // 获取外键数据：用户
    entity.setUseridFrn(userService.getById(entity.getUserid()));
    // 获取外键数据：商品信息
    entity.setGoodsidFrn(goodsService.getById(entity.getGoodsid()));
    // 获取外键数据：商家
    entity.setMidFrn(merchantService.getById(entity.getMid()));
    return successData(entity);
  }

  // 订单详情保存
  @RequestMapping("save")
  @ResponseBody
  public ApiResult save(@RequestBody Orderdetail entityTemp) {
    String id = entityTemp.getId(); // 主键
    String userid = entityTemp.getUserid(); // 用户
    String goodsid = entityTemp.getGoodsid(); // 商品
    String mid = entityTemp.getMid(); // 商家
    String tustp = entityTemp.getTustp(); // 商品图片
    Double price = entityTemp.getPrice(); // 价格
    Integer count = entityTemp.getCount(); // 数量
    Double zongj = entityTemp.getZongj(); // 总价
    Date createtime = entityTemp.getCreatetime(); // 时间

    // 新增或更新
    if (entityTemp.getId() == null || "".equals(entityTemp.getId())) { // 新增
      id = CommonUtils.newId();
      entityTemp.setId(id);
      createtime = new Date();
      entityTemp.setCreatetime(createtime);
      // =========添加之前1

      // =========添加之前2
      orderdetailService.save(entityTemp);
    } else {
      // =========修改之前1

      // =========修改之前2
      orderdetailService.updateById(entityTemp);
    }
    return ApiResult.successMsg("操作成功"); // 返回保存成功
  }

  // 订单详情删除
  @RequestMapping("delete")
  @ResponseBody
  public ApiResult delete(String id) {
    Orderdetail delTemp = orderdetailService.getById(id);
    // =========删除之前1

    // =========删除之前2
    // 根据ID删除记录
    orderdetailService.removeById(id);
    return success();
  }
}

