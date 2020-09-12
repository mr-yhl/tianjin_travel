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
}
