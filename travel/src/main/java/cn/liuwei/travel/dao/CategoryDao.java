package cn.liuwei.travel.dao;

import cn.liuwei.travel.domain.Category;

import java.util.List;

public interface CategoryDao {
    /*
     查询所有
   */
    public List<Category> findAll();
}
