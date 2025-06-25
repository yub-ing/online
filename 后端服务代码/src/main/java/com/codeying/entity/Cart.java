package com.codeying.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import java.util.List;

import java.io.Serializable;
/** 购物车实体类 */
@TableName("tb_cart")
public class Cart implements Serializable {

  /** 主键 */
  @TableId private String id;

  /** 用户 */
  @TableField("userid")
  private String userid;

  @TableField(exist = false)
  private User useridFrn;
  /** 商品 */
  @TableField("goodsid")
  private String goodsid;

  @TableField(exist = false)
  private Goods goodsidFrn;
  /** 商品图片 */
  @TableField("tustp")
  private String tustp;

  /** 价格 */
  @TableField("price")
  private Double price;

  /** 数量 */
  @TableField("count")
  private Integer count;

  /** 总价 */
  @TableField("zongj")
  private Double zongj;

  /** 加购时间 */
  @TableField("createtime")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date createtime;

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

  public String getGoodsid() {
    return goodsid;
  }

  public void setGoodsid(String goodsid) {
    this.goodsid = goodsid;
  }

  public Goods getGoodsidFrn() {
    return goodsidFrn;
  }

  public void setGoodsidFrn(Goods v) {
    this.goodsidFrn = v;
  }

  public String getTustp() {
    return tustp;
  }

  public void setTustp(String tustp) {
    this.tustp = tustp;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public Integer getCount() {
    return count;
  }

  public void setCount(Integer count) {
    this.count = count;
  }

  public Double getZongj() {
    return zongj;
  }

  public void setZongj(Double zongj) {
    this.zongj = zongj;
  }

  public Date getCreatetime() {
    return createtime;
  }

  public void setCreatetime(Date createtime) {
    this.createtime = createtime;
  }
}

