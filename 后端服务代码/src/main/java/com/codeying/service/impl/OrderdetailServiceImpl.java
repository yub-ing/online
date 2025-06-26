package com.codeying.service.impl;

import com.codeying.mapper.OrderdetailMapper;
import com.codeying.entity.Orderdetail;
import com.codeying.service.OrderdetailService;
import org.springframework.stereotype.Service;

import java.util.List;
/** 服务实现类 */
@Service
public class OrderdetailServiceImpl extends AbsServiceImpl<OrderdetailMapper, Orderdetail>
    implements OrderdetailService {

  @Override
  public List<Orderdetail> sqlSelectList(Orderdetail qo) {
    return baseMapper.sqlSelectList(qo);
  }

  @Override
  public int sqlDeleteById(String id) {
    return baseMapper.sqlDeleteById(id);
  }

  @Override
  public int sqlUpdate(Orderdetail e) {
    return baseMapper.sqlUpdate(e);
  }

  @Override
  public int sqlSave(Orderdetail e) {
    return baseMapper.sqlSave(e);
  }
}

