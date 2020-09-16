package com.itheima.travel.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.travel.domain.Cart;
import redis.clients.jedis.Jedis;

/**
 * 购物项对象(redis操作) 工具类
 */
public class CartUtils {
    static ObjectMapper objectMapper = new ObjectMapper();

    // 将购物车保存到redis
    public static void cart2Redis(Integer uid, Cart cart) {
        Jedis jedis = JedisUtils.getJedis();
        try {
            // 将cart转为json
            String json = objectMapper.writeValueAsString(cart);
            // 保存到redis中
            jedis.set("travel_cart_" + uid, json);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
    }

    // 从redis中获取购物车
    public static Cart redis2Cart(Integer uid) {
        Jedis jedis = JedisUtils.getJedis();
        Cart cart = null;
        try {
            if (jedis.exists("travel_cart_" + uid)) {
                // 存在，获取
                String json = jedis.get("travel_cart_" + uid);
                // 转为cart对象
                cart = objectMapper.readValue(json, Cart.class);
            } else {
                // 不存在，创建
                cart = new Cart();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return cart;
    }
}
