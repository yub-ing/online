package com.codeying.service;

import com.codeying.entity.AfterSell;
import java.util.List;
/** 服务类 */
public interface AfterSellService extends MyService<AfterSell> {
  /**
   * 查询
   *
   * @param qo
   * @return
   */
  List<AfterSell> sqlSelectList(AfterSell qo);

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
  int sqlUpdate(AfterSell e);

  /**
   * 保存
   *
   * @param e
   * @return
   */
  int sqlSave(AfterSell e);
}

