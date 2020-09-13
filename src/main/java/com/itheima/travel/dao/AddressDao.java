package com.itheima.travel.dao;

import com.itheima.travel.domain.Address;

import java.util.List;

public interface AddressDao {

    List<Address> findByUid(String uid);

    void save(Address address);

}
