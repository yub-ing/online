package com.codeying.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.codeying.entity.Stype;
import java.util.List;
/** 商品类型 mybatisPlus提供接口，自动实现了各种单表操作 */
public interface StypeMapper extends BaseMapper<Stype> {
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

