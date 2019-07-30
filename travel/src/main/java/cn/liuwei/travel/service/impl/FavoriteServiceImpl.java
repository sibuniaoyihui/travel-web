package cn.liuwei.travel.service.impl;

import cn.liuwei.travel.dao.FavoriteDao;
import cn.liuwei.travel.dao.RouteImgDao;
import cn.liuwei.travel.dao.impl.FavoriteDaoImpl;
import cn.liuwei.travel.dao.impl.RouteImgDaoImpl;
import cn.liuwei.travel.domain.Favorite;
import cn.liuwei.travel.domain.PageBean;
import cn.liuwei.travel.domain.Route;
import cn.liuwei.travel.domain.RouteImg;
import cn.liuwei.travel.service.FavoriteService;

import java.util.ArrayList;
import java.util.List;

public class FavoriteServiceImpl implements FavoriteService {
    FavoriteDao favoriteDao = new FavoriteDaoImpl();
    RouteImgDao routeImgDao = new RouteImgDaoImpl();
    @Override
    public boolean isFavorite(String rid, int uid) {
       Favorite favorite = favoriteDao.findByRidAndUid(Integer.parseInt(rid),uid);
       if (favorite == null){
           return false;
       }
       return true;
    }

    @Override
    public void add(String rid, int uid) {
        favoriteDao.add(Integer.parseInt(rid),uid);
    }

    @Override
    public void delete(String rid, int uid) {
        favoriteDao.delete(Integer.parseInt(rid),uid);
    }

    @Override
    public PageBean<Route> myFavoriteRoute(String uid,int currentPage,int pageSize) {
        PageBean<Route> pageBean = new PageBean<Route>();
        int totalCount = favoriteDao.findTotalCountByUid(Integer.parseInt(uid));
        pageBean.setTotalCount(totalCount);
        pageBean.setPageSize(pageSize);
        pageBean.setCurrentPage(currentPage);
        int totalPage = totalCount % pageSize == 0 ? totalCount/pageSize:(totalCount/pageSize+1);
        pageBean.setTotalPage(totalPage);
        int start = (currentPage - 1) * pageSize;
        List<Route> list = (List<Route>) favoriteDao.findByPage(Integer.parseInt(uid),start,pageSize);
        List<Route> routes = new ArrayList<Route>();
        for (Route route : list){
            List<RouteImg> imgs = routeImgDao.findByRid(route.getRid());
            route.setRouteImgList(imgs);
            routes.add(route);
        }
        pageBean.setList(routes);
        return pageBean;
    }

}
