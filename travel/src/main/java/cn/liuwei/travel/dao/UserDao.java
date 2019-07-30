package cn.liuwei.travel.dao;

import cn.liuwei.travel.domain.Category;
import cn.liuwei.travel.domain.User;

import java.util.List;

public interface UserDao {
   /*
   查询用户名是否存在
    */
   public User findByUsername(String username);
   /*
   保存用户进数据库
    */
   public void save(User user);
   /*
   根据激活码查询用户
    */
   public User findByUserCode(String code);
   /*
   更新状态值
    */
   public void updateStatus(User user);
   /*
   根据用户名和密码查询
    */
   public User findByUsernameAndPassword(String username,String password);

}
