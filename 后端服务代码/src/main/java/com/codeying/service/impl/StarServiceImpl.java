package com.codeying.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.codeying.component.utils.CommonUtils;
import com.codeying.entity.Star;
import com.codeying.mapper.StarMapper;
import com.codeying.service.StarService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 */
@Service
public class StarServiceImpl extends AbsServiceImpl<StarMapper, Star> implements StarService {

    @Override
    public List<Star> topN(String type, int n) {
        List<Star> topN = baseMapper.topN(type);
        List<Star> resList = new ArrayList<>();
        //填充内容
        for (Star star : topN) {
            Star ori = baseMapper.getStar(type, star.getItemid());
            if (ori == null) {
                //内容已经被删除,删除所有收藏
                remove(new LambdaQueryWrapper<Star>().eq(Star::getItemid, star.getItemid()));
            } else {
                star.setShowpic(ori.getShowpic());
                star.setShowtitle(ori.getShowtitle());
                star.setShowdesc(ori.getShowdesc());
                star.setPublishtime(ori.getPublishtime());
                resList.add(star);
                if(resList.size() == n){
                    return resList;
                }
            }
        }
        return resList;
    }

    /**
     * 收藏
     */
    @Override
    public boolean star(String userid, String role, String itemId, String itemType, String op) {
        if (hasStar(userid, role, itemId, itemType, op) != null) {
            return false;
        }
        Star star = new Star();
        star.setId(CommonUtils.newId());
        star.setCreatetime(new Date());
        star.setItemid(itemId);
        star.setItemtype(itemType);
        star.setUserid(userid);
        star.setUserrole(role);
        star.setType(op);
        save(star);
        return true;
    }

    /**
     * is收藏
     */
    @Override
    public String hasStar(String userid, String role, String itemId, String itemType, String op) {
        List<Star> list = list(new LambdaQueryWrapper<Star>()
                .eq(Star::getItemid, itemId)
                .eq(Star::getUserid, userid)
                .eq(Star::getUserrole, role)
                .eq(Star::getType, op)
                .eq(Star::getItemtype, itemType)
        );
        if (list.size() > 0) {
            return list.get(0).getId();
        }
        return null;
    }

    /**
     * 我的收藏
     */
    @Override
    public List<Star> userStar(String userid, String role) {
        List<Star> list = list(new LambdaQueryWrapper<Star>()
                .eq(Star::getUserid, userid)
                .eq(Star::getUserrole, role)
                .eq(Star::getType, "收藏")
        );
        List<Star> resList = new ArrayList<>();
        //填充内容
        for (Star star : list) {
            Star res = baseMapper.getStar(star.getItemtype(), star.getItemid());
            if (res == null) {
                //内容已经被删除
                removeById(star.getId());
            } else {
                star.setShowpic(res.getShowpic());
                star.setShowtitle(res.getShowtitle());
                star.setShowdesc(res.getShowdesc());
                star.setPublishtime(res.getPublishtime());
                resList.add(star);
            }
        }
        return resList;
    }
}

