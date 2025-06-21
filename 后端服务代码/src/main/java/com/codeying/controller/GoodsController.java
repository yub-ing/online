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

/** 商品信息控制器 关于商品信息的增删改查操作都在这 */
@Controller
@RequestMapping("goods")
public class GoodsController extends BaseController {

  // 商品信息列表数据
  @RequestMapping("list")
  @ResponseBody
  public ApiResult list(Integer current, Integer size) {
    current = current == null ? 1 : current; // 默认第一页
    size = size == null ? 20 : size; // 默认每页条数
    // 获取列表数据
    QueryWrapper<Goods> queryWrapper = getQueryWrapper(); // 分页大小
    IPage<Goods> pageInfo = new Page<Goods>().setCurrent(current).setSize(size); // 分页大小
    pageInfo = goodsService.page(pageInfo, queryWrapper);

    // 循环遍历list数据获取外键数据
    for (Goods stAv : pageInfo.getRecords()) {
      // 获取外键数据:商品类型
      stAv.setLeixFrn(stypeService.getById(stAv.getLeix()));
      // 获取外键数据:商家
      stAv.setMidFrn(merchantService.getById(stAv.getMid()));
    }

    PagerVO<Goods> pagerVO = new PagerVO<>(pageInfo); // 可以承载除了page的额外信息

    return successData(pagerVO);
  }

  private QueryWrapper<Goods> getQueryWrapper() {
    // 用于存储查询的条件
    QueryWrapper<Goods> paramMap = new QueryWrapper<>();
    String id = req.getParameter("id");
    paramMap.eq(!StringUtils.isEmpty(id), "id", id);
    String showtitle = req.getParameter("showtitle");
    if (!StringUtils.isEmpty(showtitle)) {
      paramMap.like("showtitle", showtitle);
    }
    String leix = req.getParameter("leix");
    if (!StringUtils.isEmpty(leix)) {
      paramMap.eq("leix", leix);
    }
    String mid = req.getParameter("mid");
    if (!StringUtils.isEmpty(mid)) {
      paramMap.eq("mid", mid);
    }
    String publishtimeL = req.getParameter("publishtimeL");
    String publishtimeR = req.getParameter("publishtimeR");
    if (!StringUtils.isEmpty(publishtimeL)) {
      paramMap.ge("publishtime", DateUtil.strToDate(publishtimeL));
    }
    if (!StringUtils.isEmpty(publishtimeR)) {
      paramMap.le("publishtime", DateUtil.strToDate(publishtimeR));
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

  // 商品信息详情
  @RequestMapping("detail")
  @ResponseBody
  public ApiResult detail(String id) {
    Goods entity = goodsService.getById(id);
    // 获取外键数据：商品类型
    entity.setLeixFrn(stypeService.getById(entity.getLeix()));
    // 获取外键数据：商家
    entity.setMidFrn(merchantService.getById(entity.getMid()));
    return successData(entity);
  }

  // 商品信息保存
  @RequestMapping("save")
  @ResponseBody
  public ApiResult save(@RequestBody Goods entityTemp) {
    String id = entityTemp.getId(); // 主键
    String showpic = entityTemp.getShowpic(); // 首页图片
    String showtitle = entityTemp.getShowtitle(); // 商品名称
    String showdesc = entityTemp.getShowdesc(); // 简介
    String leix = entityTemp.getLeix(); // 类型
    String mid = entityTemp.getMid(); // 商家
    Double price = entityTemp.getPrice(); // 价格
    String showdetail = entityTemp.getShowdetail(); // 商品内容
    Integer count = entityTemp.getCount(); // 库存数量
    Date publishtime = entityTemp.getPublishtime(); // 发布时间
    String vv = entityTemp.getVv(); // 展示

    // 新增或更新
    if (entityTemp.getId() == null || "".equals(entityTemp.getId())) { // 新增
      id = CommonUtils.newId();
      entityTemp.setId(id);
      publishtime = new Date();
      entityTemp.setPublishtime(publishtime);
      // 唯一字段，则在此校验
      QueryWrapper<Goods> wrappershowtitle = new QueryWrapper();
      wrappershowtitle.eq("showtitle", entityTemp.getShowtitle());
      if (goodsService.list(wrappershowtitle).size() > 0) {
        return fail("商品名称 已存在！");
      }
      // =========添加之前1

      // =========添加之前2
      goodsService.save(entityTemp);
    } else {
      // =========修改之前1

      // =========修改之前2
      goodsService.updateById(entityTemp);
    }
    return ApiResult.successMsg("操作成功"); // 返回保存成功
  }

  // 商品信息删除
  @RequestMapping("delete")
  @ResponseBody
  public ApiResult delete(String id) {
    Goods delTemp = goodsService.getById(id);
    // =========删除之前1

    // =========删除之前2
    // 根据ID删除记录
    goodsService.removeById(id);
    return success();
  }
}

