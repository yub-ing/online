package com.codeying.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import java.util.List;

import java.io.Serializable;
/** 用户订单实体类 */
@TableName("tb_order")
public class Order implements Serializable {

  /** 主键 */
  @TableId private String id;

  /** 用户 */
  @TableField("userid")
  private String userid;

  @TableField(exist = false)
  private User useridFrn;
  /** 订单号 */
  @TableField("numb")
  private String numb;

  /** 收货人 */
  @TableField("sname")
  private String sname;

  /** 收货人电话 */
  @TableField("stele")
  private String stele;

  /** 收货人地址 */
  @TableField("saddr")
  private String saddr;

  /** 订单备注 */
  @TableField("dingdbz")
  private String dingdbz;

  /** 下单时间 */
  @TableField("createtime")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date createtime;

  /** 商家 */
  @TableField("mid")
  private String mid;

  @TableField(exist = false)
  private Merchant midFrn;
  /** 订单内容 */
  @TableField("content")
  private String content;

  /** 订单详情 */
  @TableField("details")
  private String details;

  /** 总价格 */
  @TableField("price")
  private Double price;

  /** 状态 */
  @TableField("state")
  private String state;

  /** 物流单号 */
  @TableField("wuliu")
  private String wuliu;

  /** 物流信息 */
  @TableField("wulxx")
  private String wulxx;

  /** 评价 */
  @TableField("pingj")
  private String pingj;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUserid() {
    return userid;
  }

  public void setUserid(String userid) {
    this.userid = userid;
  }

  public User getUseridFrn() {
    return useridFrn;
  }

  public void setUseridFrn(User v) {
    this.useridFrn = v;
  }

  public String getNumb() {
    return numb;
  }

  public void setNumb(String numb) {
    this.numb = numb;
  }

  public String getSname() {
    return sname;
  }

  public void setSname(String sname) {
    this.sname = sname;
  }

  public String getStele() {
    return stele;
  }

  public void setStele(String stele) {
    this.stele = stele;
  }

  public String getSaddr() {
    return saddr;
  }

  public void setSaddr(String saddr) {
    this.saddr = saddr;
  }

  public String getDingdbz() {
    return dingdbz;
  }

  public void setDingdbz(String dingdbz) {
    this.dingdbz = dingdbz;
  }

  public Date getCreatetime() {
    return createtime;
  }

  public void setCreatetime(Date createtime) {
    this.createtime = createtime;
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

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getDetails() {
    return details;
  }

  public void setDetails(String details) {
    this.details = details;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getWuliu() {
    return wuliu;
  }

  public void setWuliu(String wuliu) {
    this.wuliu = wuliu;
  }

  public String getWulxx() {
    return wulxx;
  }

  public void setWulxx(String wulxx) {
    this.wulxx = wulxx;
  }

  public String getPingj() {
    return pingj;
  }

  public void setPingj(String pingj) {
    this.pingj = pingj;
  }
}

