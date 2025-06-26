package com.codeying.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.codeying.entity.Order;
import java.util.List;
/** 用户订单 mybatisPlus提供接口，自动实现了各种单表操作 */
public interface OrderMapper extends BaseMapper<Order> {
  /**
   * 查询
   *
   * @param qo
   * @return
   */
  List<Order> sqlSelectList(Order qo);

  /**
   * 删掉
   *
   * @param id
   * @return
   */
  int sqlDeleteById(String id);

  /**
   * 更新
   *
   * @param e
   * @return
   */
  int sqlUpdate(Order e);

  /**
   * 保存
   *
   * @param e
   * @return
   */
  int sqlSave(Order e);
}

