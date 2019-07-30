package cn.liuwei.travel.web.servlet;

import cn.liuwei.travel.dao.FavoriteDao;
import cn.liuwei.travel.domain.PageBean;
import cn.liuwei.travel.domain.Route;
import cn.liuwei.travel.domain.User;
import cn.liuwei.travel.service.FavoriteService;
import cn.liuwei.travel.service.RouteService;
import cn.liuwei.travel.service.impl.FavoriteServiceImpl;
import cn.liuwei.travel.service.impl.RouteServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {
    private RouteService routeService = new RouteServiceImpl();
    private FavoriteService favoriteService = new FavoriteServiceImpl();

    /*
     分页查询
    */
    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //接受参数
        String currentPageStr = request.getParameter("currentPage");
//        System.out.println(currentPageStr);
        String pageSizeStr = request.getParameter("pageSize");
        String cidStr = request.getParameter("cid");
        String rname = request.getParameter("rname");
        if (rname != null && rname.length() > 0 && !"null".equals(rname)) {
            rname = new String(rname.getBytes("iso-8859-1"), "utf-8");//解决乱码问题 tomcat7（包含）之前都要解决乱码问题
        } else {
            rname = null;
        }
//        System.out.println("rname:" + rname);
        int cid = 0;
        //处理参数
        if (cidStr != null && cidStr.length() > 0 && !"null".equals(cidStr)) {
            cid = Integer.parseInt(cidStr);
        }
        int currentPage = 0;
        if (currentPageStr != null && currentPageStr.length() > 0 && !"null".equals(currentPageStr)) {
            currentPage = Integer.parseInt(currentPageStr);
//            System.out.println(currentPage);
        } else {
            currentPage = 1;
        }
        int pageSize = 0;
        if (pageSizeStr != null && pageSizeStr.length() > 0) {
            pageSize = Integer.parseInt(pageSizeStr);
        } else {
            pageSize = 5;
        }
        PageBean<Route> pageBean = routeService.pageQuery(cid, currentPage, pageSize, rname);
//        if (rname == null || rname == "") {
//            pageBean = routeService.pageQuery(cid, currentPage, pageSize);
//        }else {
//            pageBean = routeService.pageQueryByRname(cid,currentPage, pageSize,rname);
//        }
        writeValue(pageBean, response);
    }

    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //接受参数rid
        String rid = request.getParameter("rid");
        //调用Service查询Route对象
        Route route = routeService.findOne(rid);
        //转化为Json写会客户端
        writeValue(route, response);
    }

    /*
    判断当前用户是否收藏该线路
    */
    public void isFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取路线rid
        String rid = request.getParameter("rid");
        //获取当前用户
        User user = (User) request.getSession().getAttribute("user");
        int uid;
        if (user == null) {
            //用户尚未登录
            uid = 0;
        } else {
            //用户已经登录
            uid = user.getUid();
        }
        //调用FavoriteService
        boolean flag = favoriteService.isFavorite(rid, uid);
        //判断flag的值，并在数据库的route表中改变count的值
        writeValue(flag, response);
    }

    public void changeFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取路线rid
        String rid = request.getParameter("rid");
        //获取当前用户
        User user = (User) request.getSession().getAttribute("user");
        int uid;
        if (user == null) {
            //用户尚未登录
            return;
        } else {
            //用户已经登录
            uid = user.getUid();
        }
        boolean flag = favoriteService.isFavorite(rid, uid);
        if (!flag) {
            favoriteService.add(rid, uid);
            routeService.addCount(rid);
        } else {
            favoriteService.delete(rid, uid);
            routeService.deleteCount(rid);
        }
    }

    public void myFavoriteQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uid = request.getParameter("uid");
        String currentPageStr = request.getParameter("currentPage");
        String pageSizeStr = request.getParameter("pageSize");
        int currentPage = 0;
        if (currentPageStr != null && currentPageStr.length() > 0 && !"null".equals(currentPageStr)) {
            currentPage = Integer.parseInt(currentPageStr);
        } else {
            currentPage = 1;
        }
        int pageSize = 0;
        if (pageSizeStr != null && pageSizeStr.length() > 0) {
            pageSize = Integer.parseInt(pageSizeStr);
        } else {
            pageSize = 12;
        }
        PageBean<Route> routes = favoriteService.myFavoriteRoute(uid, currentPage, pageSize);
        writeValue(routes, response);
    }

    public void popularQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Route> routes = routeService.routeQuery();
        writeValue(routes, response);
    }

    public void newestQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Route> routes = routeService.newestQuery();
        writeValue(routes, response);
    }

    public void themeQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Route> routes = routeService.themeQuery();
        writeValue(routes, response);
    }

    public void domesticQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Route> routes = routeService.domesticQuery();
        writeValue(routes, response);
    }

    public void overseasQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Route> routes = routeService.overseasQuery();
        writeValue(routes, response);
    }
    public void pageQueryRank(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rname = request.getParameter("rname");
        String currentPageStr = request.getParameter("currentPage");
//        System.out.println(currentPageStr);
        String pageSizeStr = request.getParameter("pageSize");
        String lastPriceStr = request.getParameter("lastPrice");
        String heightestPriceStr = request.getParameter("heightestPrice");
        if (rname != null && rname.length() > 0 && !"null".equals(rname)) {
            rname = new String(rname.getBytes("iso-8859-1"), "utf-8");//解决乱码问题 tomcat7（包含）之前都要解决乱码问题
        } else {
            rname = null;
        }
        int currentPage = 0;
        if (currentPageStr != null && currentPageStr.length()>0){
            currentPage = Integer.parseInt(currentPageStr);
//            System.out.println(currentPage);
        } else {
            currentPage = 1;
        }
        int pageSize = 0;
        if (pageSizeStr != null && pageSizeStr.length() > 0) {
            pageSize = Integer.parseInt(pageSizeStr);
        } else {
            pageSize = 8;
        }
        int lastPrice = 0;
        if (lastPriceStr != null && lastPriceStr.length()>0 && !"null".equals(lastPriceStr)){
            lastPrice = Integer.parseInt(lastPriceStr);
        }
        int heightestPrice = 0;
        if (heightestPriceStr != null && heightestPriceStr.length()>0&& !"null".equals(heightestPriceStr)){
            heightestPrice = Integer.parseInt(heightestPriceStr);
        }
        PageBean<Route> pageBean = routeService.pageQueryRank(rname,currentPage,pageSize,lastPrice,heightestPrice);
        writeValue(pageBean,response);
    }
}
