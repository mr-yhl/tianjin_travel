package com.itheima.travel.service.impl;

import cn.hutool.crypto.SecureUtil;
import com.itheima.travel.dao.UserDao;
import com.itheima.travel.domain.ResultInfo;
import com.itheima.travel.domain.User;
import com.itheima.travel.service.UserService;
import com.itheima.travel.util.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;

public class UserServiceImpl implements UserService {

    @Override
    public ResultInfo register(User user) {
        // 获取代理对象
        SqlSession sqlSession = MyBatisUtils.openSession();
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        // 查询用户名是否存在
        User user1 = userDao.findByUsername(user.getUsername());
        if (user1 != null) {
            MyBatisUtils.release(sqlSession);
            return new ResultInfo(false, "用户名已存在");
        }
        // 查询手机号是否存在
        User user2 = userDao.findByTelephone(user.getTelephone());
        if (user2 != null) {
            MyBatisUtils.release(sqlSession);
            return new ResultInfo(false, "手机号已注册");
        }

        // 对密码进行加密
        user.setPassword(SecureUtil.md5(user.getPassword()));

        // 调用save()方法,完成注册功能
        userDao.save(user);

        MyBatisUtils.release(sqlSession);
        return new ResultInfo(true);
    }

    /**
     * 判断用户名
     * @param username
     * @return
     */
    @Override
    public User findByUsername(String username) {
        SqlSession sqlSession = MyBatisUtils.openSession();
        UserDao mapper = sqlSession.getMapper(UserDao.class);
        User byUsername = mapper.findByUsername(username);
        MyBatisUtils.release(sqlSession);
        return byUsername;
    }

    @Override
    public User findByTelephone(String telephone) {
        SqlSession sqlSession = MyBatisUtils.openSession();
        UserDao mapper = sqlSession.getMapper(UserDao.class);
        User byTelephone = mapper.findByTelephone(telephone);
        MyBatisUtils.release(sqlSession);
        return byTelephone;
    }

    @Override
    public ResultInfo sendSms(String telephone, String code) {
        System.out.println("验证码是"+code);
        return new ResultInfo(true,"发送成功");
    }
}
