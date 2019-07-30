package cn.liuwei.travel.service;

import cn.liuwei.travel.domain.User;

public interface UserService {
    public boolean register(User user);
    public boolean activeUser(String code);
    public User login(User user);
}
