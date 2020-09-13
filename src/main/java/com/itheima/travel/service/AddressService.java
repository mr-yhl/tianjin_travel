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
}
