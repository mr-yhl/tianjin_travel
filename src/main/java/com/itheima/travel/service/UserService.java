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
}
