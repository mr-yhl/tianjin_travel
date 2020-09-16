package com.itheima.travel.dao;

import com.itheima.travel.domain.Route;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RouteDao {

    Integer findCount(@Param("cid") Integer cid,@Param("rname") String rname);


    List<Route> findList(@Param("index") Integer index, @Param("pageSize") Integer pageSize, @Param("cid") Integer cid,@Param("rname") String rname);

    Route findDetail(Integer rid);

}
