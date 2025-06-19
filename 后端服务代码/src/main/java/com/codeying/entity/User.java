package com.codeying.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import java.util.List;

import java.io.Serializable;
/** 用户实体类 */
@TableName("tb_user")
public class User extends LoginUser implements Serializable {

  /** 主键 */
  @TableId private String id;

  /** 用户名 */
  @TableField("username")
  private String username;

  /** 密码 */
  @TableField("password")
  private String password;

  /** 姓名 */
  @TableField("name")
  private String name;

  /** 头像 */
  @TableField("avatar")
  private String avatar;

  /** 性别 */
  @TableField("gender")
  private String gender;

  /** 年龄 */
  @TableField("age")
  private Integer age;

  /** 电话 */
  @TableField("tele")
  private String tele;

  /** 邮箱 */
  @TableField("youx")
  private String youx;

  /** 地址 */
  @TableField("place")
  private String place;

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

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  public String getTele() {
    return tele;
  }

  public void setTele(String tele) {
    this.tele = tele;
  }

  public String getYoux() {
    return youx;
  }

  public void setYoux(String youx) {
    this.youx = youx;
  }

  public String getPlace() {
    return place;
  }

  public void setPlace(String place) {
    this.place = place;
  }

  /** 角色 */
  @TableField(exist = false)
  private String role = "user";

  @TableField(exist = false)
  private String rolech = "用户";

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

