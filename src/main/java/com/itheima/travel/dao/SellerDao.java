package com.itheima.travel.dao;

import com.itheima.travel.domain.Seller;
import org.apache.ibatis.annotations.Select;

public interface SellerDao {
    @Select("select * from tab_seller where sid = #{sid}")
    Seller findBySid(Integer sid);
}
