package cn.liuwei.travel.service;

import cn.liuwei.travel.domain.PageBean;
import cn.liuwei.travel.domain.Route;

public interface FavoriteService {
    //判断是否收藏
    public boolean isFavorite(String rid,int uid);

    public void add(String rid, int uid);

    public void delete(String rid, int uid);

    public PageBean<Route> myFavoriteRoute(String uid,int currentPage,int pageSize);
}
