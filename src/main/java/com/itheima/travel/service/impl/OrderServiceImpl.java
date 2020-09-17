package com.itheima.travel.service.impl;

import com.itheima.travel.dao.OrderDao;
import com.itheima.travel.dao.OrderItemdao;
import com.itheima.travel.domain.Order;
import com.itheima.travel.domain.OrderItem;
import com.itheima.travel.domain.ResultInfo;
import com.itheima.travel.service.OrderService;
import com.itheima.travel.util.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Map;

public class OrderServiceImpl implements OrderService {
    @Override
    public void save(Order order) {
        // 创建代理对象
        SqlSession sqlSession = MyBatisUtils.openSession();
        OrderDao orderDao = sqlSession.getMapper(OrderDao.class);
        OrderItemdao orderItemdao = sqlSession.getMapper(OrderItemdao.class);

        // 保存订单
        orderDao.save(order);
        List<OrderItem> orderItemList = order.getOrderItemList();
        for (OrderItem orderItem : orderItemList) {
            orderItemdao.save(orderItem);
        }


        // 释放资源
        MyBatisUtils.release(sqlSession);
    }

    @Override
    public void updateState(Map<String, String> map) {
        // 创建代理对象
        SqlSession sqlSession = MyBatisUtils.openSession();
        OrderDao orderDao = sqlSession.getMapper(OrderDao.class);
        OrderItemdao orderItemdao = sqlSession.getMapper(OrderItemdao.class);

        // 获取订单编号
        String oid = map.get("out_trade_no");

        // 修改订单状态
        orderDao.updateState(oid);

        // 修改订单项状态
        orderItemdao.updateState(oid);

        // 释放资源
        MyBatisUtils.release(sqlSession);

    }

    @Override
    public ResultInfo isPay(String oid) {
        SqlSession sqlSession = MyBatisUtils.openSession();
        OrderDao orderDao = sqlSession.getMapper(OrderDao.class);

        Order order= orderDao.findByOid(oid);
        // 判断是否支付
        ResultInfo resultInfo = null;
        if (order.getState() == 1) {
            resultInfo = new ResultInfo(true);

        } else {
            resultInfo = new ResultInfo(false);

        }


        MyBatisUtils.release(sqlSession);
        return resultInfo;
    }
}
