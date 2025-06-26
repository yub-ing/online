package com.codeying.service;

import com.codeying.entity.Orderdetail;
import java.util.List;
/** 服务类 */
public interface OrderdetailService extends MyService<Orderdetail> {
  /**
   * 查询
   *
   * @param qo
   * @return
   */
  List<Orderdetail> sqlSelectList(Orderdetail qo);

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
  int sqlUpdate(Orderdetail e);

  /**
   * 保存
   *
   * @param e
   * @return
   */
  int sqlSave(Orderdetail e);
}

