package com.codeying.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import java.util.List;

import java.io.Serializable;
/** 商品类型实体类 */
@TableName("tb_stype")
public class Stype implements Serializable {

  /** 主键 */
  @TableId private String id;

  /** 类型名 */
  @TableField("name")
  private String name;

  /** 说明 */
  @TableField("describtion")
  private String describtion;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescribtion() {
    return describtion;
  }

  public void setDescribtion(String describtion) {
    this.describtion = describtion;
  }
}

