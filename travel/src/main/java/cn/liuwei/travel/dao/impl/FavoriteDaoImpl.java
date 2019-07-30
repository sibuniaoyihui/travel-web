package cn.liuwei.travel.dao.impl;

import cn.liuwei.travel.dao.FavoriteDao;
import cn.liuwei.travel.domain.Favorite;
import cn.liuwei.travel.domain.Route;
import cn.liuwei.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FavoriteDaoImpl implements FavoriteDao {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public Favorite findByRidAndUid(int rid, int uid) {
        String sql = "select *from tab_favorite where rid = ? and uid = ?";
        Favorite favorite = null;
        try{
            favorite =  jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<Favorite>(Favorite.class),rid,uid);
        }catch (Exception e){
            e.printStackTrace();
        }
       return  favorite;
    }

    @Override
    public int findCountByRid(int rid) {
        String sql = "select count(*)from tab_favorite where rid = ?";
        return jdbcTemplate.queryForObject(sql,Integer.class,rid);

    }
    @Override
    public void add(int rid, int uid) {
        String sql = "insert into tab_favorite(rid,date,uid)values(?,?,?)";
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String ds = simpleDateFormat.format(date);
        jdbcTemplate.update(sql,rid,ds,uid);
    }

    @Override
    public void delete(int rid, int uid) {
        String sql = "delete from tab_favorite where rid = ? and uid = ?";
        jdbcTemplate.update(sql,rid,uid);
    }

    @Override
    public int findTotalCountByUid(int uid) {
        String sql = "select count(*)from tab_favorite where uid = ?";
        return jdbcTemplate.queryForObject(sql,Integer.class,uid);
    }

    @Override
    public List<Route> findByPage(int uid,int start,int pageSize) {
        String sql = "select *from tab_route,tab_favorite where tab_route.rid = tab_favorite.rid and uid = ? limit ?,?";
        List<Route> routes = jdbcTemplate.query(sql,new BeanPropertyRowMapper<Route>(Route.class),uid,start,pageSize);
        return routes;
    }
}
