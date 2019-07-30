package cn.liuwei.travel.dao;

import cn.liuwei.travel.domain.Favorite;
import cn.liuwei.travel.domain.Route;

import java.util.List;

public interface FavoriteDao {
    //获取收藏对象
    public Favorite findByRidAndUid(int rid, int uid);
    //获取收藏数
    public int findCountByRid(int rid);
    //增加收藏
    public void add(int rid,int uid);
    //取消收藏
    public void delete(int rid, int uid);
    //查询收藏的总记录数
    public int findTotalCountByUid(int uid);

    public List<Route> findByPage(int uid,int start, int pageSize);
}
