package com.itheima.travel.dao;

import com.itheima.travel.domain.User;
import org.apache.ibatis.annotations.Select;

public interface UserDao {
    /**
     * 根据用户名查询
     * @param username
     * @return
     */
    @Select("SELECT * FROM tab_user WHERE username = #{username}")
    User findByUsername(String username);

    /**
     * 根据手机号进行查询
     * @param telephone
     * @return
     */
    @Select("SELECT * FROM tab_user WHERE telephone = #{telephone}")
    User findByTelephone(String telephone);

    /**
     * 注册
     * @param user
     */
    void save(User user);
}
