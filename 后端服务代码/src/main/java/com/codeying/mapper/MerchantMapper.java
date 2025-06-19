package com.codeying.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.codeying.entity.Merchant;
import java.util.List;
/** 商家 mybatisPlus提供接口，自动实现了各种单表操作 */
public interface MerchantMapper extends BaseMapper<Merchant> {
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

