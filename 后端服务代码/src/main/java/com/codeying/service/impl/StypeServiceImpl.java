package com.codeying.service.impl;

import com.codeying.mapper.StypeMapper;
import com.codeying.entity.Stype;
import com.codeying.service.StypeService;
import org.springframework.stereotype.Service;

import java.util.List;
/** 服务实现类 */
@Service
public class StypeServiceImpl extends AbsServiceImpl<StypeMapper, Stype> implements StypeService {

  @Override
  public List<Stype> sqlSelectList(Stype qo) {
    return baseMapper.sqlSelectList(qo);
  }

  @Override
  public int sqlDeleteById(String id) {
    return baseMapper.sqlDeleteById(id);
  }

  @Override
  public int sqlUpdate(Stype e) {
    return baseMapper.sqlUpdate(e);
  }

  @Override
  public int sqlSave(Stype e) {
    return baseMapper.sqlSave(e);
  }
}

