package com.codeying.service;

import com.codeying.entity.Order;
import java.util.List;
/** 服务类 */
public interface OrderService extends MyService<Order> {
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

