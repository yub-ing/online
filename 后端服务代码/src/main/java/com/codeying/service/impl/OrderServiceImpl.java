package com.codeying.service.impl;

import com.codeying.mapper.OrderMapper;
import com.codeying.entity.Order;
import com.codeying.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;
/** 服务实现类 */
@Service
public class OrderServiceImpl extends AbsServiceImpl<OrderMapper, Order> implements OrderService {

  @Override
  public List<Order> sqlSelectList(Order qo) {
    return baseMapper.sqlSelectList(qo);
  }

  @Override
  public int sqlDeleteById(String id) {
    return baseMapper.sqlDeleteById(id);
  }

  @Override
  public int sqlUpdate(Order e) {
    return baseMapper.sqlUpdate(e);
  }

  @Override
  public int sqlSave(Order e) {
    return baseMapper.sqlSave(e);
  }
}

