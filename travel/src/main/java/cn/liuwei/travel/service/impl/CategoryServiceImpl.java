package cn.liuwei.travel.service.impl;

import cn.liuwei.travel.dao.CategoryDao;
import cn.liuwei.travel.dao.impl.CategoryDaoImpl;
import cn.liuwei.travel.domain.Category;
import cn.liuwei.travel.service.CategoryService;
import cn.liuwei.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService {
    CategoryDao categoryDao = new CategoryDaoImpl();
    @Override
    public List<Category> findAll() {
        //1 从redis中查询
        //1.1 获取jedis客户端
        Jedis jedis = JedisUtil.getJedis();
        //1.2 可使用sortedset排序查询
//        Set<String> categorys = jedis.zrange("category",0,-1);
        //1.3 查询sortset中的分数（cid）和值（cname）
        Set<Tuple> categorys = jedis.zrangeWithScores("category",0,-1);
        List<Category> list = null;
        if (categorys == null || categorys.size() ==0){
            System.out.println("从数据库中查询");
            list = categoryDao.findAll();
            System.out.println(list);
            for (int i = 0; i < list.size(); i++) {
                jedis.zadd("category",list.get(i).getCid(),list.get(i).getCname());
            }
        }else {
            System.out.println("从redis中查询");
            list = new ArrayList<Category>();
            for (Tuple tuple : categorys) {
                Category category = new Category();
                category.setCname(tuple.getElement());
                category.setCid((int)tuple.getScore());
                list.add(category);
            }
        }
        return list;
    }
}
