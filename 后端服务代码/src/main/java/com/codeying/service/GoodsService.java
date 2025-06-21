package com.codeying.service;

import com.codeying.entity.Goods;
import java.util.List;
/** 服务类 */
public interface GoodsService extends MyService<Goods> {
  /**
   * 查询
   *
   * @param qo
   * @return
   */
  List<Goods> sqlSelectList(Goods qo);

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
  int sqlUpdate(Goods e);

  /**
   * 保存
   *
   * @param e
   * @return
   */
  int sqlSave(Goods e);
}

