package com.itheima.travel.service;

import com.itheima.travel.domain.PageBean;
import com.itheima.travel.domain.Route;

/**
 * 线路相关的接口
 */

public interface RouteService {
    /**
     * 获取线路的数据
     * @param cid 导航的编号(分类的编号)
     * @param pageNum 页数
     * @param pageSize 每页数量
     * @param rname
     * @return
     */
    PageBean<Route> findByPage(Integer cid, Integer pageNum, Integer pageSize, String rname);

    /**
     * 详情展示
     * @param rid
     * @return
     */
    Route findDetail(Integer rid);
}
