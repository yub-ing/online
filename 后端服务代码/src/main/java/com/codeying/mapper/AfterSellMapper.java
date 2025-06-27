package com.codeying.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.codeying.entity.AfterSell;
import java.util.List;
/** 售后申请 mybatisPlus提供接口，自动实现了各种单表操作 */
public interface AfterSellMapper extends BaseMapper<AfterSell> {
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

