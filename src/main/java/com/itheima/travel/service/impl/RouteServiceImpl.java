package com.itheima.travel.service.impl;

import com.itheima.travel.dao.RouteDao;
import com.itheima.travel.domain.PageBean;
import com.itheima.travel.domain.Route;
import com.itheima.travel.service.RouteService;
import com.itheima.travel.util.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * 线路相关接口实现类
 */
public class RouteServiceImpl implements RouteService {

    /**
     * 实现类
     * @param cid 导航的编号(分类的编号)
     * @param pageNum 页数
     * @param pageSize 每页数量
     * @param rname
     * @return
     */
    @Override
    public PageBean<Route> findByPage(Integer cid, Integer pageNum, Integer pageSize, String rname) {
        // 创建代理对象
        SqlSession sqlSession = MyBatisUtils.openSession();
        RouteDao routeDao = sqlSession.getMapper(RouteDao.class);

        // 查询总记录数,通过cid查询记录,返回数量totalCount
        Integer totalCount = routeDao.findCount(cid,rname);

        // 通过当前页和每页条数计算开始索引
        Integer index = (pageNum - 1) * pageSize;

        // 查询返回结果集
        List<Route> list = routeDao.findList(index,pageSize,cid,rname);

        // 释放资源
        MyBatisUtils.release(sqlSession);
        return new PageBean<Route>(pageNum,pageSize,totalCount,list);
    }

    /**
     *
     * @param rid
     * @return
     */
    @Override
    public Route findDetail(Integer rid) {
        SqlSession sqlSession = MyBatisUtils.openSession();
        RouteDao routeDao = sqlSession.getMapper(RouteDao.class);

        Route route = routeDao.findDetail(rid);

        MyBatisUtils.release(sqlSession);

        return route;
    }
}
