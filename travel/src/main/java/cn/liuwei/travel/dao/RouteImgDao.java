package cn.liuwei.travel.dao;

import cn.liuwei.travel.domain.RouteImg;

import java.util.List;

public interface RouteImgDao {
    public List<RouteImg> findByRid(int rid);
}
