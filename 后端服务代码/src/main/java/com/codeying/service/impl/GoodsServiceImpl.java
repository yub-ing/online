package com.codeying.service.impl;

import com.codeying.mapper.GoodsMapper;
import com.codeying.entity.Goods;
import com.codeying.service.GoodsService;
import org.springframework.stereotype.Service;

import java.util.List;
/** 服务实现类 */
@Service
public class GoodsServiceImpl extends AbsServiceImpl<GoodsMapper, Goods> implements GoodsService {

  @Override
  public List<Goods> sqlSelectList(Goods qo) {
    return baseMapper.sqlSelectList(qo);
  }

  @Override
  public int sqlDeleteById(String id) {
    return baseMapper.sqlDeleteById(id);
  }

  @Override
  public int sqlUpdate(Goods e) {
    return baseMapper.sqlUpdate(e);
  }

  @Override
  public int sqlSave(Goods e) {
    return baseMapper.sqlSave(e);
  }
}

