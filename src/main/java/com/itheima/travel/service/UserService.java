package com.itheima.travel.service;

import com.itheima.travel.domain.ResultInfo;
import com.itheima.travel.domain.User;

public interface UserService {
    /**
     * @description 用户注册
     * @param user
     * @return
     */
    ResultInfo register(User user);

    /**
     * 判断用户名
     * @param username
     * @return
     */
    User findByUsername(String username);

    User findByTelephone(String telephone);

    /**
     * 发送短信
     * @param telephone
     * @param code
     * @return
     */
    ResultInfo sendSms(String telephone, String code);

    /**
     * 验证登陆,用户密码方式
     * @param username
     * @param password
     * @return
     */
    ResultInfo loginPwd(String username, String password);

    /**
     * 更新用户信息
     * @param user
     */
    void updateInfo(User user);

    /**
     * 根据id找用户
     * @param uid
     * @return
     */
    User findByUid(int uid);
}
