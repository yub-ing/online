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

/** 用户订单控制器 关于用户订单的增删改查操作都在这 */
@Controller
@RequestMapping("order")
public class OrderController extends BaseController {

  // 用户订单列表数据
  @RequestMapping("list")
  @ResponseBody
  public ApiResult list(Integer current, Integer size) {
    current = current == null ? 1 : current; // 默认第一页
    size = size == null ? 20 : size; // 默认每页条数
    // 获取列表数据
    QueryWrapper<Order> queryWrapper = getQueryWrapper(); // 分页大小
    IPage<Order> pageInfo = new Page<Order>().setCurrent(current).setSize(size); // 分页大小
    pageInfo = orderService.page(pageInfo, queryWrapper);

    // 循环遍历list数据获取外键数据
    for (Order stAv : pageInfo.getRecords()) {
      // 获取外键数据:用户
      stAv.setUseridFrn(userService.getById(stAv.getUserid()));
      // 获取外键数据:商家
      stAv.setMidFrn(merchantService.getById(stAv.getMid()));
    }

    PagerVO<Order> pagerVO = new PagerVO<>(pageInfo); // 可以承载除了page的额外信息

    StringBuilder statisticInfo = new StringBuilder();
    {
      QueryWrapper<Order> sumQueryWrapper = queryWrapper.clone();
      sumQueryWrapper.select("IFNULL(SUM(price), 0) AS total");
      Map<String, Object> sumResult = orderService.getMap(sumQueryWrapper); // 获取SUM结果
      BigDecimal totalSum =
          sumResult != null ? new BigDecimal(sumResult.get("total").toString()) : BigDecimal.ZERO;
      statisticInfo.append("总总价格：" + totalSum + "；");
    }
    pagerVO.setStatisticInfo(statisticInfo.toString());

    return successData(pagerVO);
  }

