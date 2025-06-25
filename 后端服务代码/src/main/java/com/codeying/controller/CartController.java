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

/** 购物车控制器 关于购物车的增删改查操作都在这 */
@Controller
@RequestMapping("cart")
public class CartController extends BaseController {

  // 购物车列表数据
  @RequestMapping("list")
  @ResponseBody
  public ApiResult list(Integer current, Integer size) {
    current = current == null ? 1 : current; // 默认第一页
    size = size == null ? 20 : size; // 默认每页条数
    // 获取列表数据
    QueryWrapper<Cart> queryWrapper = getQueryWrapper(); // 分页大小
    IPage<Cart> pageInfo = new Page<Cart>().setCurrent(current).setSize(size); // 分页大小
    pageInfo = cartService.page(pageInfo, queryWrapper);

    // 循环遍历list数据获取外键数据
    for (Cart stAv : pageInfo.getRecords()) {
      // 获取外键数据:用户
      stAv.setUseridFrn(userService.getById(stAv.getUserid()));
      // 获取外键数据:商品信息
      stAv.setGoodsidFrn(goodsService.getById(stAv.getGoodsid()));
    }

    PagerVO<Cart> pagerVO = new PagerVO<>(pageInfo); // 可以承载除了page的额外信息

    StringBuilder statisticInfo = new StringBuilder();
    {
      QueryWrapper<Cart> sumQueryWrapper = queryWrapper.clone();
      sumQueryWrapper.select("IFNULL(SUM(count), 0) AS total");
      Map<String, Object> sumResult = cartService.getMap(sumQueryWrapper); // 获取SUM结果
      BigDecimal totalSum =
          sumResult != null ? new BigDecimal(sumResult.get("total").toString()) : BigDecimal.ZERO;
      statisticInfo.append("总数量：" + totalSum + "；");
    }
    {
      QueryWrapper<Cart> sumQueryWrapper = queryWrapper.clone();
      sumQueryWrapper.select("IFNULL(SUM(zongj), 0) AS total");
      Map<String, Object> sumResult = cartService.getMap(sumQueryWrapper); // 获取SUM结果
      BigDecimal totalSum =
          sumResult != null ? new BigDecimal(sumResult.get("total").toString()) : BigDecimal.ZERO;
      statisticInfo.append("总总价：" + totalSum + "；");
    }
    pagerVO.setStatisticInfo(statisticInfo.toString());

    return successData(pagerVO);
  }

  private QueryWrapper<Cart> getQueryWrapper() {
    // 用于存储查询的条件
    QueryWrapper<Cart> paramMap = new QueryWrapper<>();
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
    // User  只能查看 和自己相关的信息！
    if (req.getSession().getAttribute("role").equals("user")) {
      paramMap.eq("userid", ((User) req.getSession().getAttribute("user")).getId()); // 只能查看和自己相关的内容
    }

    String orderByStr = "id desc"; // 默认根据id降序排序
    // 默认按照id排序
    paramMap.last("order by " + orderByStr);
    return paramMap;
  }

  // 购物车详情
  @RequestMapping("detail")
  @ResponseBody
  public ApiResult detail(String id) {
    Cart entity = cartService.getById(id);
    // 获取外键数据：用户
    entity.setUseridFrn(userService.getById(entity.getUserid()));
    // 获取外键数据：商品信息
    entity.setGoodsidFrn(goodsService.getById(entity.getGoodsid()));
    return successData(entity);
  }

  // 购物车保存
  @RequestMapping("save")
  @ResponseBody
  public ApiResult save(@RequestBody Cart entityTemp) {
    String id = entityTemp.getId(); // 主键
    String userid = entityTemp.getUserid(); // 用户
    String goodsid = entityTemp.getGoodsid(); // 商品
    String tustp = entityTemp.getTustp(); // 商品图片
    Double price = entityTemp.getPrice(); // 价格
    Integer count = entityTemp.getCount(); // 数量
    Double zongj = entityTemp.getZongj(); // 总价
    Date createtime = entityTemp.getCreatetime(); // 加购时间

    // 新增或更新
    if (entityTemp.getId() == null || "".equals(entityTemp.getId())) { // 新增
      id = CommonUtils.newId();
      entityTemp.setId(id);
      tustp = goodsService.getById(goodsid).getShowpic();
      entityTemp.setTustp(tustp);
      price = goodsService.getById(goodsid).getPrice();
      entityTemp.setPrice(price);
      zongj = price * count;
      entityTemp.setZongj(zongj);
      createtime = new Date();
      entityTemp.setCreatetime(createtime);
      // =========添加之前1
      QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
      queryWrapper.eq("userid", userid);
      queryWrapper.eq("goodsid", goodsid);
      if (cartService.count(queryWrapper) > 0) {
        return fail("购物车已经有该商品了");
      }
      Goods food = goodsService.getById(goodsid);
      if (food.getCount() < count) {
        return fail("很抱歉 数量不足！");
      }
      // =========添加之前2
      cartService.save(entityTemp);
    } else {
      // =========修改之前1
      zongj = price * count;
      entityTemp.setZongj(zongj);
      // =========修改之前2
      cartService.updateById(entityTemp);
    }
    return ApiResult.successMsg("操作成功"); // 返回保存成功
  }

  // 购物车删除
  @RequestMapping("delete")
  @ResponseBody
  public ApiResult delete(String id) {
    Cart delTemp = cartService.getById(id);
    // =========删除之前1

    // =========删除之前2
    // 根据ID删除记录
    cartService.removeById(id);
    return success();
  }
}

