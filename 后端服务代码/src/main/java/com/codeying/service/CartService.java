package com.codeying.service;

import com.codeying.entity.Cart;
import java.util.List;
/** 服务类 */
public interface CartService extends MyService<Cart> {
  /**
   * 查询
   *
   * @param qo
   * @return
   */
  List<Cart> sqlSelectList(Cart qo);

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
  int sqlUpdate(Cart e);

  /**
   * 保存
   *
   * @param e
   * @return
   */
  int sqlSave(Cart e);
}

