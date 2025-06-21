package com.codeying.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import java.util.List;

import java.io.Serializable;
/** 商品信息实体类 */
@TableName("tb_goods")
public class Goods implements Serializable {

  /** 主键 */
  @TableId private String id;

  /** 首页图片 */
  @TableField("showpic")
  private String showpic;

  /** 商品名称 */
  @TableField("showtitle")
  private String showtitle;

  /** 简介 */
  @TableField("showdesc")
  private String showdesc;

  /** 类型 */
  @TableField("leix")
  private String leix;

  @TableField(exist = false)
  private Stype leixFrn;
  /** 商家 */
  @TableField("mid")
  private String mid;

  @TableField(exist = false)
  private Merchant midFrn;
  /** 价格 */
  @TableField("price")
  private Double price;

  /** 商品内容 */
  @TableField("showdetail")
  private String showdetail;

  /** 库存数量 */
  @TableField("count")
  private Integer count;

  /** 发布时间 */
  @TableField("publishtime")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date publishtime;

  /** 展示 */
  @TableField("vv")
  private String vv;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getShowpic() {
    return showpic;
  }

  public void setShowpic(String showpic) {
    this.showpic = showpic;
  }

  public String getShowtitle() {
    return showtitle;
  }

  public void setShowtitle(String showtitle) {
    this.showtitle = showtitle;
  }

  public String getShowdesc() {
    return showdesc;
  }

  public void setShowdesc(String showdesc) {
    this.showdesc = showdesc;
  }

  public String getLeix() {
    return leix;
  }

  public void setLeix(String leix) {
    this.leix = leix;
  }

  public Stype getLeixFrn() {
    return leixFrn;
  }

  public void setLeixFrn(Stype v) {
    this.leixFrn = v;
  }

  public String getMid() {
    return mid;
  }

  public void setMid(String mid) {
    this.mid = mid;
  }

  public Merchant getMidFrn() {
    return midFrn;
  }

  public void setMidFrn(Merchant v) {
    this.midFrn = v;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public String getShowdetail() {
    return showdetail;
  }

  public void setShowdetail(String showdetail) {
    this.showdetail = showdetail;
  }

  public Integer getCount() {
    return count;
  }

  public void setCount(Integer count) {
    this.count = count;
  }

  public Date getPublishtime() {
    return publishtime;
  }

  public void setPublishtime(Date publishtime) {
    this.publishtime = publishtime;
  }

  public String getVv() {
    return vv;
  }

  public void setVv(String vv) {
    this.vv = vv;
  }
}

