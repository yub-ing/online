package com.codeying.service.impl;

import com.codeying.mapper.AfterSellMapper;
import com.codeying.entity.AfterSell;
import com.codeying.service.AfterSellService;
import org.springframework.stereotype.Service;

import java.util.List;
/** 服务实现类 */
@Service
public class AfterSellServiceImpl extends AbsServiceImpl<AfterSellMapper, AfterSell>
    implements AfterSellService {

  @Override
  public List<AfterSell> sqlSelectList(AfterSell qo) {
    return baseMapper.sqlSelectList(qo);
  }

  @Override
  public int sqlDeleteById(String id) {
    return baseMapper.sqlDeleteById(id);
  }

  @Override
  public int sqlUpdate(AfterSell e) {
    return baseMapper.sqlUpdate(e);
  }

  @Override
  public int sqlSave(AfterSell e) {
    return baseMapper.sqlSave(e);
  }
}

