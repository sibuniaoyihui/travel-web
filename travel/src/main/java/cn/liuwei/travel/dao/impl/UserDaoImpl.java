package cn.liuwei.travel.dao.impl;

import cn.liuwei.travel.dao.UserDao;
import cn.liuwei.travel.domain.User;
import cn.liuwei.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDaoImpl implements UserDao {

    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public User findByUsername(String username) {
        User user = null;
        try {
            String sql = "select *from tab_user where username = ?";
            user = jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<User>(User.class),username);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public void save(User user) {
        String sql = "insert into tab_user(username,password,name,birthday,sex,telephone,email,status,code)" +
                "     values(?,?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql,user.getUsername(),
                                user.getPassword(),
                                user.getName(),
                                user.getBirthday(),
                                user.getSex(),
                                user.getTelephone(),
                                user.getEmail(),
                                user.getStatus(),
                                user.getCode());
    }

    @Override
    public User findByUserCode(String code) {
        User user = null;
        try{
            String sql = "select *from tab_user where code = ?";
            user = jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<User>(User.class),code);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return user;
    }
    @Override
    public void updateStatus(User user) {
        try{
            String sql = "update tab_user set status = 'Y' where uid = ?";
            jdbcTemplate.update(sql, user.getUid());
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        String sql = "select *from tab_user where username = ? and password = ?";
        User user = null;
        try{
            user = jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<User>(User.class),username,password);
        }catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }
}
