package com.codeying.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.codeying.entity.Goods;
import java.util.List;
/** 商品信息 mybatisPlus提供接口，自动实现了各种单表操作 */
public interface GoodsMapper extends BaseMapper<Goods> {
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

