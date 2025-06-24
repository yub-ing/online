package com.codeying.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.codeying.entity.Star;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 *   管理员
 *   mybatisPlus提供接口，自动实现了各种单表操作
 */
public interface StarMapper extends BaseMapper<Star> {

    @Select("select itemid,count(1) count from tb_star where itemtype=#{type} and type='收藏' group by itemid order by count desc")
    List<Star> topN(@Param("type") String type);

    @Select("select showtitle,showpic,showdesc,publishtime from tb_${type} where id=#{itemId}")
    Star getStar(@Param("type") String type,@Param("itemId") String itemId);

}
