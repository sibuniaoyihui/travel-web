package cn.liuwei.travel.service.impl;

import cn.liuwei.travel.dao.UserDao;
import cn.liuwei.travel.dao.impl.UserDaoImpl;
import cn.liuwei.travel.domain.User;
import cn.liuwei.travel.service.UserService;
import cn.liuwei.travel.util.MailUtils;
import cn.liuwei.travel.util.UuidUtil;

public class UserServiceImpl implements UserService {
    UserDao userDao = new UserDaoImpl();
    @Override
    public boolean register(User user) {
        User existUser = userDao.findByUsername(user.getUsername());
        if (existUser == null){
            //设置激活码，唯一字符串
            user.setCode(UuidUtil.getUuid());
            //设置激活状态
            user.setStatus("N");
            //激活邮件发送
            String content = "<a href= 'http://localhost/travel/userServlet/active?code="+user.getCode()+"'>点击激活【1234旅游网】</a>";
            MailUtils.sendMail(user.getEmail(),content,"1234旅游网激活邮件");
            userDao.save(user);
            return true;
        }
        return false;
    }

    @Override
    public boolean activeUser(String code) {
        User user = userDao.findByUserCode(code);
        if (user != null){
            userDao.updateStatus(user);
            return true;
        }
        return false;
    }

    @Override
    public User login(User user) {
        user = userDao.findByUsernameAndPassword(user.getUsername(),user.getPassword());
        return user;
    }
}
