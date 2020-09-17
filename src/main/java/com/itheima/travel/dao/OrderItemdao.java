package com.itheima.travel.dao;

import com.itheima.travel.domain.OrderItem;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;

public interface OrderItemdao {
    @Insert("insert into tab_orderitem values(null,#{itemtime},#{state},#{num},#{subtotal},#{rid},#{oid})")
    void save(OrderItem orderItem);

    @Update("update tab_orderitem set state = 1 where oid = #{oid}")
    void updateState(String oid);
}
