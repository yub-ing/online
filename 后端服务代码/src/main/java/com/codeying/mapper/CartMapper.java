package com.codeying.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.codeying.entity.Cart;
import java.util.List;
/** 购物车 mybatisPlus提供接口，自动实现了各种单表操作 */
public interface CartMapper extends BaseMapper<Cart> {
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

