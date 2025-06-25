package com.codeying.service.impl;

import com.codeying.mapper.CartMapper;
import com.codeying.entity.Cart;
import com.codeying.service.CartService;
import org.springframework.stereotype.Service;

import java.util.List;
/** 服务实现类 */
@Service
public class CartServiceImpl extends AbsServiceImpl<CartMapper, Cart> implements CartService {

  @Override
  public List<Cart> sqlSelectList(Cart qo) {
    return baseMapper.sqlSelectList(qo);
  }

  @Override
  public int sqlDeleteById(String id) {
    return baseMapper.sqlDeleteById(id);
  }

  @Override
  public int sqlUpdate(Cart e) {
    return baseMapper.sqlUpdate(e);
  }

  @Override
  public int sqlSave(Cart e) {
    return baseMapper.sqlSave(e);
  }
}

