package com.codeying.service;

import com.codeying.entity.Merchant;
import java.util.List;
/** 服务类 */
public interface MerchantService extends MyService<Merchant> {
  /**
   * 查询
   *
   * @param qo
   * @return
   */
  List<Merchant> sqlSelectList(Merchant qo);

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
  int sqlUpdate(Merchant e);

  /**
   * 保存
   *
   * @param e
   * @return
   */
  int sqlSave(Merchant e);
}

