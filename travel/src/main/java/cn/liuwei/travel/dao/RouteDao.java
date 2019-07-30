package cn.liuwei.travel.dao;

import cn.liuwei.travel.domain.PageBean;
import cn.liuwei.travel.domain.Route;

import java.util.List;

public interface RouteDao {
    /*
    查询总记录数
     */
    public int findTotalCount(int cid,String rname);
    /*
       根据cid，start，pageSize查询当前数据集合
    */
    public List<Route> findByPage(int cid, int start, int pageSize,String rname);


    public  Route findOne(int rid);

    //增加收藏数
    public void addCount(int rid);
    //减少收藏数
    public void deleteCount(int rid);
    //查询人气旅游，按照收藏数查询
    public List<Route> routeQuery();
    //查询路线按时间排序
    public List<Route>findRouteOrderByRtime();
    //主题旅游
    public List<Route> findByIsThemeTour();
    //查询记录数按收藏数排序
    public int findTotalCountByRnameAndPrice(String rname,int lastPrice,int hightestPrice);
    //查询线路排行
    public List<Route> findRoutesRank(String rname,int start,int pageSize,int lastPrice,int hightestPrice);
}
