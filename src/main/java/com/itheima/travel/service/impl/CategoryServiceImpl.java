package com.itheima.travel.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.travel.dao.CategoryDao;
import com.itheima.travel.domain.Category;
import com.itheima.travel.service.CategoryService;
import com.itheima.travel.util.JedisUtils;
import com.itheima.travel.util.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.util.List;

public class CategoryServiceImpl implements CategoryService {
    @Override
    public List<Category> findAll() {
        // 创建返回对象
        List<Category> list =null;
        // 转换对象
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // 先查缓存中的数据
            // 获取对象
            Jedis jedis = JedisUtils.getJedis();
            // 判断缓存中的内容,存在内容返回查询结果
            if (jedis.exists("travel_category")) {
                // 获取json
                String json = jedis.get("travel_category");
                list=objectMapper.readValue(json,List.class);
                System.out.println("redis缓存");
            }else {
                // 缓存中没有数据,查询数据库
                SqlSession sqlSession = MyBatisUtils.openSession();
                CategoryDao categoryDao = sqlSession.getMapper(CategoryDao.class);

                list = categoryDao.findAll();
                // 将list转为json
                String json = objectMapper.writeValueAsString(list);
                // 将数据库缓存到redis中
                jedis.set("travel_category",json);
                MyBatisUtils.release(sqlSession);
                System.out.println("数据库查询");
            }
            jedis.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("redis异常");
        }



        return list;

    }
}
