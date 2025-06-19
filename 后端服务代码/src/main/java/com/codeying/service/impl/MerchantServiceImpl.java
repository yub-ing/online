package com.codeying.service.impl;

import com.codeying.mapper.MerchantMapper;
import com.codeying.entity.Merchant;
import com.codeying.service.MerchantService;
import org.springframework.stereotype.Service;

import java.util.List;
/** 服务实现类 */
@Service
public class MerchantServiceImpl extends AbsServiceImpl<MerchantMapper, Merchant>
    implements MerchantService {

  @Override
  public List<Merchant> sqlSelectList(Merchant qo) {
    return baseMapper.sqlSelectList(qo);
  }

  @Override
  public int sqlDeleteById(String id) {
    return baseMapper.sqlDeleteById(id);
  }

  @Override
  public int sqlUpdate(Merchant e) {
    return baseMapper.sqlUpdate(e);
  }

  @Override
  public int sqlSave(Merchant e) {
    return baseMapper.sqlSave(e);
  }
}

