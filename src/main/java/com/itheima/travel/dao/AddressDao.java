package com.itheima.travel.dao;

import com.itheima.travel.domain.Address;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AddressDao {

    List<Address> findByUid(String uid);

    void save(Address address);

    void updateByAid(Address address);

    void deleAddress(String aid);

    void setZero(int uid);

    void setOne(@Param("uid") int uid, @Param("aid") String aid);

}
