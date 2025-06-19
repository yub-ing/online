package com.codeying.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import java.util.List;

import java.io.Serializable;
/** 商家实体类 */
@TableName("tb_merchant")
public class Merchant extends LoginUser implements Serializable {

  /** 主键 */
  @TableId private String id;

  /** 用户名 */
  @TableField("username")
  private String username;

  /** 密码 */
  @TableField("password")
  private String password;

  /** 商家名 */
  @TableField("name")
  private String name;

  /** 头像 */
  @TableField("avatar")
  private String avatar;

  /** 电话 */
  @TableField("tele")
  private String tele;

  /** 小店简介 */
  @TableField("xiaodjj")
  private String xiaodjj;

  /** 主营内容 */
  @TableField("zhuynr")
  private String zhuynr;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAvatar() {
    return avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }

  public String getTele() {
    return tele;
  }

  public void setTele(String tele) {
    this.tele = tele;
  }

  public String getXiaodjj() {
    return xiaodjj;
  }

  public void setXiaodjj(String xiaodjj) {
    this.xiaodjj = xiaodjj;
  }

  public String getZhuynr() {
    return zhuynr;
  }

  public void setZhuynr(String zhuynr) {
    this.zhuynr = zhuynr;
  }

  /** 角色 */
  @TableField(exist = false)
  private String role = "merchant";

  @TableField(exist = false)
  private String rolech = "商家";

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public String getRolech() {
    return rolech;
  }

  public void setRolech(String rolech) {
    this.rolech = rolech;
  }
}

