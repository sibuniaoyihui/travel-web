package cn.liuwei.travel.dao.impl;

import cn.liuwei.travel.dao.RouteDao;
import cn.liuwei.travel.domain.Route;
import cn.liuwei.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class RouteDaoImpl implements RouteDao {
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());
   /*
   查询总记录数
    */
    @Override
    public int findTotalCount(int cid, String rname) {
        //定义sql模板
        String sql = "select count(*)from tab_route where 1=1";
        StringBuffer sb = new StringBuffer(sql);
        List params = new ArrayList();
        if (cid != 0){
            sb.append(" and cid = ? ");
            params.add(cid);
        }
        if (rname != null && rname.length()>0){
            sb.append(" and rname like ? ");
            params.add("%"+rname+"%");
        }
        sql = sb.toString();
        return  jdbcTemplate.queryForObject(sql,Integer.class,params.toArray());
    }

    /*
    分页查询
     */
    @Override
    public List<Route> findByPage(int cid, int start, int pageSize, String rname) {
        String sql = "select *from tab_route where 1=1";
        StringBuffer sb = new StringBuffer(sql);
        List params = new ArrayList();
        if (cid != 0){
            sb.append(" and cid = ? ");
            params.add(cid);
        }
        if (rname != null && rname.length()>0){
            sb.append(" and rname like ? ");
            params.add("%"+rname+"%");
        }
        sb.append(" limit ?,? ");
        sql = sb.toString();
        params.add(start);
        params.add(pageSize);
        List<Route> routes = jdbcTemplate.query(sql,new BeanPropertyRowMapper<Route>(Route.class),params.toArray());
        return routes;
    }

    @Override
    public Route findOne(int rid) {
        String sql = "select *from tab_route where rid = ?";
        return jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<Route>(Route.class),rid);

    }

    @Override
    public void addCount(int rid) {
        String sql = "update tab_route set count = ? where rid = ?";
        int count = getCount(rid)+1;
        jdbcTemplate.update(sql,count,rid);
    }

    @Override
    public void deleteCount(int rid) {
        String sql = "update tab_route set count = ? where rid = ?";
        int count = getCount(rid)-1;
        jdbcTemplate.update(sql,count,rid);
    }

    @Override
    public List<Route> routeQuery() {
        String sql = "select *from tab_route order by count desc limit ?,?";
        List<Route> routes = jdbcTemplate.query(sql,new BeanPropertyRowMapper<Route>(Route.class),0,4);
        return routes;
    }

    @Override
    public List<Route> findRouteOrderByRtime() {
        String sql = "select *from tab_route order by rdate desc limit ?,?";
        List<Route> routes = jdbcTemplate.query(sql,new BeanPropertyRowMapper<Route>(Route.class),0,4);
        return routes;
    }

    @Override
    public List<Route> findByIsThemeTour() {
        String sql = "select *from tab_route where isThemeTour = ? order by rdate desc limit ?,?";
        List<Route> routes = jdbcTemplate.query(sql,new BeanPropertyRowMapper<Route>(Route.class),1,0,4);
        return routes;
    }

    @Override
    public int findTotalCountByRnameAndPrice(String rname,int lastPrice,int hightestPrice) {
        String sql = "select count(*)from tab_route where 1 = 1";
        StringBuffer sb = new StringBuffer(sql);
        List params = new ArrayList();
        if (rname != null && rname.length()>0){
            sb.append(" and rname like ?");
            params.add("%"+rname+"%");
        }
        if (lastPrice >= 0 && hightestPrice >=0 && lastPrice<hightestPrice){
            sb.append(" and price between ? and ? ");
            params.add(lastPrice);
            params.add(hightestPrice);
        }

        sql = sb.toString();
        int totalCount = jdbcTemplate.queryForObject(sql,Integer.class,params.toArray());
        return totalCount;
    }

    @Override
    public List<Route> findRoutesRank(String rname, int start,int pageSize,int lastPrice, int hightestPrice) {
        String sql = "select *from tab_route where 1 = 1";
        StringBuffer sb = new StringBuffer(sql);
        List params = new ArrayList();
        if (rname != null && rname.length()>0){
            sb.append(" and rname like ?");
            params.add("%"+rname+"%");
        }
        if (lastPrice >= 0 && hightestPrice > 0 && lastPrice <= hightestPrice){
            sb.append(" and price between ? and ? ");
            params.add(lastPrice);
            params.add(hightestPrice);
        }
        sb.append(" order by count desc ");
        sb.append(" limit ?,? ");
        params.add(start);
        params.add(pageSize);
        sql = sb.toString();
        List<Route> routes = jdbcTemplate.query(sql,new BeanPropertyRowMapper<Route>(Route.class),params.toArray());
        return routes;
    }
//    //获取拼接sql
//    public String getSql(String rname,int lastPrice,int hightestPrice){
//        String sql = "select *from tab_route where 1 = 1";
//        StringBuffer sb = new StringBuffer(sql);
//        List params = new ArrayList();
//        if (rname != null && rname.length()>0){
//            sb.append(" and rname like ?");
//            params.add("%"+rname+"%");
//        }
//        if (lastPrice > 0 && hightestPrice >0 && lastPrice<hightestPrice){
//            sb.append(" and price between ? and ? ");
//            params.add(lastPrice);
//            params.add(hightestPrice);
//        }
//        params.add(" order by count desc ");
//        sql = sb.toString();
//        return sql;
//    }

    //获取线路收藏数
    public int getCount(int rid){
        String sql= "select *from tab_route where rid = ?";
        Route route = jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<Route>(Route.class),rid);
        return route.getCount();
    }
}
