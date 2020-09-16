package com.itheima.travel.domain;

import lombok.Data;

/**
 * 购物项
 */
@Data
public class CartItem {
    // 商品
    private Route route;
    // 数量
    private Integer num;
    // 小计 double
    private Double subTotal;

    // 计算小计,el表达式取值是通过get方法获取
    public Double getSubTotal(){
        return route.getPrice()*num;
    }
}
