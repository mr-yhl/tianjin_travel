package com.itheima.travel.service.impl;

import com.itheima.travel.dao.AddressDao;
import com.itheima.travel.domain.Address;
import com.itheima.travel.service.AddressService;
import com.itheima.travel.util.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class AddressServiceImpl implements AddressService {
    /**
     * 根据uid查询
     * @param uid
     * @return
     */
    @Override
    public List<Address> findByUid(String uid) {
        // 创建代理对象
        SqlSession sqlSession = MyBatisUtils.openSession();
        AddressDao mapper = sqlSession.getMapper(AddressDao.class);

        List<Address> list = mapper.findByUid(uid);

        // 释放资源
        MyBatisUtils.release(sqlSession);

        return list;
    }

    @Override
    public void save(Address address) {
        // 创建代理对象
        SqlSession sqlSession = MyBatisUtils.openSession();
        AddressDao mapper = sqlSession.getMapper(AddressDao.class);

        mapper.save(address);

        // 释放资源
        MyBatisUtils.release(sqlSession);


    }
}
