package com.codeying.service;

import com.codeying.entity.Stype;
import java.util.List;
/** 服务类 */
public interface StypeService extends MyService<Stype> {
  /**
   * 查询
   *
   * @param qo
   * @return
   */
  List<Stype> sqlSelectList(Stype qo);

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
  int sqlUpdate(Stype e);

  /**
   * 保存
   *
   * @param e
   * @return
   */
  int sqlSave(Stype e);
}

