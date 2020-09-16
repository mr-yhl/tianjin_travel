package com.itheima.travel.domain;

import lombok.Data;

import java.util.LinkedHashMap;

/**
 * 购物车对象
 */
@Data
public class Cart {
    // 总数量
    private Integer totalCount;
    // 总金额
    private  Double totalPrice;
    // 购物项集合
    private LinkedHashMap<String,CartItem> cartItemMap = new LinkedHashMap<>();



    // 计算总数量
    public Integer getTotalCount() {
        totalCount = 0;
        cartItemMap.forEach((rid,cartItem)->{
            totalCount += cartItem.getNum();
        });
        return totalCount;
    }

    // 计算总金额
    public Double getTotalPrice() {
        totalPrice = 0.0;
        cartItemMap.forEach((rid,cartItem)->{
            totalPrice+=cartItem.getSubTotal();
        });
        return totalPrice;
    }
}
