package cn.liuwei.travel.service;

import cn.liuwei.travel.domain.PageBean;
import cn.liuwei.travel.domain.Route;

import java.util.List;

public interface RouteService {
    public PageBean<Route> pageQuery(int cid,int currentPage,int pageSize,String rname);
//    public PageBean<Route> pageQueryByRname(int cid,int currentPage,int pageSize,String rname);//该方法不用
    /*
    根据rid查询
     */
    Route findOne(String rid);
    /*
    增加收藏数
     */
    public void addCount(String rid);
    /*
    减少收藏数
     */
    public void deleteCount(String rid);
    //查询人气推荐
    public List<Route> routeQuery();
    //查询最新路线
    public List<Route> newestQuery();
    //主题旅游
    public List<Route> themeQuery();
    //国内游查询
    public List<Route> domesticQuery();
    //进外游查询
    public List<Route> overseasQuery();
    //查询rank排名
    public PageBean<Route> pageQueryRank(String rname, int currentPage, int pageSize,int lastPrice,int hightestPrice);
}
