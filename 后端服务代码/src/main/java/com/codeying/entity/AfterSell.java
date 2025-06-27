package com.codeying.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import java.util.List;

import java.io.Serializable;
/** 售后申请实体类 */
@TableName("tb_afterSell")
public class AfterSell implements Serializable {

  /** 主键 */
  @TableId private String id;

  /** 用户 */
  @TableField("userid")
  private String userid;

  @TableField(exist = false)
  private User useridFrn;
  /** 订单号 */
  @TableField("oid")
  private String oid;

  @TableField(exist = false)
  private Order oidFrn;
  /** 售后类型 */
  @TableField("typer")
  private String typer;

  /** 要求售后内容 */
  @TableField("content")
  private String content;

  /** 产品问题拍照上传 */
  @TableField("pic")
  private String pic;

  /** 售后申请时间 */
  @TableField("createtime")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date createtime;

  /** 处理状态 */
  @TableField("state")
  private String state;

  /** 处理结果 */
  @TableField("chuljg")
  private String chuljg;

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

  public String getOid() {
    return oid;
  }

  public void setOid(String oid) {
    this.oid = oid;
  }

  public Order getOidFrn() {
    return oidFrn;
  }

  public void setOidFrn(Order v) {
    this.oidFrn = v;
  }

  public String getTyper() {
    return typer;
  }

  public void setTyper(String typer) {
    this.typer = typer;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getPic() {
    return pic;
  }

  public void setPic(String pic) {
    this.pic = pic;
  }

  public Date getCreatetime() {
    return createtime;
  }

  public void setCreatetime(Date createtime) {
    this.createtime = createtime;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getChuljg() {
    return chuljg;
  }

  public void setChuljg(String chuljg) {
    this.chuljg = chuljg;
  }
}

