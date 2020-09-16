package com.itheima.travel.dao;

import com.itheima.travel.domain.Category;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CategoryDao {
    List<Category> findAll();

    @Select("select * from tab_category where cid = #{cid}")
    Category findByCid(Integer cid);

}
