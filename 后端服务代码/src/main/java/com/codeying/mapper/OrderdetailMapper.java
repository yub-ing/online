package com.codeying.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.codeying.entity.Orderdetail;
import java.util.List;
/** 订单详情 mybatisPlus提供接口，自动实现了各种单表操作 */
public interface OrderdetailMapper extends BaseMapper<Orderdetail> {
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

