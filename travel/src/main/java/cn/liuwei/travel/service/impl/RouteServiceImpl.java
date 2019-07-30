package cn.liuwei.travel.service.impl;

import cn.liuwei.travel.dao.FavoriteDao;
import cn.liuwei.travel.dao.RouteDao;
import cn.liuwei.travel.dao.RouteImgDao;
import cn.liuwei.travel.dao.SellerDao;
import cn.liuwei.travel.dao.impl.FavoriteDaoImpl;
import cn.liuwei.travel.dao.impl.RouteDaoImpl;
import cn.liuwei.travel.dao.impl.RouteImgDaoImpl;
import cn.liuwei.travel.dao.impl.SellerDaoImpl;
import cn.liuwei.travel.domain.PageBean;
import cn.liuwei.travel.domain.Route;
import cn.liuwei.travel.domain.RouteImg;
import cn.liuwei.travel.domain.Seller;
import cn.liuwei.travel.service.RouteService;

import java.util.List;

public class RouteServiceImpl implements RouteService {
    RouteDao routeDao = new RouteDaoImpl();
    RouteImgDao routeImgDao = new RouteImgDaoImpl();
    SellerDao sellerDao = new SellerDaoImpl();
    FavoriteDao favoriteDao = new FavoriteDaoImpl();
    @Override
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize,String rname) {
        PageBean<Route> pageBean = new PageBean<Route>();
        pageBean.setCurrentPage(currentPage);
        pageBean.setPageSize(pageSize);
        int totalCount = routeDao.findTotalCount(cid,rname);
        pageBean.setTotalCount(totalCount);
        int totalPage = totalCount % pageSize == 0? totalCount/pageSize:(totalCount/pageSize + 1);
        pageBean.setTotalPage(totalPage);
        int start = (currentPage - 1) * pageSize;
        List<Route> list = (List<Route>) routeDao.findByPage(cid,start,pageSize,rname);
        pageBean.setList(list);
        return pageBean;
    }

//    @Override
//    public PageBean<Route> pageQueryByRname(int cid, int currentPage, int pageSize, String rname) {
//        PageBean<Route> pageBean = new PageBean<Route>();
//        pageBean.setCurrentPage(currentPage);
//        pageBean.setPageSize(pageSize);
//        int totalCount = routeDao.findTotalCount(cid,rname);
//        pageBean.setTotalCount(totalCount);
//        int totalPage = totalCount % pageSize == 0? totalCount/pageSize:(totalCount/pageSize + 1);
//        pageBean.setTotalPage(totalPage);
//        int start = (currentPage - 1) * pageSize;
//        List<Route> list = (List<Route>) routeDao.findByPage(cid,start,pageSize,rname);
//        pageBean.setList(list);
//        return pageBean;
//    }

    @Override
    public Route findOne(String rid) {
        //根据rid去route表中查询Route对象
        Route route = routeDao.findOne(Integer.parseInt(rid));
        List<RouteImg> list = routeImgDao.findByRid(Integer.parseInt(rid));
        route.setRouteImgList(list);
        //根据sid查询商家的信息
        Seller seller = sellerDao.findById(route.getSid());
        route.setSeller(seller);
        int count = favoriteDao.findCountByRid(route.getRid());
        route.setCount(count);
        return route;
    }
    @Override
    public void addCount(String rid) {
        routeDao.addCount(Integer.parseInt(rid));
    }
    @Override
    public void deleteCount(String rid) {
       routeDao.deleteCount(Integer.parseInt(rid));
    }

    @Override
    public List<Route> routeQuery() {
        return routeDao.routeQuery();
    }

    @Override
    public List<Route> newestQuery() {
        return routeDao.findRouteOrderByRtime();
    }

    @Override
    public List<Route> themeQuery() {
        return routeDao.findByIsThemeTour();
    }

    @Override
    public List<Route> domesticQuery() {
        return routeDao.findByPage(5,0,8,null);
    }

    @Override
    public List<Route> overseasQuery() {
        return routeDao.findByPage(4,0,8,null);
    }
    public PageBean<Route> pageQueryRank(String rname, int currentPage, int pageSize,int lastPrice,int hightestPrice) {
        PageBean<Route> pageBean = new PageBean<Route>();
        pageBean.setCurrentPage(currentPage);
        pageBean.setPageSize(pageSize);
        int totalCount = routeDao.findTotalCountByRnameAndPrice(rname,lastPrice,hightestPrice);
        pageBean.setTotalCount(totalCount);
        int totalPage = totalCount % pageSize == 0? totalCount/pageSize:(totalCount/pageSize + 1);
        pageBean.setTotalPage(totalPage);
        int start = (currentPage-1)*pageSize;
        List<Route> lists = routeDao.findRoutesRank(rname,start,pageSize,lastPrice,hightestPrice);
        pageBean.setList(lists);
        return pageBean;
    }
}
