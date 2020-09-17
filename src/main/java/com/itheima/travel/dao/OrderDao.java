package com.itheima.travel.dao;

import com.itheima.travel.domain.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface OrderDao {
    @Insert("insert into tab_order values(#{oid},#{ordertime},#{total},#{state},#{address},#{contact},#{telephone},#{uid})")
    void save(Order order);

    @Update("update tab_order set state = 1 where oid = #{oid}")
    void updateState(String oid);
    @Select("select * from tab_order where oid = #{oid}")
    Order findByOid(String oid);
}