  private QueryWrapper<Order> getQueryWrapper() {
    // 用于存储查询的条件
    QueryWrapper<Order> paramMap = new QueryWrapper<>();
    String id = req.getParameter("id");
    paramMap.eq(!StringUtils.isEmpty(id), "id", id);
    String userid = req.getParameter("userid");
    if (!StringUtils.isEmpty(userid)) {
      paramMap.eq("userid", userid);
    }
    String numb = req.getParameter("numb");
    if (!StringUtils.isEmpty(numb)) {
      paramMap.like("numb", numb);
    }
    String mid = req.getParameter("mid");
    if (!StringUtils.isEmpty(mid)) {
      paramMap.eq("mid", mid);
    }
    String state = req.getParameter("state");
    if (!StringUtils.isEmpty(state)) {
      paramMap.eq("state", state);
    }
    String wuliu = req.getParameter("wuliu");
    if (!StringUtils.isEmpty(wuliu)) {
      paramMap.like("wuliu", wuliu);
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

  // 用户订单详情
  @RequestMapping("detail")
  @ResponseBody
  public ApiResult detail(String id) {
    Order entity = orderService.getById(id);
    // 获取外键数据：用户
    entity.setUseridFrn(userService.getById(entity.getUserid()));
    // 获取外键数据：商家
    entity.setMidFrn(merchantService.getById(entity.getMid()));
    return successData(entity);
  }

  // 用户订单保存
  @RequestMapping("save")
  @ResponseBody
  public ApiResult save(@RequestBody Order entityTemp) {
    String id = entityTemp.getId(); // 主键
    String userid = entityTemp.getUserid(); // 用户
    String numb = entityTemp.getNumb(); // 订单号
    String sname = entityTemp.getSname(); // 收货人
    String stele = entityTemp.getStele(); // 收货人电话
    String saddr = entityTemp.getSaddr(); // 收货人地址
    String dingdbz = entityTemp.getDingdbz(); // 订单备注
    Date createtime = entityTemp.getCreatetime(); // 下单时间
    String mid = entityTemp.getMid(); // 商家
    String content = entityTemp.getContent(); // 订单内容
    String details = entityTemp.getDetails(); // 订单详情
    Double price = entityTemp.getPrice(); // 总价格
    String state = entityTemp.getState(); // 状态
    String wuliu = entityTemp.getWuliu(); // 物流单号
    String wulxx = entityTemp.getWulxx(); // 物流信息
    String pingj = entityTemp.getPingj(); // 评价

    // 新增或更新
    if (entityTemp.getId() == null || "".equals(entityTemp.getId())) { // 新增
      id = CommonUtils.newId();
      entityTemp.setId(id);
      numb = "YD" + DateUtil.dateToStr(new Date(), "yyyyMMddHHmmss");
      entityTemp.setNumb(numb);
      createtime = new Date();
      entityTemp.setCreatetime(createtime);
      mid = "";
      entityTemp.setMid(mid);
      content = "";
      entityTemp.setContent(content);
      details = "";
      entityTemp.setDetails(details);
      price = 0.0;
      entityTemp.setPrice(price);
      state = "已下单";
      entityTemp.setState(state);
      wuliu = "";
      entityTemp.setWuliu(wuliu);
      wulxx = "";
      entityTemp.setWulxx(wulxx);
      pingj = "";
      entityTemp.setPingj(pingj);
      // =========添加之前1
      Map<String, Order> orderMap = new HashMap<>(); // 每个商家合并一个order
      List<Cart> cartList = cartService.list(new QueryWrapper<Cart>().eq("userid", userid));
      List<Orderdetail> orderdetailList = new ArrayList<>();
      List<Goods> foods = new ArrayList<>();
      for (Cart cart : cartList) {
        // 获取食物信息
        Goods food = goodsService.getBy("id", cart.getGoodsid());
        food.setCount(food.getCount() - cart.getCount());
        foods.add(food);
        if (food.getCount() < 0) {
          return ApiResult.fail(food.getShowtitle() + "库存不足");
        }
        // 订单信息
        Order order = orderMap.get(food.getMid());
        if (order == null) {
          order = new Order();
          order.setId(CommonUtils.newId());
          order.setUserid(userid);
          order.setCreatetime(createtime);
          order.setMid(food.getMid());
          order.setSname(sname);
          order.setNumb("YD"+DateUtil.dateToStr(new Date(),"yyyyMMddHHmmss"));
          order.setStele(stele);
          order.setSaddr(saddr);
          order.setDingdbz(dingdbz);
          order.setContent("");
          order.setDetails("");
          order.setPrice(0.0);
          order.setState("已下单");
          order.setPingj("");
          orderMap.put(food.getMid(), order);
        }
        // 计算每一家的总价
        order.setPrice(order.getPrice() + cart.getZongj()); // 价格++
        // 订单内容
        order.setContent(order.getContent() + food.getShowtitle() + "*" + cart.getCount() + " ");
        // 订单详情字符串
        order.setDetails(
            order.getDetails()
                + food.getShowtitle()
                + "*"
                + cart.getCount()
                + "，单价"
                + cart.getPrice()
                + "，总价"
                + cart.getZongj()
                + "\n");
        // 订单详情
        Orderdetail orderdetail = new Orderdetail();
        orderdetail.setId(CommonUtils.newId());
        orderdetail.setUserid(userid);
        orderdetail.setMid(food.getMid());
        orderdetail.setGoodsid(cart.getGoodsid());
        orderdetail.setTustp(food.getShowpic());
        orderdetail.setPrice(cart.getPrice());
        orderdetail.setCount(cart.getCount());
        orderdetail.setZongj(cart.getZongj());
        orderdetail.setCreatetime(createtime);
        orderdetailList.add(orderdetail);
      }
      orderdetailService.saveBatch(orderdetailList);
      // foods更新
      for (Goods food : foods) {
        goodsService.updateById(food);
      }
      for (Cart cart : cartList) {
        cartService.removeById(cart.getId());
      }
      if (1 < 2) {
        orderService.saveBatch(orderMap.values());
        return ApiResult.successMsg("结算成功");
      }
      // =========添加之前2
      orderService.save(entityTemp);
    } else {
      // =========修改之前1

      // =========修改之前2
      orderService.updateById(entityTemp);
    }
    return ApiResult.successMsg("操作成功"); // 返回保存成功
  }

  // 用户订单删除
  @RequestMapping("delete")
  @ResponseBody
  public ApiResult delete(String id) {
    Order delTemp = orderService.getById(id);
    // =========删除之前1

    // =========删除之前2
    // 根据ID删除记录
    orderService.removeById(id);
    return success();
  }
}

