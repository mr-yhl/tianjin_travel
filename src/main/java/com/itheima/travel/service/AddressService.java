package com.itheima.travel.service;

import com.itheima.travel.domain.Address;

import java.util.List;

public interface AddressService {
    /**
     * 根据uid查询
     * @param uid
     * @return
     */
    List<Address> findByUid(String uid);

    void save(Address address);

    void updateByAid(Address address);

    void deleAddress(String aid);

    void setZero(int uid);

    void setOne(int uid, String aid);
}
